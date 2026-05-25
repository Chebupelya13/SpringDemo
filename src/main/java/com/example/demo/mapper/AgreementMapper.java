package com.example.demo.mapper;

import com.example.demo.dto.request.AgreementRequestDto;
import com.example.demo.dto.response.AgreementResponseDto;
import com.example.demo.entity.Agreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgreementMapper {

    @Mapping(target = "applicationId", source = "application.id")
    AgreementResponseDto toResponseDto(Agreement agreement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "application", ignore = true) // Set manually via service
    Agreement toEntityFromRequest(AgreementRequestDto requestDto);
}
