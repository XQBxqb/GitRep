package com.itheima.spirngboot_02_ssmp.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.spirngboot_02_ssmp.Service.BookService;
import com.itheima.spirngboot_02_ssmp.DAO.BookMapper;
import com.itheima.spirngboot_02_ssmp.domain.Book;
import org.springframework.stereotype.Service;

/**
 * @author hp
 * @date 2023-01-02 20:34
 * @explain
 */

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper,Book>  implements BookService {
}
