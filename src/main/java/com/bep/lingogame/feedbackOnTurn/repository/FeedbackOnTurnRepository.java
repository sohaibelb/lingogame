package com.bep.lingogame.feedbackOnTurn.repository;

import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurn;
import com.bep.lingogame.feedbackOnTurn.model.FeedbackOnTurnRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackOnTurnRepository implements IFeedbackOnTurnRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int create(FeedbackOnTurnRequest feedbackOnTurnRequest) {
        return jdbcTemplate.update(
                "insert into feedbackOnTurn(gameRound_id) values (?)",
                new Object[]{
                        feedbackOnTurnRequest.getGameRound_id()
                });
    }

    @Override
    public List<FeedbackOnTurn> getByGameRound(int gameRound_id) {
        List<FeedbackOnTurn> feedbackOnTurns = new ArrayList<FeedbackOnTurn>();
        String query = "SELECT * FROM feedbackonturn WHERE gameround_id = ?";
        try {
            return feedbackOnTurns = jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, gameRound_id);
                        }
                    },
                    (rs, rowNum) ->
                            new FeedbackOnTurn(
                                    rs.getInt("feedbackonturn_id"),
                                    rs.getInt("gameround_id")

                            )
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in FeedbackOnTurnRepository: getting feedbackOnTurn by gameround ID");
        }
        return feedbackOnTurns;
    }
}
