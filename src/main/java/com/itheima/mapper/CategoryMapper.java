package com.itheima.mapper;

import com.itheima.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ClassName: CategoryMapper
 * Package: com.itheima.mapper
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/16 15:47
 * @Version 1.0
 */
@Mapper
public interface CategoryMapper {

    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            " values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Select("select * from category where create_user=#{userID}")
    List<Category> list(Integer userID);

    @Select("select * from category where id=#{id}")
    Category findByID(Integer id);

    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);

    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
