package com.example.jpa.param;

import javax.validation.constraints.NotBlank;
import com.example.jpa.dto.base.InputConverter;
import com.example.jpa.enums.UserStatusEnum;
import com.example.jpa.pojo.User;
import lombok.Data;

@Data
public class UserInfo implements InputConverter<User> {
    @NotBlank(message = "用户名不能为空")
    private String name;
    private int id;
    private int status;
}
