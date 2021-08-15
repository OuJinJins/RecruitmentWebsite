package com.oujiajun.recruitment.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oujiajun
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMessage {
    private boolean isSystem;
    private String fromName;
    private String toName;
    private Object message;
}
