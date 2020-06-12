package com.bep.lingogame.controller;

import com.bep.lingogame.account.model.Account;
import com.bep.lingogame.account.service.AccountService;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.feedbackOnTurn.service.FeedbackOnTurnService;
import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.model.GameResponse;
import com.bep.lingogame.game.service.GameService;
import com.bep.lingogame.gameRound.model.GameRound;
import com.bep.lingogame.gameRound.model.GameRoundResponse;
import com.bep.lingogame.gameRound.service.GameRoundService;
import com.bep.lingogame.word.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    GameRoundService gameRoundService;

    @Autowired
    AccountService accountService;

    @Autowired
    WordService wordService;

    @Autowired
    FeedbackOnTurnService feedbackOnTurnService;


    //Get the game of the user
    @GetMapping
    public GameResponse getGameOfUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Get Account of the user
        String username = ((UserDetails) principal).getUsername();
        Account accountOfUser = accountService.getAccountByUsername(username);

        //Get the game
        Game game = gameService.getGameByUser_id(accountOfUser.getUser_id());

        //Creating GameRound Response
        GameRound gameRound = gameRoundService.getLatestOfGameRoundOfGame(game.getGame_id());

        GameRoundResponse gameRoundResponse = new GameRoundResponse(gameRound.getGameRound_id(), gameRound.getGame_id(), wordService.getWordByWordId(gameRound.getWord_id()),
                (ArrayList<FeedbackOnTurn>) feedbackOnTurnService.getFeedbackOnTurnsByGameRound(gameRound.getGameRound_id()));

        //Creating the complete game response:
        GameResponse gameResponse = new GameResponse(game.getGame_id(), game.getScore(), game.getUser_id(), gameRoundResponse);

        return gameResponse;
    }

    //Get game with the highscore of user
    @GetMapping("/highscore")
    public Object getHighScore() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Get Account of the user
        String username = ((UserDetails) principal).getUsername();
        Account accountOfUser = accountService.getAccountByUsername(username);

        //Get the game with the highscore of user
        Game game = gameService.getHighScoreOfUser(accountOfUser.getUser_id());

        if (game == null) {
            return new Error("You didn't play a game yet");
        }

        return game;
    }

    //Save score to game of user
    @PostMapping("/{pressn}")
    public ResponseEntity<String> savingScoreOfGameRound(@PathVariable int pressn, @RequestBody FeedbackOnTurn feedbackOnTurn) {
        //Getting the gameId
        int gameId = gameRoundService.getGameIdOfGameRound(feedbackOnTurn.getGameRound_id());
        //Updating the gamescore
        int scoreSaved = gameService.saveScore(pressn, gameId);

        //Checking if the score is saved
        if (scoreSaved != 0) {
            return new ResponseEntity<>("The score is saved", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Could not save the score", HttpStatus.BAD_REQUEST);
        }
    }

    //Create a game
    @PostMapping
    public ResponseEntity<String> createGame() {
        //Get Account of the user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account accountOfUser = accountService.getAccountByUsername(((UserDetails) principal).getUsername());

        //Validate Account
        if (((UserDetails) principal).isAccountNonExpired()) {
            //Create a game
            int gameCreated = gameService.createGame(accountOfUser);
            //Get the game
            Game game = gameService.getGameByUser_id(accountOfUser.getUser_id());
            //Create a gameround for the game
            GameRound gameRound = new GameRound(gameRoundService.findGameRoundId(), game.getGame_id(), wordService.getWordByWordLength(5).getWord_id());
            int gameRoundCreated = gameRoundService.createGameRound(gameRound);

            //Create feedback for every turn of the gameround
            int feedBackCreated = feedbackOnTurnService.createFeedbackOnTurn(gameRound.getGameRound_id());

            //Checking if the game, gameround & feedback for every turn are created
            if (gameCreated != 0 && gameRoundCreated != 0 && feedBackCreated != 0) {
                return new ResponseEntity<>("A game is created", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Could not create a game", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Invalid JWT Token", HttpStatus.BAD_REQUEST);
        }
    }
}
