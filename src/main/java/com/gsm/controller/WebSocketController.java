package com.gsm.controller;

import com.gsm.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("webSocket")
public class WebSocketController {

    @GetMapping("/socket/{cid}")
    public String socket(@PathVariable("cid") String cid){
        System.out.println("--socket接口--");
        pushToWeb(cid,"我是客户端的信息");
        return "success";
    }

    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public Object pushToWeb(@PathVariable("cid") String cid,String msg){
        System.out.println("--pushToWeb接口--");
        WebSocketServer.sendInfoAll(msg,cid);
        return "aa";
    }
}
