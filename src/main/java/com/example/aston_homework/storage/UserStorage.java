package com.example.aston_homework.storage;

import com.example.aston_homework.model.User;

import java.sql.SQLException;

public interface UserStorage {
    public User save(User user) throws SQLException;

    public User get(Long chatId) throws SQLException;
}
