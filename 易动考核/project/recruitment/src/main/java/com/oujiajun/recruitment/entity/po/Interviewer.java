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
public class Interviewer extends User{
    String company;
    Boolean isPass;
}
