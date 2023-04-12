package com.myblog.blogApp.service;

import com.myblog.blogApp.payload.PostDto;
import com.myblog.blogApp.payload.PostResponse;

public interface PostService {
    public PostDto createPosts(PostDto postDto);

    public PostResponse findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
//
    public void deleteById(Long id);
//
    public PostDto findByPostId(Long id);

    public PostDto updatePostById(Long id,PostDto postDto);
}