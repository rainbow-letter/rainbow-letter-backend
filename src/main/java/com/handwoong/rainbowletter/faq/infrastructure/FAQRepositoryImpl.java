package com.handwoong.rainbowletter.faq.infrastructure;

import com.handwoong.rainbowletter.faq.domain.FAQ;
import com.handwoong.rainbowletter.faq.service.port.FAQRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FAQRepositoryImpl implements FAQRepository {
    private final JPAQueryFactory queryFactory;
    private final FAQJpaRepository faqJpaRepository;

    @Override
    public FAQ save(final FAQ faq) {
        return faqJpaRepository.save(FAQEntity.from(faq)).toModel();
    }

    @Override
    public List<FAQ> saveAll(final FAQ... faqs) {
        final List<FAQEntity> faqEntities = Arrays.stream(faqs)
                .map(FAQEntity::from)
                .toList();
        return faqJpaRepository.saveAll(faqEntities)
                .stream()
                .map(FAQEntity::toModel)
                .toList();
    }

    @Override
    public Optional<FAQ> findById(final Long id) {
        return faqJpaRepository.findById(id).map(FAQEntity::toModel);
    }

    @Override
    public List<FAQ> findAllByUser() {
        final QFAQEntity f = QFAQEntity.fAQEntity;
        return queryFactory.select(Projections.fields(FAQEntity.class, f.id, f.summary, f.detail))
                .from(f)
                .where(f.visibility.isTrue())
                .orderBy(f.sequence.asc())
                .fetch()
                .stream()
                .map(FAQEntity::toModel)
                .toList();
    }

    @Override
    public List<FAQ> findAllByAdmin() {
        final QFAQEntity f = QFAQEntity.fAQEntity;
        return queryFactory.select(Projections.fields(FAQEntity.class, f.id, f.summary, f.detail, f.visibility))
                .from(f)
                .orderBy(f.sequence.asc())
                .fetch()
                .stream()
                .map(FAQEntity::toModel)
                .toList();
    }

    @Override
    public void delete(final FAQ faq) {
        faqJpaRepository.delete(FAQEntity.from(faq));
    }
}
