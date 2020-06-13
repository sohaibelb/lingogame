package com.bep.lingogame.gameRound.repository;

import com.bep.lingogame.gameRound.model.GameRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameRoundRepository implements IGameRoundRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int create(GameRound gameRound) {

        return jdbcTemplate.update(
                "insert into gameround(gameround_id, game_id, word_id) values (?,?,?)",
                new Object[]{
                        gameRound.getGameRoundId(),
                        gameRound.getGameId(),
                        gameRound.getWordId()
                });
    }

    @Override
    public GameRound findById(int gameRound_id) {
        String query = "SELECT * FROM gameround WHERE gameround_id = ?";
        try {
            return jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, gameRound_id);
                        }
                    }, new ResultSetExtractor<GameRound>() {
                        public GameRound extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                return new GameRound(
                                        resultSet.getInt("gameround_id"),
                                        resultSet.getInt("game_id"),
                                        resultSet.getInt("word_id")
                                );
                            }
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in GameRoundRepository: finding a gameround by a gameround ID");
        }
        return null;
    }

    @Override
    public int findGameRoundId() {
        List<GameRound> gameRoundList = new ArrayList<GameRound>();
        String query = "SELECT * FROM gameround ORDER BY gameround_id DESC LIMIT 1";
        try {
            gameRoundList = jdbcTemplate.query(
                    query,
                    (rs, rowNum) ->

                            new GameRound(
                                    rs.getInt("gameround_id"),
                                    rs.getInt("game_id"),
                                    rs.getInt("word_id")
                            )
            );
            for (GameRound gameRound : gameRoundList) {
                int gameRound_id = gameRound.getGameRoundId();
                if (gameRound_id == 0) {
                    return 1;
                } else {
                    return (gameRound_id + 1);
                }
            }

        } catch (Exception e) {
            System.err.println("Something went wrong in GameRoundRepository: finding gameRound ID");
        }
        return 1;
    }

    @Override
    public GameRound getLatestOfGame(int game_id) {
        String query = "SELECT * FROM GAMEROUND WHERE game_id = ?  ORDER BY gameround_id DESC LIMIT 1";
        GameRound gameRound = new GameRound();
        try {
            return jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, game_id);
                        }
                    }, new ResultSetExtractor<GameRound>() {
                        public GameRound extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                return new GameRound(
                                        resultSet.getInt("gameround_id"),
                                        resultSet.getInt("game_id"),
                                        resultSet.getInt("word_id")
                                );
                            }
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in GameRoundRepository: finding the last created round of a game");
        }
        return null;
    }
}
