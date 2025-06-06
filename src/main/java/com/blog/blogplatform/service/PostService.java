package com.blog.blogplatform.service;

import com.blog.blogplatform.exception.ResourceNotFoundException;
import com.blog.blogplatform.model.Post;
import com.blog.blogplatform.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post getPostByIdOrThrow(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


}
