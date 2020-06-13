package com.bep.lingogame.controller;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import com.bep.lingogame.feedbackOnTurn.service.FeedbackOnTurnService;
import com.bep.lingogame.gameRound.model.GameRound;
import com.bep.lingogame.gameRound.service.GameRoundService;
import com.bep.lingogame.word.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gameround")
public class GameRoundController {

    @Autowired
    private GameRoundService gameRoundService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WordService wordService;

    @Autowired
    private FeedbackOnTurnService feedbackOnTurnService;


    //Create a gameRound
    @PostMapping
    public ResponseEntity<String> createGameRound() {
        //Get Account of the user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account accountOfUser = accountService.getAccountByUsername(((UserDetails) principal).getUsername());

        //Validate jwt token
        if (((UserDetails) principal).isAccountNonExpired()) {

            //Getting the last gameround of game
            GameRound gameRound = gameRoundService.getLatestOfGameRoundOfGameUser(accountOfUser.getUserId());

            //Create a new gameround for the game
            GameRound newGameRound = new GameRound(gameRoundService.findGameRoundId(), gameRound.getGameId(), wordService.getNewGameRoundWord(gameRound.getWordId()).getWordId());
            int gameRoundCreated = gameRoundService.createGameRound(newGameRound);

            //Create feedback for every turn of the gameround
            int feedBackCreated = feedbackOnTurnService.createFeedbackOnTurn(newGameRound.getGameRoundId());

            //Checking if the game, gameround & feedback for every turn are created
            if (gameRoundCreated != 0 && feedBackCreated != 0) {
                return new ResponseEntity<>("A game round is created", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Could not create a game round", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Invalid JWT Token", HttpStatus.BAD_REQUEST);
        }
    }
}

