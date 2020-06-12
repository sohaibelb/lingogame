package com.bep.lingogame.account.repository;

import com.bep.lingogame.account.model.Account;
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
public class AccountRepository implements IAccountRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int create(Account account) {
        return jdbcTemplate.update(
                "insert into account(username, password) values (?,?)",
                new Object[]{
                        account.getUsername(),
                        account.getPassword()
                });
    }

    @Override
    public Account findByUsername(String username) {
        String query = "SELECT * FROM ACCOUNT WHERE username = ?";
        try {
            return jdbcTemplate.query(
                    query,
                    new PreparedStatementSetter() {
                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, username);
                        }
                    }, new ResultSetExtractor<Account>() {
                        public Account extractData(ResultSet resultSet) throws SQLException,
                                DataAccessException {
                            if (resultSet.next()) {
                                return new Account(
                                        resultSet.getInt("user_id"),
                                        resultSet.getString("username"),
                                        resultSet.getString("password")
                                );
                            }
                            return null;
                        }
                    }
            );
        } catch (Exception e) {
            System.err.println("Something went wrong in AccountRepository: finding account by username");
        }
        return null;
    }
}
