package com.example.iwa_ms_commentaire.Repository;

import com.example.iwa_ms_commentaire.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByLocationId(Integer locationId);
}


