package com.handwoong.rainbowletter.gpt.infrastructure;

import com.handwoong.rainbowletter.common.infrastructure.BaseEntity;
import com.handwoong.rainbowletter.gpt.domain.ChatGptToken;
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
@Table(name = "chat_gpt_token")
public class ChatGptTokenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int promptTokens;

    @NotNull
    private int completionTokens;

    @NotNull
    private int totalTokens;

    public static ChatGptTokenEntity from(final ChatGptToken chatGptToken) {
        final ChatGptTokenEntity chatGptTokenEntity = new ChatGptTokenEntity();
        chatGptTokenEntity.id = chatGptToken.id();
        chatGptTokenEntity.promptTokens = chatGptToken.promptTokens();
        chatGptTokenEntity.completionTokens = chatGptToken.completionTokens();
        chatGptTokenEntity.totalTokens = chatGptToken.totalTokens();
        return chatGptTokenEntity;
    }

    public ChatGptToken toModel() {
        return ChatGptToken.builder()
                .id(id)
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
        ChatGptTokenEntity entity = (ChatGptTokenEntity) obj;
        return Objects.nonNull(id) && Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.nonNull(id) ? id.intValue() : 0;
    }
}
