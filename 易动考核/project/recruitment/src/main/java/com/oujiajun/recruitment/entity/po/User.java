package com.oujiajun.recruitment.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String email;
    private String username;
    private String password;
    private String gender;
    private String city;
    private String briefIntroduction;
    private Integer age;
    private String education;
    private String profile;
}
