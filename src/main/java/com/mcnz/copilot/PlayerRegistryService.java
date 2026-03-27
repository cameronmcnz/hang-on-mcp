package com.mcnz.copilot;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerRegistryService {

    private final Map<String, PlayerSessionState> players = new ConcurrentHashMap<>();

    public PlayerSessionState createPlayer() {
        String token = UUID.randomUUID().toString();
        PlayerSessionState player = new PlayerSessionState(token);
        players.put(token, player);
        return player;
    }

    public PlayerSessionState getOrCreate(String token) {
        return players.computeIfAbsent(token, PlayerSessionState::new);
    }
}

