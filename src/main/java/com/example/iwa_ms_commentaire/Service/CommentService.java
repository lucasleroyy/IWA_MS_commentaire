package com.example.iwa_ms_commentaire.Service;

import com.example.iwa_ms_commentaire.models.Comment;
import com.example.iwa_ms_commentaire.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentService {

    private final RestTemplate restTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository repository, RestTemplate restTemplate) {
        this.commentRepository = repository;
        this.restTemplate = restTemplate;
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Optional<Comment> findById(Integer id) {
        return commentRepository.findById(id);
    }

    public Comment save(Comment comment) {
        // Enregistrer le commentaire dans la base de données
        Comment savedComment = commentRepository.save(comment);

        // Appeler le service de notifications
        sendCommentNotification(savedComment);

        return savedComment;
    }

    // Méthode pour envoyer une notification pour un commentaire
    private void sendCommentNotification(Comment comment) {
        if (comment == null || comment.getLocationId() == null || comment.getUserId() == null) {
            throw new IllegalArgumentException("Les données du commentaire sont invalides pour l'envoi de notification.");
        }

        // Construire le corps de la requête pour la notification
        Map<String, Long> requestBody = Map.of(
                "locationId", comment.getLocationId()
        );

        // URL du service de notifications
        String notificationUrl = "http://host.docker.internal:8085/notifications/create/comment";

        try {
            // Appeler la route POST du ms_notification
            restTemplate.postForEntity(notificationUrl, requestBody, Void.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel au service de notifications : " + e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findByLocationId(Integer locationId) {
        return commentRepository.findByLocationId(locationId);
    }

    public Comment updateComment(Integer id, Comment updatedComment) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment comment = existingComment.get();
            comment.setCommentText(updatedComment.getCommentText());
            comment.setRating(updatedComment.getRating());
            comment.setLocationId(updatedComment.getLocationId());
            comment.setUserId(updatedComment.getUserId());
            return commentRepository.save(comment);
        } else {
            throw new IllegalArgumentException("Commentaire avec l'ID " + id + " non trouvé.");
        }
    }
}
