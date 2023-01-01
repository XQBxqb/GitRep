package com.itheima.spirngboot_02_ssmp.domain;

import lombok.Data;

/**
 * @author hp
 * @date 2023-01-02 16:37
 * @explain
 */

@Data
public class Book {
   private  Integer id;
   private String  type;
   private String name;
   private String description;
}
