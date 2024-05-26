package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: UserController
 * Package: com.itheima.controller
 * Description: 注册、登录接口，注册、登录请求做出响应，需要进行具体操作时，调用Service层的业务代码。
 *
 * @Author JinJin
 * @Create 2024/5/13 20:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Validated// 参数校验@Pattern所需要的注解
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 注册请求：对用户名和密码进行参数校验
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User u = userService.findByUsername(username);
        if (u == null) {
            // 没有占用，进行注册
            userService.register(username, password);
            return Result.success();
        } else {
            // 占用了，返回错误信息
            return Result.error("用户名已被占用");
        }
    }

    // 登录请求：对用户名和密码进行参数校验
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        // 判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误或不存在");
        }
        // 判断密码是否正确：对比数据库中的密码是暗文
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // 登录成功，给账号生成token
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            // 将token存储到redis中
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        // 登录失败
        return Result.error("密码错误！");
    }

    // 获取用户信息请求：通过token获取相关信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name="Authorization") String token*/) {
    /* 优化：解析代码与拦截器重复，将验证工作和用户名的获取操作统一交给拦截器
        // token从请求头中的Authorization属性获取，解析token获取载荷信息
        Map<String, Object> claims = JwtUtil.parseToken(token);
        // 从载荷信息中获取用户名
        String username = (String)claims.get("username");
    */
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String)claims.get("username");

        // 根据用户名查询用户
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    // 更新用户信息请求
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    // 更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    // 更新用户密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params, @RequestHeader("Authorization") String token) {
        // 校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺失必要的参数。");
        }

        // 校验原密码是否正确
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String)claims.get("username");
        User loginUser = userService.findByUsername(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码填写不正确。");
        }

        // 校验newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次新密码填写不一致。");
        }

        // 调用service完成密码更新
        userService.updatePwd(newPwd);

        // 删除redis中旧密码对应的token
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.getOperations().delete(token);

        return Result.success();
    }
}
