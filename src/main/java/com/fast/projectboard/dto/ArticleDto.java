package com.fast.projectboard.dto;

import com.fast.projectboard.domain.Article;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

public record ArticleDto(
    Long id,
    UserAccountDto userAccountDto,
    String title,
    String content,
    String hashtag,
    LocalDateTime createdAt,
    String createdBy,
    LocalDateTime modifiedAt,
    String modifiedBy
) {
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
            entity.getId(),
            UserAccountDto.from(entity.getUserAccount()),
            entity.getTitle(),
            entity.getContent(),
            entity.getHashtag(),
            entity.getCreatedAt(),
            entity.getCreatedBy(),
            entity.getModifiedAt(),
            entity.getModifiedBy()
        );
    }

    public Article toEntity() {
        return Article.of(
            userAccountDto.toEntity(),
            title,
            content,
            hashtag
        );
    }
}
