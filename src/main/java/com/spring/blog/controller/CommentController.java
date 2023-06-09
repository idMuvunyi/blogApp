package com.spring.blog.controller;

import com.spring.blog.payload.CommentDto;
import com.spring.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @Valid @RequestBody CommentDto commentDto) {
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

    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(name = "postId") long postId, @PathVariable(name = "id") long commentId, @Valid @RequestBody CommentDto commentDto){
        CommentDto comment = commentService.updateCommentById(postId, commentId, commentDto);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    // Delete post's comment by id
    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "postId") long postId, @PathVariable(name = "id") long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment is deleted successfully!", HttpStatus.OK);
    }
}
