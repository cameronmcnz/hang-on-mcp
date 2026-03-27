package com.mcnz.copilot;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordBankService implements WordSource {

    private static final List<String> WORDS = List.of(
            "JAVA",
            "SPRING",
            "MAVEN",
            "BEAN",
            "CODER",
            "GITHUB",
            "COPILOT",
            "PROMPT",
            "COMMIT",
            "BRANCH",
            "MERGE",
            "REFACTOR",
            "DEBUG",
            "TEMPLATE",
            "CONTROLLER"
    );

    @Override
    public String randomWord() {
        return WORDS.get(ThreadLocalRandom.current().nextInt(WORDS.size()));
    }
}

