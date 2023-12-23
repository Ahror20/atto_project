package org.example.repository;

import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.enums.GeneralStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Card card) {
        String sql = "insert into card (card_number, exp_date,balance,status,phone,created_date) values(?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, card.getCardNumber(), card.getExpDate(), card.getBalance(), card.getStatus().name(), card.getPhone(), card.getCreatedDate());

    }

    public Card getCardById(Integer id) {
        String sql = "select * from card where id=?";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class), id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    public void addMinusCard(String card_number, Double balance) {
        String sql = "update card set balance = ? where card_number = ?";
        jdbcTemplate.update(sql, balance, card_number);
    }

    public List<Card> getList() {
        String sql = "select * from card";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
        return list;
    }

    public Card getCardByNumber(String number) {
        String sql = "select * from card where card_number=?";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class), number);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public int assignPhoneToCard(String phone, String cardNum) {
        String sql = "update card set phone = ? where card_number = ?";
        return jdbcTemplate.update(sql, phone, cardNum);

    }

    public List<Card> getCardByProfilePhone(String phone) {
        String sql = "select * from card where phone=?";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class), phone);
        return list;

    }

    public int updateCardStatus(String cardNum, GeneralStatus status) {
        String sql = "update card set status = ? where card_number = ?";
        return jdbcTemplate.update(sql, status.name(), cardNum);


    }

    public int deleteCard(String cardNumber) {
        String sql = "update card set visible = false where card_number = ?";
        return jdbcTemplate.update(sql, cardNumber);

    }

    public int updateCard(Card card) {
        String sql = "update card set exp_date = ? where card_number = ?";
        return jdbcTemplate.update(sql, card.getExpDate(), card.getCardNumber());

    }

    public int refillCard(String cardNum, Double amount) {
        String sql = "update card set balance = ? where card_number = ?";
        return jdbcTemplate.update(sql, amount, cardNum);

    }
}
