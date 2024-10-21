package com.example.aston_homework.storage;

import com.example.aston_homework.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Slf4j
@Repository
@PropertySource("application.properties")
public class UserStorageJdbc implements UserStorage {
    private PreparedStatement statement;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public User save(User user) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String query = "INSERT INTO Users(name, chat_id) VALUES(?, ?)";

            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setLong(2, user.getChatId());
            statement.executeUpdate();
            user.setId(statement.getGeneratedKeys().getLong(1));
        }
        return user;
    }

    @Override
    public User get(Long chatId) throws SQLException {
        User user = new User();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            String query = "SELECT * FROM Users WHERE chat_id = ?";

            statement = connection.prepareStatement(query);
            statement.setLong(1, chatId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setChatId(resultSet.getLong("chat_id"));
            }
        }
        return user;
    }
}
