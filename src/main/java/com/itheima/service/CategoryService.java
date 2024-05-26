package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

/**
 * ClassName: CategoryService
 * Package: com.itheima.service
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/16 15:46
 * @Version 1.0
 */
public interface CategoryService {
    // 新增文章分类
    void add(Category category);

    // 获取文章分类列表
    List<Category> list();

    // 获取文章分类详情
    Category findByID(Integer id);

    // 更新文章分类
    void update(Category category);

    // 删除文章分类
    void delete(Integer id);
}
