package com.spring.blog.service;

import com.spring.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
   List<CommentDto> getCommentsByPostId(long postId);
   CommentDto getCommentById(long postId, long commentId);
   CommentDto updateCommentById(long postId, long CommentId, CommentDto commentDto);
   void deleteCommentById(long postId, long commentId);
}
