package com.oujiajun.recruitment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oujiajun.recruitment.config.GetHttpSessionConfigurator;
import com.oujiajun.recruitment.entity.dto.ResultInfo;
import com.oujiajun.recruitment.entity.po.Message;
import com.oujiajun.recruitment.entity.po.User;
import com.oujiajun.recruitment.service.RoomService;
import com.oujiajun.recruitment.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author oujiajun
 */
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndpoint {
    @Autowired
    RoomService roomService;

    /**
     * 用来存储每个用户客户端对象的ChatEndpoint对象
     */
    private static Map<Integer,ChatEndpoint> onlineUsers = new ConcurrentHashMap<>();

    /**
     * 声明session对象，通过对象可以发送消息给指定的用户
     */
    private Session session;

    /**
     * 声明HttpSession对象，我们之前在HttpSession对象中存储了用户
     */
    private HttpSession httpSession;

    /**
     * 连接建立时被调用
     * @param session session
     * @param config config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //存储登陆的对象
        User loginUser = (User)httpSession.getAttribute("loginUser");

        onlineUsers.put(loginUser.getId(),this);

        //将当前在线用户的用户名推送给所有的客户端
        //1 获取消息
        String message = MessageUtils.getMessage(true, null, getAllIds());
        //2 调用方法进行系统消息的推送
        broadcastAllUsers(message);
    }

    private void broadcastAllUsers(String message){
        try {
            //将消息推送给聊天室成员
            // 此处改为所有聊天室
            Set<Integer> ids = onlineUsers.keySet();
            for (Integer id : ids) {
                ChatEndpoint chatEndpoint = onlineUsers.get(id);
                chatEndpoint.session.getBasicRemote().sendText(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 返回在线关联用户id
     * @return Set<Integer>
     */
    private Set<Integer> getRelatedOnlineIds(){
        // TODO 关联用户id
        return onlineUsers.keySet();
    }

    /**
     * 返回在线用户id
     * @return Set<Integer>
     */
    private Set<Integer> getAllIds(){
        return onlineUsers.keySet();
    }

    /**
     * 收到客户端发送的消息时被调用
     * @param message message
     * @param session session
     */
    @OnMessage
    public void onMessage(String message,Session session){
        //将数据转换成对象
        try {
            ObjectMapper mapper =new ObjectMapper();
            Message mess = mapper.readValue(message, Message.class);
            Integer toRoomId = mess.getToRoomId();
            Object data = mess.getMessage();
            User loginUser = (User) httpSession.getAttribute("loginUser");
            String resultMessage = MessageUtils.getMessage(false, loginUser, data);
            // 发送数据
            // 遍历房间对应的用户id 除了自己
            // 发送数据到客户端
            ResultInfo resultInfo = roomService.queryRoomUser(toRoomId);
            if(resultInfo.getSuccess()){
                List<User> userList = (List<User>) resultInfo.getData();
                for (User user : userList) {
                    if (!user.getId().equals(loginUser.getId())){
                        onlineUsers.get(toRoomId).session.getBasicRemote().sendText(resultMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        Integer userId = (Integer) httpSession.getAttribute("loginUser");
        //从容器中删除指定的用户
        onlineUsers.remove(userId);
        MessageUtils.getMessage(true,null,getAllIds());
    }
}
