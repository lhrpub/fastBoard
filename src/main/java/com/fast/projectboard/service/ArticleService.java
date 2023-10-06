package com.fast.projectboard.service;

import com.fast.projectboard.domain.type.SearchType;
import com.fast.projectboard.dto.ArticleDto;
import com.fast.projectboard.dto.ArticleUpdateDto;
import com.fast.projectboard.dto.ArticleWithCommentsDto;
import com.fast.projectboard.repository.ArticleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto of) {

    }

    public void updateArticle(ArticleDto dto) {
    }

    public void deleteArticle(long l) {

    }
}
