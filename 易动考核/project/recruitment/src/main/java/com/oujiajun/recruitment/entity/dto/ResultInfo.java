package com.oujiajun.recruitment.entity.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 结果信息集
 * @author oujiajun
 */
@Data
@NoArgsConstructor
public class ResultInfo {

    private Boolean success;

    private String message;

    private Object data;


    public ResultInfo(Boolean success) {
        this.success = success;
    }


    public ResultInfo(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    public ResultInfo(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
