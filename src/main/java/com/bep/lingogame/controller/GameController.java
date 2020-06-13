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
    private GameService gameService;

    @Autowired
    private GameRoundService gameRoundService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private WordService wordService;

    @Autowired
    private FeedbackOnTurnService feedbackOnTurnService;


    //Get the game of the user
    @GetMapping
    public GameResponse getGameOfUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Get Account of the user
        String username = ((UserDetails) principal).getUsername();
        Account accountOfUser = accountService.getAccountByUsername(username);

        //Get the game
        Game game = gameService.getGameByUser_id(accountOfUser.getUserId());

        //Creating GameRound Response
        GameRound gameRound = gameRoundService.getLatestOfGameRoundOfGameUser(accountOfUser.getUserId());
        GameRoundResponse gameRoundResponse = new GameRoundResponse(gameRound.getGameRoundId(), gameRound.getGameId(), wordService.getWordByWordId(gameRound.getWordId()),
                (ArrayList<FeedbackOnTurn>) feedbackOnTurnService.getFeedbackOnTurnsByGameRound(gameRound.getGameRoundId()));

        //Creating the complete game response:
        GameResponse gameResponse = new GameResponse(game.getGameId(), game.getScore(), game.getUserId(), gameRoundResponse);

        return gameResponse;
    }

    //Get game with the highscore of user
    @GetMapping("/highscore")
    public Object getHighScore() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //Get username
        String username = ((UserDetails) principal).getUsername();

        //Get the game with the highscore of user
        Game game = gameService.getHighScoreOfUser(username);

        if (game == null) {
            return new Error("You didn't play a game yet");
        }

        return game;
    }

    //Save score to game of user
    @PostMapping("/{pressn}")
    public ResponseEntity<String> savingScoreOfGameRound(@PathVariable int pressn, @RequestBody FeedbackOnTurn feedbackOnTurn) {
        //Getting the gameId
        int gameId = gameRoundService.getGameIdOfGameRound(feedbackOnTurn.getGameRoundId());
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

            //Get the game that is created
            Game game = gameService.getGameByUser_id(accountOfUser.getUserId());

            //Create a gameround for the game
            GameRound gameRound = new GameRound(gameRoundService.findGameRoundId(), game.getGameId(), wordService.getWordByWordLength(5).getWordId());
            int gameRoundCreated = gameRoundService.createGameRound(gameRound);

            //Create feedback for every turn of the gameround
            int feedBackCreated = feedbackOnTurnService.createFeedbackOnTurn(gameRound.getGameRoundId());

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
