package com.myblog.blogApp.repository;
import com.myblog.blogApp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    public List<Comment> findByPostId(Long postId);
}
