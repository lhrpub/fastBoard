package com.fast.projectboard.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@DisplayName("DATA REST api 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    @DisplayName("[api] 게시글 리스트 조회")
    void 게시글_조회() throws Exception {
        mvc.perform(get("/api/articles"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void 게시글단건_조회() throws Exception {

        mvc.perform(get("/api/articles/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
        ;
    }

    @DisplayName("[api] 댓글 리스트 조회")
    @Test
    void 댓글리스트_조회() throws Exception {

        mvc.perform(get("/api/articleComments"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
        ;
    }

    @DisplayName("[api] 게시글 댓글 리스트 조회")
    @Test
    void 게시글_댓글리스트_조회() throws Exception {

        mvc.perform(get("/api/articles/1/articleComments"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
        ;
    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void 댓글단건_조회() throws Exception {

        mvc.perform(get("/api/articleComments/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
        ;
    }
}
