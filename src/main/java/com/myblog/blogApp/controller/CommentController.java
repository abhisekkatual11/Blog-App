package com.myblog.blogApp.controller;
import com.myblog.blogApp.payload.CommentDto;
import com.myblog.blogApp.service.CommentService;
import com.myblog.blogApp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentSer;
    private PostService postSer;

    public CommentController(CommentService commentSer, PostService postSer) {
        this.commentSer = commentSer;
        this.postSer = postSer;
    }

    //localhost:8080/api/posts/{postId}/comments
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId")Long postId , @RequestBody CommentDto commentDto){
        CommentDto dto = commentSer.createCommentByPostId(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



    //UPDATE COMMENT BY POST ID
    //localhost:8080/api/posts/{postId}/comments/{commentId}
    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentByPostId(
            @PathVariable("postId")long postId,
            @PathVariable("commentId")long commentId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentSer.updateCommentByPostId(postId, commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }





    //FIND ALL COMMENTS FOR A PARITICULAR POST_ID
    //==================================================================================================================
    //localhost:8080/api/posts/{postId}/comments
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> finAllCommentsByPostId(@PathVariable("postId")long postId){
        List<CommentDto> dtoList = commentSer.findAllCommentsByPostId(postId);
        return dtoList;
    }



    //DELETE COMMENT BY POST_ID
    //localhost:8080/api/posts/{postId}/comments/{commentId}
    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentByPostId(
            @PathVariable("postId")long postId,
            @PathVariable("commentId")long commentId
    ){
        commentSer.deleteCommentBYPostId(postId,commentId);
        return new ResponseEntity<>("Successfully Delete the comment",HttpStatus.OK);
    }
}
