package com.example.c11_examensarbete.dtos;

import com.example.c11_examensarbete.entities.Image;

public record ImageDto(Integer id,
                       String imageUrl,
                       String largeImageUrl,
                       String smallImageUrl,
                       Integer manga_id) {
    public static ImageDto fromImage(Image image) {
        return new ImageDto(
                image.getId(),
                image.getImageUrl(),
                image.getLargeImageUrl(),
                image.getSmallImageUrl(),
                image.getManga().getId());
    }
}
