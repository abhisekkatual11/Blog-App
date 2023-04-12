package com.myblog.blogApp.service.impl;
import com.myblog.blogApp.entities.Post;
import com.myblog.blogApp.exception.ResourceNotFoundException;
import com.myblog.blogApp.payload.PostDto;
import com.myblog.blogApp.payload.PostResponse;
import com.myblog.blogApp.repository.PostRepository;
import com.myblog.blogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper=mapper;
    }


    @Override
    public PostDto createPosts(PostDto postDto) {
        Post post=mapper.map(postDto,Post.class);
        Post newPost = postRepo.save(post);
        PostDto dto=mapper.map(newPost,PostDto.class);
        return dto;
    }

    @Override
    public PostResponse findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> page=postRepo.findAll(pageable);
        List<Post> content = page.getContent();

        List<PostDto> dtoList = content.stream().map(x -> mapToDto(x)).collect(Collectors.toList());

        PostResponse postRes=new PostResponse();
        postRes.setGetAllContents(dtoList);
        postRes.setPageNo(page.getNumber());
        postRes.setPageSize(page.getSize());
        postRes.setTotalElements(page.getTotalElements());
        postRes.setTotalPages(page.getTotalPages());
        postRes.setLastPage(page.isLast());
        return postRes;
    }

    @Override
    public void deleteById(Long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id",id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public PostDto findByPostId(Long id) {
        Post post=postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("post","id",id)
        );
        return mapToDto(post);
    }
//
    @Override
    public PostDto updatePostById(Long id,PostDto postDto) {
        Post post=postRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("post","id",id)
        );
        post.setLikes(postDto.getLikes());
        post.setTitle(postDto.getTitle());
        post.setContents(postDto.getContents());
        post.setDescription(postDto.getDescription());
        Post newPost = postRepo.save(post);

        return mapper.map(newPost,PostDto.class);
    }
//
    private PostDto mapToDto(Post newPost) {
        PostDto postDto=mapper.map(newPost,PostDto.class);
//        postDto.setId(newPost.getId());
//        postDto.setLikes(newPost.getLikes());
//        postDto.setTitle(newPost.getTitle());
//        postDto.setContents(newPost.getContents());
//        postDto.setDescription(newPost.getDescription());
        return postDto;
    }

    private Post mapToPost(PostDto dto) {
        Post post=mapper.map(dto,Post.class);
//        post.setLikes(dto.getLikes());
//        post.setTitle(dto.getTitle());
//        post.setContents(dto.getContents());
//        post.setDescription(dto.getDescription());
        return post;
    }
}
