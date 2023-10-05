package com.fast.projectboard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;


    ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][Get] 게시글 리스트 페이지 - 정상 호출")
    @Test
    public void 게시글리스호출() throws Exception {
        mvc.perform(get("/articles"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML))
            .andExpect(model().attributeExists("articles"));
    }

    @DisplayName("[view][Get] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void 게시글상세호출() throws Exception {

        mvc.perform(get("/articles/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.TEXT_HTML))
            .andExpect(model().attributeExists("article"));

    }

    @DisplayName("[view][Get] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void 게시글검색페이지호출() throws Exception {
        mvc.perform(get("/articles/search"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @DisplayName("[view][Get] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void 게시글해_시태그검색페이지호출() throws Exception {
        mvc.perform(get("/articles/search-hashtag"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));

    }

}