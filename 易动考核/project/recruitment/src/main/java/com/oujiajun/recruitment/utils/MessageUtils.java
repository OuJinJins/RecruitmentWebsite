package com.oujiajun.recruitment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oujiajun.recruitment.entity.dto.ResultMessage;
import com.oujiajun.recruitment.entity.po.User;

/**
 * @author oujiajun
 */
public class MessageUtils {
    public static String getMessage(boolean isSystemMessage, User fromUser, Object message){
        try {
            // 设置结果信息
            ResultMessage result = new ResultMessage();
            result.getMessage().setIsSystem(isSystemMessage);
            result.getMessage().setMessage(message);
            if (fromUser!=null){
                result.getMessage().setFromUser(fromUser);
            }
            // 把字符串转成json格式的字符串
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
