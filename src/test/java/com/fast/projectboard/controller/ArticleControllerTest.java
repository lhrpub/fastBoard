package com.fast.projectboard.controller;

import com.fast.projectboard.config.SecurityConfig;
import com.fast.projectboard.domain.type.SearchType;
import com.fast.projectboard.dto.ArticleCommentDto;
import com.fast.projectboard.dto.ArticleWithCommentsDto;
import com.fast.projectboard.dto.UserAccountDto;
import com.fast.projectboard.service.ArticleService;
import com.fast.projectboard.service.PaginationService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;

    @MockBean private ArticleService articleService;
    @MockBean private PaginationService paginationService;


    ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][Get] 게시글 리스트 페이지 - 정상 호출")
    @Test
    public void 게시글리스호출() throws Exception {

        given(articleService.searchArticles(eq(null),eq(null), any(Pageable.class))).willReturn(
            Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(),anyInt())).willReturn(List.of(0,1,2,3,4));

        mvc.perform(get("/articles"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/index"))
            .andExpect(model().attributeExists("articles"))
            .andExpect(model().attributeExists("paginationBarNumbers"));

        then(articleService).should().searchArticles(eq(null),eq(null), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(),anyInt());
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 검색어와 함께 호출")
    @Test
    public void givenSearchKeyword_whenSearchingArticlesView_thenReturnsArticlesView() throws Exception {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        given(articleService.searchArticles(eq(searchType), eq(searchValue), any(Pageable.class))).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mvc.perform(
                get("/articles")
                    .queryParam("searchType", searchType.name())
                    .queryParam("searchValue", searchValue)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/index"))
            .andExpect(model().attributeExists("articles"))
            .andExpect(model().attributeExists("searchTypes"));
        then(articleService).should().searchArticles(eq(searchType), eq(searchValue), any(Pageable.class));
        then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    void givenPagingAndSortingParams_whenSearchingArticlesPage_thenReturnsArticlesPage() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Order.desc(sortName)));
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(articleService.searchArticles(null, null, pageable)).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages())).willReturn(barNumbers);

        // When & Then
        mvc.perform(
                get("/articles")
                    .queryParam("page", String.valueOf(pageNumber))
                    .queryParam("size", String.valueOf(pageSize))
                    .queryParam("sort", sortName + "," + direction)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/index"))
            .andExpect(model().attributeExists("articles"))
            .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(articleService).should().searchArticles(null, null, pageable);
        then(paginationService).should().getPaginationBarNumbers(pageable.getPageNumber(), Page.empty().getTotalPages());
    }

    @DisplayName("[view][Get] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void 게시글상세호출() throws Exception {

        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        mvc.perform(get("/articles/" + articleId))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/detail"))
            .andExpect(model().attributeExists("article"))
            .andExpect(model().attributeExists("articleComments"));
        then(articleService).should().getArticle(articleId);
    }

    @DisplayName("[view][Get] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void 게시글검색페이지호출() throws Exception {
        mvc.perform(get("/articles/search"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/search"));
    }

    @DisplayName("[view][Get] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void 게시글해_시태그검색페이지호출() throws Exception {
        mvc.perform(get("/articles/search-hashtag"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("articles/search-hashtag"));

    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
            1L,
            createUserAccountDto(),
            Set.of(),
            "title",
            "content",
            "#java",
            LocalDateTime.now(),
            "uno",
            LocalDateTime.now(),
            "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(1L,
            "uno",
            "pw",
            "uno@mail.com",
            "Uno",
            "memo",
            LocalDateTime.now(),
            "uno",
            LocalDateTime.now(),
            "uno"
        );
    }

}