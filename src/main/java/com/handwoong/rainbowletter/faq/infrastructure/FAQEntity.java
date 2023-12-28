package com.handwoong.rainbowletter.faq.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.faq.domain.FAQ;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@DynamicInsert
@Table(name = "faq")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FAQEntity extends BaseEntity {
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

    @PostPersist
    private void initSortIndex() {
        sortIndex = id;
    }

    public FAQ toModel() {
        return FAQ.builder()
                .id(id)
                .summary(summary)
                .detail(detail)
                .visibility(visibility)
                .sortIndex(sortIndex)
                .build();
    }

    public static FAQEntity fromModel(final FAQ faq) {
        final FAQEntity faqEntity = new FAQEntity();
        faqEntity.id = faq.id();
        faqEntity.summary = faq.summary();
        faqEntity.detail = faq.detail();
        faqEntity.visibility = faq.visibility();
        faqEntity.sortIndex = faq.sortIndex();
        return faqEntity;
    }
}
