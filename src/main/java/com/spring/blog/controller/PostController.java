package com.spring.blog.controller;

import com.spring.blog.payload.PostDto;
import com.spring.blog.payload.PostResponse;
import com.spring.blog.service.PostService;
import com.spring.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/posts")
@Tag(
        name = "CRUD for blog post resource APIs"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post endpoint
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create Blog Post",
            description = "Request to add new post to the database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 created"
    )
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // Get all blog post
    @Operation(
            summary = "Get all Blog Posts",
            description = "Request to get all posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 ok"
    )
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false ) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    // Get single post by id
    @Operation(
            summary = "Get Blog Post",
            description = "Request to get a specific post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 ok"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    // update blog post
    @SecurityRequirement(
             name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")

    @Operation(
            summary = "Update Blog Post",
            description = "Request to update a specific post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 ok"
    )
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable("id") long postId){
        return new ResponseEntity<>(postService.updatePostById(postDto, postId), HttpStatus.OK);
    }

    // delete specific blog post
    @SecurityRequirement(
            name = "Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")

    @Operation(
            summary = "Delete Blog Post",
            description = "Request to delete a specific post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 ok"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post deleted successfully!", HttpStatus.OK);
    }

    // Get all posts by category
    @GetMapping("/filter")
    public ResponseEntity<List<PostDto>> getPostByCategory(@RequestParam(value = "category", required = true) Long id){
        List<PostDto> postResponse = postService.getPostByCategory(id);
        return ResponseEntity.ok(postResponse);
    }

}
