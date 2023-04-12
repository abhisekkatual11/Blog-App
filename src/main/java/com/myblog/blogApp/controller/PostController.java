package com.myblog.blogApp.controller;
import com.myblog.blogApp.payload.PostDto;
import com.myblog.blogApp.payload.PostResponse;
import com.myblog.blogApp.service.PostService;
import com.myblog.blogApp.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PostController {

    private PostService postSer;

    public PostController(PostService postSer) {
        this.postSer = postSer;
    }

    //create Posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    //localhost:8080/api/post
    public ResponseEntity<Object> createPosts(@Valid @RequestBody PostDto postDto, BindingResult bindingRes){

        if(bindingRes.hasErrors()){
            return new  ResponseEntity<>(
                    bindingRes.getFieldError().getDefaultMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(postSer.createPosts(postDto),HttpStatus.OK);
    }

    //get all Posts
    //localhost:8080/api/post
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false)String sortDir
    ){
        PostResponse allPosts = postSer.findAllPosts(pageNo, pageSize, sortBy, sortDir);
        return allPosts;
    }

    //delete Posts
    //localhost:8080/api/post/2
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id")Long id){
        postSer.deleteById(id);
        return new ResponseEntity<>("Record Successfully deleted",HttpStatus.OK);
    }


    //findById
    //localhost:8080/api/post/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getByPostId(@PathVariable("id")Long id){
        PostDto ddto = postSer.findByPostId(id);
        return new ResponseEntity<>(ddto,HttpStatus.FOUND);
    }

    //    //update the Post
    //localhost:8080/api/post/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable("id")Long id,@RequestBody PostDto postDto){
        PostDto dto = postSer.updatePostById(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
