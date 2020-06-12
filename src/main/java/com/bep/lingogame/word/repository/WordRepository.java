package com.bep.lingogame.word.repository;

import com.bep.lingogame.word.model.Word;
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
public class WordRepository implements IWordRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Word findWordByLength(int lengthWord) {
        String query = "SELECT * FROM WORD WHERE length(name) = ? offset random() * ((select count(*) FROM WORD where length(name) = ?) - 1) limit 1";
        try {
            return jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, lengthWord);
                            preparedStatement.setInt(2, lengthWord);
                        }
                    }, new ResultSetExtractor<Word>() {
                        public Word extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                return new Word(
                                        resultSet.getInt("word_id"),
                                        resultSet.getString("name")
                                );
                            }
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in WordRepository: finding a word by word length");
        }
        return null;
    }

    @Override
    public Word findWordById(int word_id) {
        String query = "SELECT * from word WHERE word_id = ?";
        try {
            return jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setInt(1, word_id);
                        }
                    }, new ResultSetExtractor<Word>() {
                        public Word extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                return new Word(
                                        resultSet.getInt("word_id"),
                                        resultSet.getString("name")
                                );
                            }
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in WordRepository: finding word by word ID");
        }
        return null;
    }
}
