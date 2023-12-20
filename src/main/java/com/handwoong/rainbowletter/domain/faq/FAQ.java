package com.handwoong.rainbowletter.domain.faq;

import com.handwoong.rainbowletter.domain.BaseEntity;
import com.handwoong.rainbowletter.dto.faq.FAQCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class FAQ extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 30)
    private String summary;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String detail;

    @NotNull
    private boolean visibility;

    private Long sortIndex;

    private FAQ(final String summary, final String detail, final boolean visibility) {
        this.summary = summary;
        this.detail = detail;
        this.visibility = visibility;
    }

    public static FAQ create(final FAQCreateRequest request) {
        return new FAQ(request.summary(), request.detail(), true);
    }

    @PostPersist
    private void initSortIndex() {
        sortIndex = id;
    }
}
