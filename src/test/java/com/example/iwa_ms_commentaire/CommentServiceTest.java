package com.example.iwa_ms_commentaire;

import com.example.iwa_ms_commentaire.Repository.CommentRepository;
import com.example.iwa_ms_commentaire.Service.CommentService;
import com.example.iwa_ms_commentaire.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private RestTemplate restTemplate;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(commentRepository, restTemplate);

        testComment = new Comment();
        testComment.setCommentId(1);
        testComment.setLocationId(101L);
        testComment.setUserId(202);
        testComment.setCommentText("This is a test comment");
    }

    @Test
    void testFindAll() {
        when(commentRepository.findAll()).thenReturn(Arrays.asList(testComment));

        List<Comment> comments = commentService.findAll();
        assertEquals(1, comments.size());
        assertEquals("This is a test comment", comments.get(0).getCommentText());
    }

    @Test
    void testFindById() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(testComment));

        Optional<Comment> comment = commentService.findById(1);
        assertEquals("This is a test comment", comment.get().getCommentText());
    }

    @Test
    void testSave() {
        when(commentRepository.save(testComment)).thenReturn(testComment);

        Comment savedComment = commentService.save(testComment);
        assertEquals("This is a test comment", savedComment.getCommentText());
        verify(restTemplate).postForEntity(anyString(), any(), any());
    }

    @Test
    void testDeleteById() {
        doNothing().when(commentRepository).deleteById(1);

        commentService.deleteById(1);
        verify(commentRepository, times(1)).deleteById(1);
    }

    @Test
    void testFindByLocationId() {
        when(commentRepository.findByLocationId(101)).thenReturn(Arrays.asList(testComment));

        List<Comment> comments = commentService.findByLocationId(101);
        assertEquals(1, comments.size());
        assertEquals(101, comments.get(0).getLocationId());
    }
}
