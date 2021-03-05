package com.example.jpa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User2 {
    private Integer id;
    private String name;
    private String enName;
    private String email;
    private int age;
    private boolean isTrue;

}
