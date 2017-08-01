package com.lance.mongo.test;

import java.io.Serializable;

/**
 * Author Lance.
 * Date: 2017-07-19 15:53
 * Desc:
 */
public class User implements Serializable {

    private Integer id;

    private int age;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
