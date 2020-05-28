package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import com.example.demo.utils.Md5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 关注微信公众号《web项目开发》
 */

@Api(description = "用户接口")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("test")
    @ResponseBody
    public String getData() {
        return "String";
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> register(@RequestParam String userName,
                                        @RequestParam String passwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passwd)) {
            map.put("msg", "用户名和密码不能为空");
            return map;
        }
        // 验证用户名是否已经注册
        User exsitUser = userService.selectByName(userName);
        if (exsitUser != null) {
            map.put("msg", "该用户名已存在!");
            return map;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        User user = new User();
        user.setUserName(userName);
        user.setRegistTime(format);
        user.setStatus("1");
        user.setPasswd(Md5Util.MD5(passwd) + userName);
        int count = userService.insert(user);
        System.out.println(count);
        if (count != 1) {
            map.put("msg", "注册失败");
            return map;
        }
        map.put("msg", "注册成功");
        return map;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CrossOrigin
    public Map<String, Object> login(@RequestParam String userName,
                                     @RequestParam String passwd,
                                     HttpSession session,
                                     HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        User exsitUser = userService.selectByName(userName);
        if (exsitUser == null) {
            map.put("msg", "该用户未注册");
            return map;
        }
        if (!exsitUser.getPasswd().equals(Md5Util.MD5(passwd) + userName)) {
            map.put("msg", "密码错误,请重新输入");
            return map;
        }
        session.setAttribute("loginUser", exsitUser);
        map.put("msg", "登录成功");
        return map;
    }
}
