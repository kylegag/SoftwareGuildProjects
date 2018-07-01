/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.powerball.data;

import com.sg.powerball.models.Pick;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class PowerballJdbcTemplateDao implements PowerballDao {

    @Autowired
    private JdbcTemplate jt;

    @Override
    public boolean addPick(Pick pick) {
        return jt.update(
                "INSERT INTO Pick (firstName, lastName, email, state, firstNum, secondNum, thirdNum, fourthNum, fifthNum, powerBall, isQuickPick, isValid, pickDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                pick.getFirstName(),
                pick.getLastName(),
                pick.getEmail(),
                pick.getState(),
                pick.getFirstNum(),
                pick.getSecondNum(),
                pick.getThirdNum(),
                pick.getFourthNum(),
                pick.getFifthNum(),
                pick.getPowerBall(),
                pick.isIsQuickPick(),
                pick.isIsValid(),
                pick.getPickDate()) > 0;
    }

    @Override
    public List<Pick> getAll() {
        String sql = "select * from pick;";
        return jt.query(sql, new PickMapper());
    }

    @Override
    public List<Pick> search(String firstName, String lastName, String email, String state, int pickId) {
        String sql = "select * from pick "
                + "where (firstName is null OR firstName like ?) "
                + "and (lastName is null OR lastName like ?) "
                + "and (email is null OR email like ?) "
                + "and (state is null OR state like ?)";

        if (pickId < 0) {
            sql += ";";
            return jt.query(sql, new PickMapper(), firstName + '%', lastName + '%', email + '%', state + '%');
        } else {
            sql += " and (pickId = ? );";
            return jt.query(sql, new PickMapper(), firstName + '%', lastName + '%', email + '%', state + '%', pickId);
        }
    }

    @Override
    public void markAllAsInvalid() {
        String sql = "UPDATE Pick SET"
                + " isValid = false"
                + " where 0 < pickId;"; // Necessary for updating all columns in safe update mode

        jt.update(sql);
    }

    private static final class PickMapper implements RowMapper<Pick> {

        @Override
        public Pick mapRow(ResultSet rs, int i) throws SQLException {
            Pick pick = new Pick();
            pick.setPickId(rs.getInt("pickId"));
            pick.setFirstName(rs.getString("firstName"));
            pick.setLastName(rs.getString("lastName"));
            pick.setEmail(rs.getString("email"));
            pick.setState(rs.getString("state"));
            pick.setFirstNum(rs.getInt("firstNum"));
            pick.setSecondNum(rs.getInt("secondNum"));
            pick.setThirdNum(rs.getInt("thirdNum"));
            pick.setFourthNum(rs.getInt("fourthNum"));
            pick.setFifthNum(rs.getInt("fifthNum"));
            pick.setPowerBall(rs.getInt("powerBall"));
            pick.setIsQuickPick(rs.getBoolean("isQuickPick"));
            pick.setIsValid(rs.getBoolean("isValid"));
            pick.setPickDate(rs.getDate("pickDate"));

            return pick;
        }

    }

}
