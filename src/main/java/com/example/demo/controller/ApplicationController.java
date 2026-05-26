package com.example.demo.controller;

import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.security.service.JwtTokenService;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@Tag(description = "Операции с заявками", name = "Заявки")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, UserService userService, JwtTokenService jwtTokenService) {
        this.applicationService = applicationService;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping
    @Operation(summary = "Получение всех заявок")
    public ResponseEntity<List<ApplicationResponseDto>> getApplications(
            @RequestHeader("Authorization") String authHeader
    ) {
        String name = jwtTokenService.extractUsername(authHeader.replace("Bearer ", ""));
        List<ApplicationResponseDto> applications = applicationService.getAllApplications();
        return applications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(applications);
    }

    @GetMapping("/findByUser")
    @Operation(summary = "Получение заявок пользователя")
    public ResponseEntity<List<ApplicationResponseDto>> getUsersApplications (
            @RequestParam int userId
    ) {
        List<ApplicationResponseDto> usersApplications = applicationService.getApplicationsByUser(userId);
        return usersApplications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(usersApplications);
    }

    @GetMapping("/accepted")
    @Operation(summary = "Получение списка всех одобренных заявок")
    public ResponseEntity<List<ApplicationResponseDto>> getAllAcceptedApplications() {
        List<ApplicationResponseDto> acceptedApplications = applicationService.getAllAccepted();
        return acceptedApplications.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(acceptedApplications);
    }

    @PostMapping
    @Operation(summary = "Создание новой заявки на кредит")
    public HttpStatus createApplication(
            @RequestBody ApplicationRequestDto requestDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader
    ) {
        String username = jwtTokenService.extractUsername(authHeader.replace("Bearer ", ""));
        requestDto.setUserId(userService.getUserByUsername(username).id);

        boolean isCreated = applicationService.createApplication(requestDto);
        return isCreated ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }
}
