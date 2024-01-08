package com.lazarev.players;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayersController {

    private final PlayersService service;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() throws ResponseStatusException {
        return ResponseEntity.ok(service.findAllPlayers());
    }

    @GetMapping("/{playerID}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("playerID") String playerId) throws ResponseStatusException {
        return ResponseEntity.ok(service.findPlayer(playerId));
    }
}