package com.example.demo.mapper;

import com.example.demo.dto.request.ApplicationRequestDto;
import com.example.demo.dto.response.ApplicationResponseDto;
import com.example.demo.entity.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "passportPhotoPath", expression = "java(\"/api/applications/\" + application.getId() + \"/documents/passport\")")
    @Mapping(target = "registrationPhotoPath", expression = "java(\"/api/applications/\" + application.getId() + \"/documents/registration\")")
    @Mapping(target = "userPhotoPath", expression = "java(\"/api/applications/\" + application.getId() + \"/documents/avatar\")")
    ApplicationResponseDto toResponseDto(Application application);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true) // Set manually via service
    Application toEntityFromRequest(ApplicationRequestDto requestDto);

}
