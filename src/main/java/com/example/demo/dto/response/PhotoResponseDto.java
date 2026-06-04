package com.example.demo.dto.response;

import com.example.demo.enums.PhotoType;
import io.swagger.v3.oas.annotations.media.Schema;

public class PhotoResponseDto {
    @Schema(description = "ID фотографии документа")
    private int id;

    @Schema(description = "Тип документа")
    private PhotoType type;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(PhotoType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public PhotoType getType() {
        return type;
    }
}
