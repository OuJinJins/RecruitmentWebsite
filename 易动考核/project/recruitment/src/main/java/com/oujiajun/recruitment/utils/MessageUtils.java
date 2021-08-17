package com.oujiajun.recruitment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oujiajun.recruitment.entity.dto.ResultMessage;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.entity.vo.MessageVo;

/**
 * @author oujiajun
 */
public class MessageUtils {
    public static String getMessage(boolean isSystemMessage, User fromUser, Object message){
        try {
            // 设置结果信息
            ResultMessage result = new ResultMessage();
            // 系统消息
            if (isSystemMessage){
                result.setMessage(new MessageVo());
                result.getMessage().setIsSystem(true);
                result.getMessage().setMessage(((MessageVo)message).getMessage());
            }else {
                // 非系统消息
                result.setMessage((MessageVo)message);
                result.getMessage().setIsSystem(false);
            }
            if (fromUser!=null){
                result.getMessage().setFromUserId(fromUser.getId());
                result.getMessage().setFromUser(fromUser);
            }
            System.out.println(":"+result.getMessage().getToRoomId());
            // 把字符串转成json格式的字符串
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(result));
            return mapper.writeValueAsString(result);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
