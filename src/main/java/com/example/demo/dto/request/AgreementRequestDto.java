package com.example.demo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class AgreementRequestDto {

    @Schema(description = "ID заявки", example = "1")
    private int applicationId;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
}
