package com.example.iwa_ms_commentaire.Repository;

import com.example.iwa_ms_commentaire.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // Définis des méthodes spécifiques si nécessaire
}

