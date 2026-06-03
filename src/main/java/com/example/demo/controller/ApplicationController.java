package com.example.demo.controller;

import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.dto.response.ListResponseDto;
import com.example.demo.service.ApplicationService;
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

import java.io.InputStream;

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
    public ResponseEntity<ListResponseDto<ApplicationResponseDto>> getApplications(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader
    ) {
        String username = jwtTokenService.extractUsername(authHeader.replace("Bearer ", ""));

        ListResponseDto<ApplicationResponseDto> applications = applicationService.getApplicationsByUser(
                userService.getUserByUsername(username).id
        );

        return ResponseEntity.ok(applications);
    }

    @GetMapping("/findByUser")
    @Operation(summary = "Получение заявок пользователя")
    public ResponseEntity<ListResponseDto<ApplicationResponseDto>> getUsersApplications (
            @RequestParam int userId
    ) {
        ListResponseDto<ApplicationResponseDto> usersApplications = applicationService.getApplicationsByUser(userId);

        return ResponseEntity.ok(usersApplications);
    }

    @GetMapping("/accepted")
    @Operation(summary = "Получение списка одобренных заявок")
    public ResponseEntity<ListResponseDto<ApplicationResponseDto>> getAllAcceptedApplications(
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader
    ) {
        String username = jwtTokenService.extractUsername(authHeader.replace("Bearer ", ""));
        ListResponseDto<ApplicationResponseDto> acceptedApplications = applicationService.getAcceptedApplicationsByUser(
                userService.getUserByUsername(username).id
        );

        return ResponseEntity.ok(acceptedApplications);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Создание новой заявки на кредит")
    public HttpStatus createApplication(
            @ModelAttribute ApplicationRequestDto requestDto,
            @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader
    ) {
        String username = jwtTokenService.extractUsername(authHeader.replace("Bearer ", ""));
        requestDto.setUserId(userService.getUserByUsername(username).id);

        boolean isCreated = applicationService.createApplication(requestDto);
        return isCreated ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
    }

    @Operation(summary = "Получение аватарки пользователя")
    @GetMapping("/{applicationId}/documents/avatar")
    public ResponseEntity<byte[]> getAvatar( @PathVariable int applicationId ){
        try (InputStream stream = applicationService.getAvatarFile(applicationId)) {
            byte[] response = stream.readAllBytes();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Получение фото прописки пользователя")
    @GetMapping("/{applicationId}/documents/registrtion")
    public ResponseEntity<byte[]> getRegistration( @PathVariable int applicationId ){
        try (InputStream stream = applicationService.getRegistrationFile(applicationId)) {
            byte[] response = stream.readAllBytes();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @Operation(summary = "Получение фото паспорта пользователя")
    @GetMapping("/{applicationId}/documents/passport")
    public ResponseEntity<byte[]> getPassport( @PathVariable int applicationId ){
        try (InputStream stream = applicationService.getPassportFile(applicationId)) {
            byte[] response = stream.readAllBytes();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
