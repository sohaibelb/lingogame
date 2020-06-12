package com.bep.lingogame.game.repository;

import com.bep.lingogame.game.model.Game;
import com.bep.lingogame.game.model.GameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class GameRepository implements IGameRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int create(GameRequest gameRequest) {

        return jdbcTemplate.update(
                "insert into game(score, user_id) values (?,?)",
                new Object[]{
                        gameRequest.getScore(),
                        gameRequest.getUser_id()
                });
    }

    @Override
    public Game findByUser_id(int user_id) {
        String query = "SELECT * FROM GAME WHERE user_id = ? ORDER BY game_id DESC LIMIT 1";
        try {
            return jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, user_id);
                        }
                    }, new ResultSetExtractor<Game>() {
                        public Game extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                return new Game(
                                        resultSet.getInt("game_id"),
                                        resultSet.getInt("score"),
                                        resultSet.getInt("user_id")
                                );
                            }
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in GameRepository: finding the game of the user");
        }
        return null;
    }

    @Override
    public Game getHighScoreOfUser(int user_id) {
        String query = "SELECT * FROM game where user_id=? ORDER BY score DESC LIMIT 1";
        try {
            return jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, user_id);
                        }
                    }, new ResultSetExtractor<Game>() {
                        public Game extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                return new Game(
                                        resultSet.getInt("game_id"),
                                        resultSet.getInt("score"),
                                        resultSet.getInt("user_id")
                                );
                            }
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in GameRepository: getting the highscore of the user");
        }
        return null;
    }

    @Override
    public int saveScore(int pressNumber, int gameId) {
        String query = "update game set score=score+? where game_id = ?";
        int score = 50 - ((pressNumber - 1) * 10);
        try {
            return jdbcTemplate.update(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, score);
                            preparedStatement.setInt(2, gameId);
                        }
                    });
        } catch (
                Exception e) {
            System.err.println("Something went wrong in GameRepository: saving the gamescore");
        }
        return 0;
    }
}
