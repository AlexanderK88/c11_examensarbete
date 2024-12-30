package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.List;

public record ListDto(
        String listName,
        String description,
        int user_id) {
    public static ListDto fromList(List list) {
        return new ListDto(
                list.getListName(),
                list.getDescription(),
                list.getUser().getId()
        );
    }
}
