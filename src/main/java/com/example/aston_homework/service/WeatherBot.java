package com.example.aston_homework.service;

import com.example.aston_homework.config.BotConfig;
import com.example.aston_homework.model.Weather;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class WeatherBot extends TelegramLongPollingBot {
    private final BotConfig config;
    @Autowired
    private WeatherService weatherService;

    private boolean stateGetWeather = false;

    public WeatherBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String memberName = update.getMessage().getFrom().getFirstName();

            switch (messageText){
                case "/start":
                    startBot(chatId, memberName);
                    break;
                case "/weather":
                    startWeather(chatId);
                    break;
                default: if(stateGetWeather) {
                    getWeather(chatId, messageText);
                }
            }
        }
    }

    private void startBot(long chatId, String userName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Hello, " + userName + "! I'm a Telegram bot.");
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void startWeather(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Enter city name.");
        stateGetWeather = true;
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void getWeather(long chatId, String city) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        Weather weather = weatherService.getWeather(city);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Temperature = " + weather.getTemperature() + ", ");
        stringBuilder.append("wind_speed = " + weather.getWind_speed() + ", ");
        stringBuilder.append("wind_degree = " + weather.getWind_degree());
        message.setText(stringBuilder.toString());
        stateGetWeather = true;
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}

