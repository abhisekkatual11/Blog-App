package com.myblog.blogApp.service.impl;
import com.myblog.blogApp.entities.Comment;
import com.myblog.blogApp.entities.Post;
import com.myblog.blogApp.exception.ResourceNotFoundException;
import com.myblog.blogApp.payload.CommentDto;
import com.myblog.blogApp.repository.CommentRepository;
import com.myblog.blogApp.repository.PostRepository;
import com.myblog.blogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepoo;
    private PostRepository postRepoo;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper mapper) {
        this.commentRepoo = commentRepo;
        this.postRepoo = postRepo;
        this.mapper=mapper;
    }


    //CREATE COMMENT BY POST_ID
    //==================================================================================================================
    @Override
    public CommentDto createCommentByPostId(Long postId, CommentDto commentDto) {
        Post post=postRepoo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id",postId)
        );

        //->->->MAPPER CLASS<- <-<-
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        Comment comment = mapToComment(commentDto);
        Comment comment = mapper.map(commentDto, Comment.class);


        comment.setPost(post);

        Comment newComment = commentRepoo.save(comment);

        //->->->MAPPER CLASS<- <-<-
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        CommentDto dto = mapToDto(newComment);
        CommentDto dto = mapper.map(comment, CommentDto.class);
        return dto;
    }







    //UPDATE THE COMMENT
    //==================================================================================================================
    @Override
    public CommentDto updateCommentByPostId(long postId, long commentId, CommentDto commentDto) {
        Post post=postRepoo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id",postId)
        );
        Comment comments=commentRepoo.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",commentId)
        );
//        comments.setPost(post);

        comments.setName(commentDto.getName());
        comments.setEmail(commentDto.getEmail());
        comments.setBody(commentDto.getBody());

        Comment comment = commentRepoo.save(comments);
        return mapper.map(comment,CommentDto.class);
    }

    @Override
    public List<CommentDto> findAllCommentsByPostId(long postId) {
        Post post=postRepoo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id",postId)
        );
        List<Comment> comments = commentRepoo.findByPostId(postId);
        List<CommentDto> dtoList = comments.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public void deleteCommentBYPostId(long postId, long commentId) {
        Post post=postRepoo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id",postId)
        );
        Comment comment=commentRepoo.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("comment","id",commentId)
        );
        commentRepoo.deleteById(commentId);
    }


    //ENTITY TO DTO
    //==================================================================================================================
    private CommentDto mapToDto(Comment newComment) {
        CommentDto commentDto=mapper.map(newComment,CommentDto.class);
//        commentDto.setId(newComment.getId());
//        commentDto.setName(newComment.getName());
//        commentDto.setEmail(newComment.getEmail());
//        commentDto.setBody(newComment.getBody());
        return commentDto;
    }





    //DTO TO ENTITY
    //==================================================================================================================
    private Comment mapToComment(CommentDto commentDto) {
        Comment comment=mapper.map(commentDto,Comment.class);
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
