package com.example.iwa_ms_commentaire;

import com.example.iwa_ms_commentaire.Service.CommentService;
import com.example.iwa_ms_commentaire.controllers.CommentController;
import com.example.iwa_ms_commentaire.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment();
        testComment.setCommentId(1);
        testComment.setLocationId(101L);
        testComment.setUserId(202);
        testComment.setCommentText("This is a test comment");
    }

    @Test
    void testGetAllComments() throws Exception {
        when(commentService.findAll()).thenReturn(Arrays.asList(testComment));

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].locationId").value(101))
                .andExpect(jsonPath("$[0].userId").value(202))
                .andExpect(jsonPath("$[0].text").value("This is a test comment"));
    }

    @Test
    void testGetCommentById() throws Exception {
        when(commentService.findById(1)).thenReturn(Optional.of(testComment));

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("This is a test comment"));
    }

    @Test
    void testCreateComment() throws Exception {
        when(commentService.save(any(Comment.class))).thenReturn(testComment);

        String requestBody = """
            {
                "locationId": 101,
                "userId": 202,
                "text": "This is a test comment"
            }
            """;

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("This is a test comment"));
    }

    @Test
    void testDeleteComment() throws Exception {
        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk());
        Mockito.verify(commentService).deleteById(1);
    }

    @Test
    void testGetCommentsByLocationId() throws Exception {
        when(commentService.findByLocationId(101)).thenReturn(Arrays.asList(testComment));

        mockMvc.perform(get("/comments/location/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].locationId").value(101))
                .andExpect(jsonPath("$[0].text").value("This is a test comment"));
    }
}
