package com.handwoong.rainbowletter.gpt.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.gpt.domain.ChatGpt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.Getter;
import org.hibernate.Hibernate;

@Getter
@Entity
@Table(name = "chat_gpt")
public class ChatGptEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private int promptTokens;

    @NotNull
    private int completionTokens;

    @NotNull
    private int totalTokens;

    public static ChatGptEntity from(final ChatGpt chatGpt) {
        final ChatGptEntity chatGptEntity = new ChatGptEntity();
        chatGptEntity.id = chatGpt.id();
        chatGptEntity.content = chatGpt.content();
        chatGptEntity.promptTokens = chatGpt.promptTokens();
        chatGptEntity.completionTokens = chatGpt.completionTokens();
        chatGptEntity.totalTokens = chatGpt.totalTokens();
        return chatGptEntity;
    }

    public ChatGpt toModel() {
        return ChatGpt.builder()
                .id(id)
                .content(content)
                .promptTokens(promptTokens)
                .completionTokens(completionTokens)
                .totalTokens(totalTokens)
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
        ChatGptEntity entity = (ChatGptEntity) obj;
        return Objects.nonNull(id) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? id.intValue() : 0;
    }
}
