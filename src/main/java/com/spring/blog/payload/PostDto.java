package com.spring.blog.payload;


import com.spring.blog.model.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Schema(
        description = "PostDto model information"
)
public class PostDto {
    private long id;
    // title should not be empty / null and must have at least 2 chars
    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @Schema(
            description = "Blog Post Content"
    )
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog Post Category id"
    )
    private Long categoryId;

}
