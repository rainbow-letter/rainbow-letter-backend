package com.handwoong.rainbowletter.faq.infrastructure;

import static com.handwoong.rainbowletter.faq.infrastructure.QFAQ.fAQ;

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
    public void save(final FAQ faq) {
        faqJpaRepository.save(FAQEntity.fromModel(faq));
    }

    @Override
    public void saveAll(final FAQ... faqs) {
        final List<FAQEntity> faqEntities = Arrays.stream(faqs)
                .map(FAQEntity::fromModel)
                .toList();
        faqJpaRepository.saveAll(faqEntities);
    }

    @Override
    public Optional<FAQ> findById(final Long id) {
        return faqJpaRepository.findById(id).map(FAQEntity::toModel);
    }

    @Override
    public List<FAQ> findAllByUser() {
        final QFAQ f = fAQ;
        return queryFactory.select(Projections.fields(FAQ.class, f.id, f.summary, f.detail))
                .from(f)
                .where(f.visibility.isTrue())
                .orderBy(f.sortIndex.asc())
                .fetch();
    }

    @Override
    public List<FAQ> findAllByAdmin() {
        final QFAQ f = fAQ;
        return queryFactory.select(Projections.fields(FAQ.class, f.id, f.summary, f.detail, f.visibility))
                .from(f)
                .orderBy(f.sortIndex.asc())
                .fetch();
    }

    @Override
    public void delete(final FAQ faq) {
        faqJpaRepository.delete(FAQEntity.fromModel(faq));
    }
}
