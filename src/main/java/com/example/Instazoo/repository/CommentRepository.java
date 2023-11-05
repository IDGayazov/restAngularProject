package com.example.Instazoo.repository;

import com.example.Instazoo.entity.Comment;
import com.example.Instazoo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findAllByPost(Post post);

    Comment findByIdAndUserId(Long id, Long userId);
}
