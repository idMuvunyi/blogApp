package com.spring.blog.service.impl;

import com.spring.blog.exception.BlogAPIException;
import com.spring.blog.exception.ResourceNotFoundException;
import com.spring.blog.model.Comment;
import com.spring.blog.model.Post;
import com.spring.blog.payload.CommentDto;
import com.spring.blog.repository.CommentRepository;
import com.spring.blog.repository.PostRepository;
import com.spring.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {
    // get instance to the corresponding repository(DAO)
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImp(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id before comment
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //set post to comment entity
        comment.setPost(post);

        // save comment entity to db
        Comment newComment = commentRepository.save(comment);

        // map entity to DTO
        CommentDto commentResponse =  mapToDto(newComment);

        return commentResponse;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // get comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentResponse = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

        return commentResponse;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        // retrieve post with specific id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment with specific id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId() != (post.getId())){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        CommentDto commentResponse = mapToDto(comment);
        return commentResponse;
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment with specific id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(comment.getPost().getId() != (post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);

        Comment updatedComment = commentRepository.save(comment);
        CommentDto commentResponse = mapToDto(updatedComment);

        return commentResponse;
    }


    // convert Entity/Model to DTO
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());


        return commentDto;
    }
    // convert DTO to Entity/Model

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;


    }
}
