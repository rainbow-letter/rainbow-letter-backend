package com.handwoong.rainbowletter.letter.infrastructure;

import com.handwoong.rainbowletter.letter.domain.ChatGpt;
import com.handwoong.rainbowletter.letter.domain.Letter;
import com.handwoong.rainbowletter.letter.domain.Reply;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptPrompt;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptRequest;
import com.handwoong.rainbowletter.letter.domain.dto.chatgpt.ChatGptResponse;
import com.handwoong.rainbowletter.letter.infrastructure.chatgpt.ChatGptExecutor;
import com.handwoong.rainbowletter.letter.service.port.ReplyGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyGeneratorImpl implements ReplyGenerator {
    private static final String PROMPT_HEADER = "%s가 세상을 떠나 무지개마을에 살고있는 반려동물에게 쓴 편지에 대한 답장을 반려동물 %s 입장에서 작성해줘\n\n";
    private static final String PROMPT_CONTENT = """
            편지=
            "
            %s
            "
                        
            편지 정보
            Recipient Name: %s
            Sender Name: %s
            Type of animal: %s
            Language: **Korean**
                        
            말투: 5살 아이의 말투, **반말**
            분위기: 밝고 기쁨
            최소길이: 300자
            최대길이: 500자
                        
            내용
            - %s(Recipient Name)이 쓴 편지 내용을 잘 숙지하고, 자연스러운 답장을 생성해줘.
            - 무지개마을에 대해 물어보는 경우에만 자연스럽게 답변해줘.
            - 이모티콘을 자연스럽게 넣어줘도 좋아. 꼭 넣을 필요는 없어.
            - 맞춤법을 잘 모르는 아이가 쓰는 것 처럼 작성해줘.
            %s
                        
            """;
    private static final String FIRST_LETTER_PROMPT = """
            - 편지를 처음 받아봐서 기쁘다는 내용을 처음에 넣어줘
            - 무지개마을에는 %s을 볼 수 있는 TV가 있어서 %s을 가끔 볼 수 있어서 행복하다는 내용을 넣어줘.
            """;
    private static final String PROMPT_CAUTION = """
            주의사항
            - 1인칭 대명사 "나"를 "%s" 으로 대체해줘.
            **- 2인칭 대명사 "당신", "너", "you"를 Recipient Name "%s" 으로 대체해줘.**
            - 오직 답장 내용만 전달해줘.
            - 편지 내용 앞뒤에 (")로 감싸지 말아줘.
            - 반말로 작성했는지 다시 한 번 확인해줘.
            - "있단다" 라는 어투는 사용하지 말아줘.
            """;

    private final ChatGptExecutor chatGptExecutor;

    @Override
    public Reply generate(final Letter letter, boolean isNotFirst) {
        final ChatGptPrompt userPrompt = createUserPrompt(letter, isNotFirst);
        final ChatGptRequest chatGptRequest = createGptRequest(userPrompt);

        final ChatGptResponse chatGptResponse = chatGptExecutor.execute(chatGptRequest);
        final String output = chatGptResponse.choices().get(0).message().content();
        final int promptTokens = chatGptResponse.usage().prompt_tokens();
        final int completionTokens = chatGptResponse.usage().completion_tokens();
        final int totalTokens = chatGptResponse.usage().total_tokens();

        final ChatGpt chatGpt = ChatGpt.create(output, promptTokens, completionTokens, totalTokens);
        return Reply.create(chatGpt);
    }

    private ChatGptRequest createGptRequest(final ChatGptPrompt prompt) {
        final String model = "gpt-4-1106-preview";
        final List<ChatGptPrompt> messages = List.of(
                ChatGptPrompt.create("system", "You are a bot who replies to a letter in the position of a pet."),
                prompt
        );
        final long maxTokens = 1500L;
        final double temperature = 0.8;
        final double topP = 0.7;
        final double frequencyPenalty = 0.2;
        final double presencePenalty = 0.1;

        return ChatGptRequest.create(model, messages, maxTokens, temperature, topP, frequencyPenalty, presencePenalty);
    }

    private ChatGptPrompt createUserPrompt(final Letter letter, final boolean isNotFirst) {
        final String letterContent = letter.content().toString();
        final String petName = letter.pet().name();
        final String petOwnerName = letter.pet().owner();
        final String petSpecies = letter.pet().species();
        final String firstLetterPromptText =
                isNotFirst ? "" : String.format(FIRST_LETTER_PROMPT, petOwnerName, petOwnerName);

        final String header = String.format(PROMPT_HEADER, petOwnerName, petName);
        final String content = String.format(
                PROMPT_CONTENT, letterContent, petOwnerName, petName, petSpecies, petOwnerName, firstLetterPromptText);
        final String caution = String.format(PROMPT_CAUTION, petName, petOwnerName);

        return ChatGptPrompt.create("user", header + content + caution);
    }
}
