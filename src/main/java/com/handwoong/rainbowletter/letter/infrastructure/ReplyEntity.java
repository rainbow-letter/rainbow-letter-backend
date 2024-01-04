package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.gpt.infrastructure.ChatGptTokenEntity;
import com.handwoong.rainbowletter.letter.domain.Content;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.ReplyReadStatus;
import com.handwoong.rainbowletter.letter.domain.ReplyType;
import com.handwoong.rainbowletter.letter.domain.Summary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.Hibernate;

@Getter
@Entity
@Table(name = "reply")
public class ReplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 20)
    private String summary;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReplyType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReplyReadStatus readStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_gpt_token_id", referencedColumnName = "id")
    private ChatGptTokenEntity chatGptTokenEntity;

    public static ReplyEntity from(final Reply reply) {
        final ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.id = reply.id();
        replyEntity.summary = reply.summary().toString();
        replyEntity.content = reply.content().toString();
        replyEntity.type = reply.type();
        replyEntity.readStatus = reply.readStatus();
        return replyEntity;
    }

    public Reply toModel() {
        return Reply.builder()
                .id(id)
                .summary(new Summary(summary))
                .content(new Content(content))
                .type(type)
                .readStatus(readStatus)
                .build();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (Objects.isNull(obj) || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }
        ReplyEntity entity = (ReplyEntity) obj;
        return Objects.nonNull(id) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? id.intValue() : 0;
    }
}
