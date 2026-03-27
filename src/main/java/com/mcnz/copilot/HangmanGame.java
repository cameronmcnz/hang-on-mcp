package com.mcnz.copilot;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class HangmanGame {

    private final String targetWord;
    private final int maxWrongGuesses;
    private final Set<Character> correctLetters = new TreeSet<>();
    private final Set<Character> wrongLetters = new TreeSet<>();
    private GameStatus status = GameStatus.IN_PROGRESS;

    public HangmanGame(String targetWord, int maxWrongGuesses) {
        this.targetWord = targetWord.toUpperCase();
        this.maxWrongGuesses = maxWrongGuesses;
    }

    public GuessOutcome applyGuess(char guess) {
        char normalizedGuess = Character.toUpperCase(guess);

        if (status != GameStatus.IN_PROGRESS) {
            return GuessOutcome.ROUND_OVER;
        }

        if (hasBeenGuessed(normalizedGuess)) {
            return GuessOutcome.ALREADY_GUESSED;
        }

        if (targetWord.indexOf(normalizedGuess) >= 0) {
            correctLetters.add(normalizedGuess);
            if (isSolved()) {
                status = GameStatus.WON;
                return GuessOutcome.WON;
            }
            return GuessOutcome.CORRECT;
        }

        wrongLetters.add(normalizedGuess);
        if (wrongLetters.size() >= maxWrongGuesses) {
            status = GameStatus.LOST;
            return GuessOutcome.LOST;
        }
        return GuessOutcome.WRONG;
    }

    public boolean hasBeenGuessed(char guess) {
        char normalizedGuess = Character.toUpperCase(guess);
        return correctLetters.contains(normalizedGuess) || wrongLetters.contains(normalizedGuess);
    }

    public String getTargetWord() {
        return targetWord;
    }

    public int getMaxWrongGuesses() {
        return maxWrongGuesses;
    }

    public GameStatus getStatus() {
        return status;
    }

    public String getMaskedWord() {
        return targetWord.chars()
                .mapToObj(letter -> correctLetters.contains((char) letter) ? Character.toString(letter) : "_")
                .collect(Collectors.joining(" "));
    }

    public int getRemainingAttempts() {
        return maxWrongGuesses - wrongLetters.size();
    }

    public int getWrongGuessCount() {
        return wrongLetters.size();
    }

    public List<Character> getWrongLetters() {
        return wrongLetters.stream().toList();
    }

    public List<Character> getCorrectLetters() {
        return correctLetters.stream().toList();
    }

    public List<Character> getAllGuesses() {
        TreeSet<Character> allGuesses = new TreeSet<>(correctLetters);
        allGuesses.addAll(wrongLetters);
        return allGuesses.stream().toList();
    }

    public boolean isInProgress() {
        return status == GameStatus.IN_PROGRESS;
    }

    private boolean isSolved() {
        return targetWord.chars().allMatch(letter -> correctLetters.contains((char) letter));
    }
}

