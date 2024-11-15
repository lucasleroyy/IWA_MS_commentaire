package com.example.iwa_ms_commentaire.Service;

import com.example.iwa_ms_commentaire.models.Comment;
import com.example.iwa_ms_commentaire.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findById(Integer id) {
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findByLocationId(Integer locationId) {
        return commentRepository.findByLocationId(locationId);
    }
}
