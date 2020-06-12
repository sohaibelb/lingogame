package com.bep.lingogame.controller;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurnRequest;
import com.bep.lingogame.feedbackOnTurn.service.FeedbackOnTurnService;
import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.service.GameService;
import com.bep.lingogame.gameRound.model.GameRound;
import com.bep.lingogame.gameRound.service.GameRoundService;
import com.bep.lingogame.word.model.Word;
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
    GameRoundService gameRoundService;

    @Autowired
    AccountService accountService;

    @Autowired
    GameService gameService;

    @Autowired
    WordService wordService;

    @Autowired
    FeedbackOnTurnService feedbackOnTurnService;


    //Create a gameRound
    @PostMapping
    public ResponseEntity<String> createGameRound() {
        //Get Account of the user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account accountOfUser = accountService.getAccountByUsername(((UserDetails) principal).getUsername());

        //Validate jwt token
        if (((UserDetails) principal).isAccountNonExpired()) {
            //Get the game of the user
            Game game = gameService.getGameByUser_id(accountOfUser.getUser_id());

            //Getting the last gameround of game
            GameRound gameRound = gameRoundService.getLatestOfGameRoundOfGame(game.getGame_id());

            //Get the word of that last gameround
            Word word = wordService.getWordByWordId(gameRound.getWord_id());
            int wordLength = word.getName().length() + 1;

            //If last gameround wordlength was 7 then reset to 5
            if (wordLength == 8) {
                wordLength = 5;
            }

            //Create a gameround for the game
            GameRound newGameRound = new GameRound(gameRoundService.findGameRoundId(), game.getGame_id(), wordService.getWordByWordLength(wordLength).getWord_id());
            int gameRoundCreated = gameRoundService.createGameRound(newGameRound);

            //Create feedback for every turn of the gameround
            int feedBackCreated = feedbackOnTurnService.createFeedbackOnTurn(newGameRound.getGameRound_id());

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

