package com.itheima.spirngboot_02_ssmp.DAO;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.spirngboot_02_ssmp.domain.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hp
 * @date 2023-01-02 20:31
 * @explain
 */

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
