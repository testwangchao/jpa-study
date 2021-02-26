package com.example.jpa.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User2 {
    private int id;
    private String name;
    private String enName;
    private String email;
    private int age;
    private boolean is_true;

    public int test() {
        return 1/0;
    }
}
