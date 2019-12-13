package com.gsm.controller;

import com.gsm.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Api() 使用在 类 上，说明该 类 的作用
 */

@RestController
@RequestMapping("swagger")
@Api("用来测试 swagger")
public class SwaggerController {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @ApiOperation() 使用在 方法 上，说明该 方法 的作用
     * notes 可以添加说明，
     */
    @ApiOperation(value = "根据id获取用户信息", notes = "id 的传值为 1 或者 2")
    @GetMapping("getUserInfoById")
    public String getUserInfoById(Integer id) {
        if (id == 1) {
            return "在下是你爸爸";
        } else if (id == 2) {
            return "巧了。我也是你爸爸";
        }
        return "sss";
    }


    @ApiOperation(value = "修改用户密码",notes = "根据用户id修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id",value = "用户id",required = true,dataType = "int"),
            @ApiImplicitParam(paramType = "query",name = "pwd",value = "用户密码",required = true,dataType = "String")
    })
    @PutMapping("updatePwdById")
    public String updatePwdById(Integer id, String pwd) {
        if (id == 1) {
            return "已经修改 用户1 的密码";
        } else if (id == 2) {
            return "已经修改 用户2 的密码";
        }
        return "密码修改失败";
    }


    @ApiOperation("插入用户信息")
    @PostMapping("insertUser")
    public Map<String,Object> insertUser(User user){
        Map<String,Object> map = new HashMap<>();
        map.put("message","success");
        map.put("user",user);
        return map;
    }

    /**
     * @ApiImplicitParam
     * paramType
     *      path    请求url路径中    示例中 name 与 url {} 中一致，和 形参 userId 没关系
     *      query   请求参数
     *      header  请求头
     *      body    请求体（不常用）
     *      form    （不常用）
     * @param userId
     * @return
     */
    @ApiOperation(value = "删除用户信息",notes = "根据用户id")
    @ApiImplicitParam(paramType = "path",name = "id",value = "用户id",required = true,type = "int")
    @DeleteMapping("deleteUserById/{id}")
    public User deleteUserById(@PathVariable("id") Integer userId){
        /*User user = User.builder()
                .id(userId)
                .userName("闻家奇")
                .userAge(18)
                .build();*/
        User user = new User();
        return user;
    }
}
