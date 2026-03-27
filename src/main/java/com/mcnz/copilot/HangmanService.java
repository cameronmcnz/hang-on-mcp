package com.mcnz.copilot;

import org.springframework.stereotype.Service;

@Service
public class HangmanService {

    static final int DEFAULT_MAX_WRONG_GUESSES = 6;

    private final WordSource wordSource;

    public HangmanService(WordSource wordSource) {
        this.wordSource = wordSource;
    }

    public HangmanGame startNewGame(PlayerSessionState player) {
        HangmanGame game = new HangmanGame(wordSource.randomWord(), DEFAULT_MAX_WRONG_GUESSES);
        player.setCurrentGame(game);
        return game;
    }

    public GuessFeedback submitGuess(PlayerSessionState player, String rawGuess) {
        HangmanGame game = player.getCurrentGame();
        if (game == null) {
            game = startNewGame(player);
        }

        String normalizedGuess = rawGuess == null ? "" : rawGuess.trim();
        if (normalizedGuess.length() != 1 || !Character.isLetter(normalizedGuess.charAt(0))) {
            return new GuessFeedback(
                    GuessOutcome.INVALID,
                    "That be no proper letter. Fire one A-Z cannon shot at a time.",
                    "warning"
            );
        }

        GuessOutcome outcome = game.applyGuess(normalizedGuess.charAt(0));
        return switch (outcome) {
            case ALREADY_GUESSED -> new GuessFeedback(
                    outcome,
                    "Ye already fired that rune. Try a fresh letter, matey.",
                    "warning"
            );
            case CORRECT -> new GuessFeedback(
                    outcome,
                    "Direct hit! The code gods reveal another glyph.",
                    "success"
            );
            case WRONG -> new GuessFeedback(
                    outcome,
                    "Missed the mark. The bug beast grows stronger.",
                    "danger"
            );
            case WON -> {
                player.getStats().recordWin();
                yield new GuessFeedback(
                        outcome,
                        "Victory! Ye saved the repo and humiliated the gallows.",
                        "success"
                );
            }
            case LOST -> {
                player.getStats().recordLoss();
                yield new GuessFeedback(
                        outcome,
                        "The beast wins this round. The word slips from the shadows no longer.",
                        "danger"
                );
            }
            case ROUND_OVER -> new GuessFeedback(
                    outcome,
                    "Round be over, captain. Slam the New Game button for another duel.",
                    "info"
            );
            case INVALID -> new GuessFeedback(
                    outcome,
                    "That be no proper letter. Fire one A-Z cannon shot at a time.",
                    "warning"
            );
        };
    }
}

