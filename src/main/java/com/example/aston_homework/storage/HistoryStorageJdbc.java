package com.example.aston_homework.storage;

import com.example.aston_homework.model.History;
import com.example.aston_homework.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@PropertySource("application.properties")
public class HistoryStorageJdbc implements HistoryStorage {
    private PreparedStatement statement;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public History save(History history) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String query = "INSERT INTO histories(chat_id, town, response, request_time) VALUES(?, ?, ?, ?)";

            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, history.getChatId());
            statement.setString(2, history.getTown());
            statement.setString(3, history.getResponse());
            statement.setTimestamp(4, Timestamp.from(Instant.now()));
            statement.executeUpdate();
            history.setId(statement.getGeneratedKeys().getLong(1));
        }
        return history;
    }

    @Override
    public List<History> get(Long chatId) throws SQLException {
        List<History> histories = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String query = "SELECT * FROM histories WHERE chat_id = ?";

            statement = connection.prepareStatement(query);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                History history = new History();
                history.setId(resultSet.getLong("id"));
                history.setChatId(resultSet.getLong("chat_id"));
                history.setTown(resultSet.getString("town"));
                history.setResponse(resultSet.getString("response"));
                history.setTime(resultSet.getTimestamp("request_time").toLocalDateTime());

                histories.add(history);
            }
        }
        return histories;
    }
}
