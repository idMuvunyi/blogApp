package com.spring.blog.repository;

import com.spring.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // create custom method to find all comments by post id in DB
     List<Comment> findByPostId(long postId);

}
