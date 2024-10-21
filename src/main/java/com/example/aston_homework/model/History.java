package com.example.aston_homework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Long id;
    private Long chatId;
    private String town;
    private String response;
    private LocalDateTime time;
}
