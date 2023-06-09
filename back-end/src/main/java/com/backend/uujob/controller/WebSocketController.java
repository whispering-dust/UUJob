package com.backend.uujob.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@ServerEndpoint("/websocket/{userId}")
@EnableScheduling
public class WebSocketController {
    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        //把session加入连接池
        SessionPool.sessions.put(userId, session);
        //        查询数据业务代码
        xxxxxxxxxxx;
        //转换为json返回
        String json= JSONUtil.toJsonStr(orders);
        SessionPool.sendMessage(userId,json);
        log.info("Json:{}",json);
        log.info("建立连接成功,id:"+userId);
    }
    //关闭会话的时候
    @OnClose
    public void onClose(Session session) throws IOException {
        SessionPool.close(session.getId());
        log.info("断开连接成功,id:"+session.getId());
        session.close();
    }

    //接收到客户端的消息后调用
    @OnMessage
    public void onMessage(String message, Session session) {
        //接收客户端数据,更新数据库
        xxxxxxxxx;
    }
    //    定时任务,每秒查询一次数据库
    @Scheduled(fixedDelay = 1000)
    public void pollDatabaseForNewData() {
        //        查询数据
        xxxxxxxxx;
        //将数据转换为json发送到前端
        String json= JSONUtil.toJsonStr(xxxxx);
        SessionPool.sendMessage(json);
    }

}
