package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ArticleMapper;
import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import com.itheima.service.ArticleService;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ArticleServiceImpl
 * Package: com.itheima.service.impl
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/16 17:50
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        // 补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userID = (Integer)claims.get("id");
        article.setCreateUser(userID);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state) {
        // 构建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        // 开启分页查询，使用PageHelper插件
        PageHelper.startPage(pageNum, pageSize);

        // 调用Mapper完成查询
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer userID = (Integer) claims.get("id");
        List<Article> as = articleMapper.list(userID,categoryId,state);

        // PageHelper插件中，类型Page提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) as;

        // 把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());

        return pb;
    }

    @Override
    public Article findById(Integer id) {
        return articleMapper.findById(id);
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
