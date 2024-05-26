package com.itheima.service.impl;

import com.itheima.mapper.CategoryMapper;
import com.itheima.pojo.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * ClassName: CategoryServiceImpl
 * Package: com.itheima.service.impl
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/16 15:47
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        // 补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userID = (Integer)claims.get("id");
        category.setCreateUser(userID);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userID = (Integer)claims.get("id");
        return categoryMapper.list(userID);
    }

    @Override
    public Category findByID(Integer id) {
        return categoryMapper.findByID(id);
    }

    @Override
    public void update(Category category) {
        // 补充属性值
        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
