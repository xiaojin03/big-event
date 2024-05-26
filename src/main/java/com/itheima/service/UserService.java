package com.itheima.service;

import com.itheima.pojo.User;

/**
 * ClassName: UserService
 * Package: com.itheima.service
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/13 20:12
 * @Version 1.0
 */
public interface UserService {
    // 根据用户名查询用户
    User findByUsername(String username);

    // 注册
    void register(String username, String password);

    // 更新用户信息
    void update(User user);

    // 更新用户头像
    void updateAvatar(String avatarUrl);

    // 更新用户密码
    void updatePwd(String newPwd);
}
