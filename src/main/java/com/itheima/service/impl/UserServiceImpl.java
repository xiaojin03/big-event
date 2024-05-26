package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * ClassName: UserServiceImpl
 * Package: com.itheima.service.impl
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/13 20:12
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 给明文密码加密
        String md5String = Md5Util.getMD5String(password);
        // 再存储到数据库中
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        // 添加更新时间
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer)claims.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String newPwd) {
        // 给新密码加密
        String md5String = Md5Util.getMD5String(newPwd);

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer)claims.get("id");
        userMapper.updatePwd(md5String, id);
    }
}
