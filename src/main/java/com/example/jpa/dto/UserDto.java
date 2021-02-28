package com.example.jpa.dto;

import com.example.jpa.dto.base.OutputConverter;
import com.example.jpa.pojo.User;
import lombok.Data;
import lombok.ToString;

/**
 * 比如当前用户只想返回以下属性，需要使用DTO，
 */
@Data
@ToString
public class UserDto implements OutputConverter<UserDto, User> {
    private Integer id;
    private String name;
    private String password;
    private String birthday;

}
