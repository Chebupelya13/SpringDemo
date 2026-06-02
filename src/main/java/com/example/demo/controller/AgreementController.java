package com.example.demo.controller;

import com.example.demo.dto.request.AgreementRequestDto;
import com.example.demo.dto.response.AgreementResponseDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.service.AgreementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agreement")
@Tag(name = "Договоры", description = "Операции с кредитными договорами")
public class AgreementController {

    private final AgreementService agreementService;

    @Autowired
    public AgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @GetMapping
    @Operation(summary = "Получение списка всех договоров")
    public ResponseEntity<ListResponseDto<AgreementResponseDto>> getAllAgreements() {
        return ResponseEntity.ok(agreementService.getAgreements());
    }

    @PostMapping
    @Operation(summary = "Создание нового договора")
    public HttpStatus createAgreement(
            @RequestBody AgreementRequestDto requestDto
    ) {
        try {
            agreementService.addAgreement(requestDto);

            return HttpStatus.CREATED;

        } catch (Exception e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/findByUser")
    @Operation(summary = "Получение договоров пользователя")
    public ResponseEntity<ListResponseDto<AgreementResponseDto>> getUsersAgreements(@RequestParam int userId ) {
        try {
            return ResponseEntity.ok(agreementService.getUsersAgreements(userId));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/sign")
    @Operation(summary = "Изменение статуса договора на 'подписано'")
    public HttpStatus signAgreement(
            @RequestParam int agreementId,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader
    ) {
        try {
            boolean signStatus = agreementService.signAgreement(agreementId);
            if (signStatus)
                return HttpStatus.ACCEPTED;

            return HttpStatus.NOT_FOUND;

        } catch (IllegalArgumentException e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    @GetMapping("/findByApplication")
    @Operation(summary = "Получение договора по идентификатору заявки")
    public ResponseEntity<AgreementResponseDto> getAgreementByApplication(@RequestParam int applicationId ) {
        try {
            AgreementResponseDto agreement = agreementService.getAgreementByApplicationId(applicationId);

            return agreement == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(agreement);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
