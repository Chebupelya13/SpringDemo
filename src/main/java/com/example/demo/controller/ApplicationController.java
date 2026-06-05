package com.example.demo.controller;

import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.service.ApplicationService;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtTokenService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@Tag(description = "Операции с заявками", name = "Заявки")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;

    @Autowired
    public ApplicationController(ApplicationService applicationService, UserService userService, JwtTokenService jwtTokenService, AuthService authService) {
        this.applicationService = applicationService;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.authService = authService;
    }

    @GetMapping("/findByUser")
    @Operation(summary = "Получение всех заявок пользователя")
    public ResponseEntity<ListResponseDto<ApplicationResponseDto>> getUsersApplications() {
        ListResponseDto<ApplicationResponseDto> applications = applicationService.getApplicationsByUser(
                authService.getCurrentUser().getId()
        );

        return ResponseEntity.ok(applications);
    }

    @GetMapping
    @Operation(summary = "Получение заявок пользователя")
    public ResponseEntity<ListResponseDto<ApplicationResponseDto>> getApplications (
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        ListResponseDto<ApplicationResponseDto> usersApplications = applicationService.getAllApplications(limit, offset);

        return ResponseEntity.ok(usersApplications);
    }

    @GetMapping("/accepted")
    @Operation(summary = "Получение списка одобренных заявок")
    public ResponseEntity<ListResponseDto<ApplicationResponseDto>> getAllAcceptedApplications() {
        ListResponseDto<ApplicationResponseDto> acceptedApplications = applicationService.getAcceptedApplicationsByUser(
                authService.getCurrentUser().getId()
        );

        return ResponseEntity.ok(acceptedApplications);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Создание новой заявки на кредит")
    public HttpStatus createApplication(
            @ModelAttribute ApplicationRequestDto requestDto
    ) {
        requestDto.setUserId(authService.getCurrentUser().getId());

        boolean isCreated = applicationService.createApplication(requestDto);
        return isCreated ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }

}
