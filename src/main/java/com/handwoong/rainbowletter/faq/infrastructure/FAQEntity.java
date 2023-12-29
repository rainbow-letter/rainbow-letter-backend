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
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@DynamicInsert
@Table(name = "faq")
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

    private Long sequence;

    @PostPersist
    private void initSequence() {
        sequence = id;
    }

    public static FAQEntity from(final FAQ faq) {
        final FAQEntity faqEntity = new FAQEntity();
        faqEntity.id = faq.id();
        faqEntity.summary = faq.summary();
        faqEntity.detail = faq.detail();
        faqEntity.visibility = faq.visibility();
        faqEntity.sequence = faq.sequence();
        return faqEntity;
    }

    public FAQ toModel() {
        return FAQ.builder()
                .id(id)
                .summary(summary)
                .detail(detail)
                .visibility(visibility)
                .sequence(sequence)
                .build();
    }
}
