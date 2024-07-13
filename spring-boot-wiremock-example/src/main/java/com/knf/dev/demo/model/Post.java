package com.knf.dev.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

    public int id;
    public String title;
    public String body;
    public int userId;
}
