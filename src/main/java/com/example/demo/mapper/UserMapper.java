package com.example.demo.mapper;

import com.example.demo.dto.request.UserRequestDto;
import com.example.demo.dto.response.UserResponseDto;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "applications", ignore = true)
    UserResponseDto toResponseDto(User user);

    User toEntityFromRequest(UserRequestDto user);

}
