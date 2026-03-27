package com.mcnz.copilot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HangmanServiceTests {

    private HangmanService hangmanService;
    private PlayerSessionState player;

    @BeforeEach
    void setUp() {
        hangmanService = new HangmanService(() -> "JAVA");
        player = new PlayerSessionState("test-token");
        hangmanService.startNewGame(player);
    }

    @Test
    void startsANewGameWithMaskedWordAndAttempts() {
        HangmanGame game = player.getCurrentGame();

        assertThat(game.getTargetWord()).isEqualTo("JAVA");
        assertThat(game.getMaskedWord()).isEqualTo("_ _ _ _");
        assertThat(game.getRemainingAttempts()).isEqualTo(HangmanService.DEFAULT_MAX_WRONG_GUESSES);
        assertThat(game.getStatus()).isEqualTo(GameStatus.IN_PROGRESS);
    }

    @Test
    void correctGuessRevealsEveryMatchingLetter() {
        GuessFeedback feedback = hangmanService.submitGuess(player, "a");

        assertThat(feedback.outcome()).isEqualTo(GuessOutcome.CORRECT);
        assertThat(player.getCurrentGame().getMaskedWord()).isEqualTo("_ A _ A");
        assertThat(player.getStats().getWins()).isZero();
    }

    @Test
    void wrongGuessConsumesOneAttempt() {
        GuessFeedback feedback = hangmanService.submitGuess(player, "z");

        assertThat(feedback.outcome()).isEqualTo(GuessOutcome.WRONG);
        assertThat(player.getCurrentGame().getRemainingAttempts()).isEqualTo(5);
        assertThat(player.getCurrentGame().getWrongLetters()).containsExactly('Z');
    }

    @Test
    void repeatedGuessDoesNotConsumeAnotherAttempt() {
        hangmanService.submitGuess(player, "z");

        GuessFeedback feedback = hangmanService.submitGuess(player, "z");

        assertThat(feedback.outcome()).isEqualTo(GuessOutcome.ALREADY_GUESSED);
        assertThat(player.getCurrentGame().getRemainingAttempts()).isEqualTo(5);
        assertThat(player.getCurrentGame().getWrongLetters()).containsExactly('Z');
    }

    @Test
    void invalidGuessIsRejectedWithoutChangingGame() {
        GuessFeedback feedback = hangmanService.submitGuess(player, "12");

        assertThat(feedback.outcome()).isEqualTo(GuessOutcome.INVALID);
        assertThat(player.getCurrentGame().getRemainingAttempts()).isEqualTo(6);
        assertThat(player.getCurrentGame().getAllGuesses()).isEmpty();
    }

    @Test
    void winningRoundUpdatesPlayerStats() {
        hangmanService.submitGuess(player, "j");
        hangmanService.submitGuess(player, "a");

        GuessFeedback feedback = hangmanService.submitGuess(player, "v");

        assertThat(feedback.outcome()).isEqualTo(GuessOutcome.WON);
        assertThat(player.getCurrentGame().getStatus()).isEqualTo(GameStatus.WON);
        assertThat(player.getStats().getGamesPlayed()).isEqualTo(1);
        assertThat(player.getStats().getWins()).isEqualTo(1);
        assertThat(player.getStats().getLosses()).isZero();
    }

    @Test
    void losingRoundUpdatesPlayerStats() {
        hangmanService.submitGuess(player, "q");
        hangmanService.submitGuess(player, "w");
        hangmanService.submitGuess(player, "e");
        hangmanService.submitGuess(player, "r");
        hangmanService.submitGuess(player, "t");

        GuessFeedback feedback = hangmanService.submitGuess(player, "y");

        assertThat(feedback.outcome()).isEqualTo(GuessOutcome.LOST);
        assertThat(player.getCurrentGame().getStatus()).isEqualTo(GameStatus.LOST);
        assertThat(player.getStats().getGamesPlayed()).isEqualTo(1);
        assertThat(player.getStats().getWins()).isZero();
        assertThat(player.getStats().getLosses()).isEqualTo(1);
    }
}

