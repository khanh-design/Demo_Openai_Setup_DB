package com.example.demoopenai.dto;

public record ExpenseInfo(
        String category,
        String itemName,
        Double amount
) {

}
