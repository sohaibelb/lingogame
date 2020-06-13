package com.bep.lingogame.feedbackOnCharacter.repository;

import com.bep.lingogame.feedbackOnCharacter.model.FeedbackOnCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FeedbackOnCharacterRepository implements IFeedbackOnCharacterRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int create(FeedbackOnCharacter feedbackOnCharacter) {
        return jdbcTemplate.update(
                "insert into feedbackoncharacter(feedbackoncharacter_id, feedbackonturn_id, character, feedback) values (?,?,?,?)",
                new Object[]{
                        feedbackOnCharacter.getFeedbackOnCharacterId(),
                        feedbackOnCharacter.getFeedbackOnTurnId(),
                        feedbackOnCharacter.getCharacter(),
                        feedbackOnCharacter.getFeedback()
                });
    }

    @Override
    public int findFeedbackOnCharacterId() {
        List<FeedbackOnCharacter> feedbackOnCharacterList = new ArrayList<FeedbackOnCharacter>();
        String query = "SELECT * FROM feedbackoncharacter ORDER BY feedbackoncharacter_id DESC LIMIT 1";
        try {
            feedbackOnCharacterList = jdbcTemplate.query(
                    query,
                    (rs, rowNum) ->

                            new FeedbackOnCharacter(
                                    rs.getInt("feedbackoncharacter_id"),
                                    rs.getInt("feedbackonturn_id"),
                                    rs.getString("character"),
                                    rs.getString("feedback")
                            )
            );
            for (FeedbackOnCharacter feedbackOnCharacter : feedbackOnCharacterList) {
                int feedbackoncharacter_id = feedbackOnCharacter.getFeedbackOnCharacterId();
                if (feedbackoncharacter_id == 0) {
                    return 1;
                } else {
                    return (feedbackoncharacter_id + 1);
                }
            }
        } catch (Exception e) {
            System.err.println("Something went wrong in FeedbackOnCharacterRepository: finding feedbackOnCharacter ID");
        }
        return 1;
    }
}

