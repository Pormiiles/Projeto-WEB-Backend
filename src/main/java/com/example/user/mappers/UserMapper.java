package com.example.user.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.user.models.User;
import com.example.user.models.UserGetResponseDTO;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserGetResponseDTO userToUserGetResponseDTO(User user);
}
