package com.example.demoopenai.dto;

public record BillItem(String itemName,
                       Integer quantity,
                       Double price,
                       Double subTotal
) {
}
