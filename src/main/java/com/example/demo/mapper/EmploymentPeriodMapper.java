package com.example.demo.mapper;

import com.example.demo.dto.request.EmploymentPeriodRequestDto;
import com.example.demo.dto.response.EmploymentPeriodResponseDto;
import com.example.demo.entity.EmploymentPeriod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmploymentPeriodMapper {

    @Mapping(target = "userId", source = "user.id")
    EmploymentPeriodResponseDto toResponseDto(EmploymentPeriod employmentPeriod);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true) // Set manually via service
    EmploymentPeriod toEntityFromRequest(EmploymentPeriodRequestDto requestDto);
}
