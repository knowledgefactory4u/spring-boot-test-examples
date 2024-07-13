package com.knf.dev.demo.client;

import com.knf.dev.demo.model.Post;

public interface PostClient {

    Post findById(int id);

}