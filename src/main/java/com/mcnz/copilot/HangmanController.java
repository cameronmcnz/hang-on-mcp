package com.mcnz.copilot;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HangmanController {

    private static final String PLAYER_TOKEN_COOKIE = "HANGONMAN_TOKEN";
    private static final int COOKIE_MAX_AGE_SECONDS = 60 * 60 * 24 * 30;
    private static final List<String> HANGMAN_STAGES = List.of(
            """
              +---+
              |   |
                  |
                  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
                  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
              |   |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|   |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
                  |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
             /    |
                  |
            =========
            """,
            """
              +---+
              |   |
              O   |
             /|\\  |
             / \\  |
                  |
            =========
            """
    );

    private final HangmanService hangmanService;
    private final PlayerRegistryService playerRegistryService;

    public HangmanController(HangmanService hangmanService, PlayerRegistryService playerRegistryService) {
        this.hangmanService = hangmanService;
        this.playerRegistryService = playerRegistryService;
    }

    @GetMapping("/")
    public String index(
            @CookieValue(name = PLAYER_TOKEN_COOKIE, required = false) String playerToken,
            HttpServletResponse response,
            Model model
    ) {
        PlayerSessionState player = resolvePlayer(playerToken, response);
        if (player.getCurrentGame() == null) {
            hangmanService.startNewGame(player);
        }

        if (!model.containsAttribute("feedback")) {
            model.addAttribute("feedback", "Choose a letter and prove ye belong on the bridge.");
        }
        if (!model.containsAttribute("feedbackType")) {
            model.addAttribute("feedbackType", "info");
        }

        populateModel(model, player);
        return "index";
    }

    @PostMapping("/guess")
    public String guess(
            @CookieValue(name = PLAYER_TOKEN_COOKIE, required = false) String playerToken,
            @RequestParam("guess") String guess,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        PlayerSessionState player = resolvePlayer(playerToken, response);
        GuessFeedback feedback = hangmanService.submitGuess(player, guess);
        redirectAttributes.addFlashAttribute("feedback", feedback.message());
        redirectAttributes.addFlashAttribute("feedbackType", feedback.tone());
        return "redirect:/";
    }

    @PostMapping("/new-game")
    public String newGame(
            @CookieValue(name = PLAYER_TOKEN_COOKIE, required = false) String playerToken,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        PlayerSessionState player = resolvePlayer(playerToken, response);
        hangmanService.startNewGame(player);
        redirectAttributes.addFlashAttribute("feedback", "Fresh round, fresh swagger. Another word enters the arena.");
        redirectAttributes.addFlashAttribute("feedbackType", "info");
        return "redirect:/";
    }

    private PlayerSessionState resolvePlayer(String playerToken, HttpServletResponse response) {
        if (playerToken == null || playerToken.isBlank()) {
            PlayerSessionState player = playerRegistryService.createPlayer();
            response.addCookie(buildCookie(player.getToken()));
            return player;
        }
        return playerRegistryService.getOrCreate(playerToken);
    }

    private Cookie buildCookie(String token) {
        Cookie cookie = new Cookie(PLAYER_TOKEN_COOKIE, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_MAX_AGE_SECONDS);
        return cookie;
    }

    private void populateModel(Model model, PlayerSessionState player) {
        HangmanGame game = player.getCurrentGame();
        model.addAttribute("game", game);
        model.addAttribute("stats", player.getStats());
        model.addAttribute("hangmanStage", HANGMAN_STAGES.get(Math.min(game.getWrongGuessCount(), HANGMAN_STAGES.size() - 1)));
        model.addAttribute("wordReveal", game.getStatus() == GameStatus.LOST ? game.getTargetWord() : "");
    }
}

