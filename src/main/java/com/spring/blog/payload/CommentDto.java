package com.spring.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    // title should not be empty / null and mst have at least 2 chars
    @NotEmpty
    @Size(min = 3, message = "Name should be at least 3 characters")
    private String name;
    @NotEmpty
    @Email(message = "Should provide valid email address")
    private String email;
    @NotEmpty
    @Size(min = 3, message = "Comment should be at least 10 characters")
    private String body;
}
