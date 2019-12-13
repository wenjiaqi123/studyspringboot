package com.gsm.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket/{sid}")
public class WebSocketServer {
    /**
     *  静态变量，用来记录当前在线连接数
     */
    private static int onlineCount = 0;
    /**
     * concurrent包线程安全 Set，用来存放每个客户端对应的 WebSocket 对象
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某一个客户端的连接会话，通过该 Session 给客户端发送数据
     */
    private Session session;

    private String sid = "";

    /**
     * socket连接 建立成功时调用方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新窗口开始监听:\t" + sid + "\t当前在线连接数为:\t" + getOnlineCount());
        this.sid = sid;
        try {
            sendMsg("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ocket连接 关闭时调用方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一个连接关闭！当前在线连接数为:\t" + getOnlineCount());
    }

    /**
     * 服务端主动推送
     *
     * @param msg
     * @throws IOException
     */
    public void sendMsg(String msg) throws IOException {
        //调用 JDK 提供的方法
        this.session.getBasicRemote().sendText(msg);
    }

    public static void sendInfoAll(String msg, @PathParam("sid") String sid) {
        webSocketSet.forEach(i -> {
            try {
                if (sid == null) {
                    i.sendMsg(msg);
                } else if (i.sid.equals(sid)) {
                    i.sendMsg(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    //获取当前在线连接数
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    //给当前在线连接数 +1
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    //给当前在线连接数 -1
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
