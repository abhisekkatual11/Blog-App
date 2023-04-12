package com.myblog.blogApp.service;

import com.myblog.blogApp.payload.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createCommentByPostId(Long postId, CommentDto commentDto);

    public CommentDto updateCommentByPostId(long postId, long commentId, CommentDto commentDto);

    public List<CommentDto> findAllCommentsByPostId(long postId);

    public void deleteCommentBYPostId(long postId, long commentId);
}
