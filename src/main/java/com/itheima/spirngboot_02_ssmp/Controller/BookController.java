package com.itheima.spirngboot_02_ssmp.Controller;

import com.itheima.spirngboot_02_ssmp.Service.BookService;
import com.itheima.spirngboot_02_ssmp.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hp
 * @date 2023-01-02 21:04
 * @explain
 */

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public List<Book> getAll(){
        return bookService.list();
    }

    @PostMapping
    public Boolean save(@RequestBody  Book book){
        return bookService.save(book);
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable  Integer id){
        return bookService.removeById(id);
    }
    @PutMapping
    public Boolean update(@RequestBody Book book){
        return bookService.updateById(book);
    }
    @GetMapping("{id}")
    public Book getById(@PathVariable  Integer id){
        return bookService.getById(id);
    }
}
