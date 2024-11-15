package com.example.iwa_ms_commentaire.controllers;

import com.example.iwa_ms_commentaire.models.Comment;
import com.example.iwa_ms_commentaire.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Integer id) {
        return commentService.findById(id);
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteById(id);
    }
    @GetMapping("/location/{locationId}")
    public List<Comment> getCommentsByLocationId(@PathVariable Integer locationId) {
        return commentService.findByLocationId(locationId);
    }
}
