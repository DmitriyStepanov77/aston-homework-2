package com.example.aston_homework.storage;

import com.example.aston_homework.model.History;

import java.sql.SQLException;
import java.util.List;

public interface HistoryStorage {
    public History save(History history) throws SQLException;

    public List<History> get(Long chatId) throws SQLException;
}
