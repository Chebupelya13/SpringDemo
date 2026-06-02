package com.example.demo.dto.response;

import com.example.demo.entity.Agreement;
import com.example.demo.enums.AgreementStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public class AgreementResponseDto {

    @Schema(description = "ID договора в БД", example = "1")
    private int id;

    @Schema(description = "ID заявки", example = "1")
    private int applicationId;

    @Schema(description = "Статус договора", example = "WAITING_TO_SIGN")
    private AgreementStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public AgreementStatus getStatus() {
        return status;
    }

    public void setStatus(AgreementStatus status) {
        this.status = status;
    }
}
