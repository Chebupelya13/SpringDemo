package com.example.demo.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

public class EmploymentPeriodResponseDto {

    @Schema(description = "ID места работы", example = "1")
    private int id;

    @Schema(description = "ID пользователя", example = "1")
    private int userId;

    @Schema(description = "Дата начала работы", example = "2020-01-01T00:00:00.000Z")
    private Date since;

    @Schema(description = "Дата окончания работы", example = "2022-01-01T00:00:00.000Z")
    private Date upTo;

    @Schema(description = "Название компании", example = "ООО Ромашка")
    private String companyTitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUpTo() {
        return upTo;
    }

    public void setUpTo(Date upTo) {
        this.upTo = upTo;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }
}
