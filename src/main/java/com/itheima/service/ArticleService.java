package com.itheima.service;

import com.itheima.pojo.Article;
import com.itheima.pojo.PageBean;
import org.springframework.stereotype.Service;

/**
 * ClassName: ArticleService
 * Package: com.itheima.service
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/16 17:47
 * @Version 1.0
 */
public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, String categoryId, String state);

    Article findById(Integer id);

    void update(Article article);

    void delete(Integer id);
}
