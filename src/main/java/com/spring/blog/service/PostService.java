package com.spring.blog.service;

import com.spring.blog.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
