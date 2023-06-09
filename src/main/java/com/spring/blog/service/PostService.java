package com.spring.blog.service;

import com.spring.blog.payload.PostDto;
import com.spring.blog.payload.PostResponse;

import java.util.List;


public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);
    PostDto updatePostById(PostDto postDto, long id);
    void deletePostById(long id);
    List<PostDto> getPostByCategory(Long id);
}
