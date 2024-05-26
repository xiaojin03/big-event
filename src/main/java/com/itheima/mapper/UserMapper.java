package com.itheima.mapper;

import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * ClassName: UserMapper
 * Package: com.itheima.mapper
 * Description: 此处编写SQL语句对数据库进行操作
 *
 * @Author JinJin
 * @Create 2024/5/13 20:12
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    // 注册添加用户
    @Insert("insert into user(username, password, create_time, update_time)" +
            " values(#{username}, #{password}, now(), now())")
    void add(String username, String password);

    // 更新用户信息
    @Update("update user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime} where id=#{id}")
    void update(User user);

    // 更新用户头像
    @Update("update user set user_pic=#{avatarUrl}, update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    // 更新用户密码
    @Update("update user set password=#{password}, update_time=now() where id=#{id}")
    void updatePwd(String password, Integer id);
}
