package com.course.server;

import com.ourse.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "这是我全部的post请求")
@RequestMapping("/v1")
public class MyPostMethod {

    //这个变量用来装cookies
    public static Cookie cookie;

    //用户登陆成功获取到cookies，然后再访问其他接口获取到列表
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登陆接口，成功后获取cookies信息", httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "PassWord", required = true) String PassWord) {
        if (userName.equals("zhangsan") & PassWord.equals("123456")) {
            cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "恭喜你登陆成功";
        }
        return "用户名或密码不正确";
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                              @RequestBody User u) {
        User user;
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies是否合法
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && u.getUserName().equals("zhangsan")
                    && u.getPassWord().equals("123456")
            ) {
                user = new User();
                user.setName("李四");
                user.setAge("18");
                user.setSex("男");
                return user.toString();
            }
        }
        return "参数不合法";

    }
}
