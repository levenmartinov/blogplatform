package com.blog.blogplatform.controller;

import com.blog.blogplatform.dto.PostDto;
import com.blog.blogplatform.model.Post;
import com.blog.blogplatform.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtos = postService.getAllPosts()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDtos);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        Post post = convertToEntity(postDto);
        Post savedPost = postService.createPost(post);
        return ResponseEntity.ok(convertToDto(savedPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        Post post = postService.getPostByIdOrThrow(id);
        return ResponseEntity.ok(convertToDto(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        Post existingPost = postService.getPostByIdOrThrow(id); //We get the Post object directly
        existingPost.setTitle(postDto.getTitle());
        existingPost.setContent(postDto.getContent());
        Post savedPost = postService.createPost(existingPost);
        return ResponseEntity.ok(convertToDto(savedPost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Post post = postService.getPostByIdOrThrow(id);
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }


    //Entity -> DTO
    private PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        return postDto;
    }

    //DTO -> Entity
    private Post convertToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return post;
    }


}
