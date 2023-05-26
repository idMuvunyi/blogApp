package com.spring.blog.controller;

import com.spring.blog.payload.CommentDto;
import com.spring.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postId") long postId){
        return  commentService.getCommentsByPostId(postId);
    }
    @GetMapping("posts/{postId}/comments/{id}")
    public CommentDto getCommentsByPostId(@PathVariable(name = "postId") long postId, @PathVariable(name = "id") long commentId){
        return  commentService.getCommentById(postId, commentId);
    }
}
