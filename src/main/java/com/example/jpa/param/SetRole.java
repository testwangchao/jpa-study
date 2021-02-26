package com.example.jpa.param;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetRole {
    private int roleId;
    private int userId;
}
