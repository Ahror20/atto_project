package org.example.repository;

import org.example.dto.Card;
import org.example.dto.Terminal;
import org.example.enums.GeneralStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TerminalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Terminal terminal) {
        String sql = "insert into terminal (code, address,created_date,status) values(?,?,?,?)";
        return jdbcTemplate.update(sql, terminal.getCode(), terminal.getAddress(), terminal.getCreatedDate(), terminal.getStatus().name());


    }

    public Terminal getTerminalByCode(String code) {
        String sql = "select * from terminal where visible = true and code=?";
        List<Terminal> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Terminal.class), code);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }

    public List<Terminal> getTerminalList() {
        String sql = "select * from terminal where visible = true";
        List<Terminal> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Terminal.class));
        return list;

    }

    public int updateTerminal(Terminal terminal) {
        String sql = "update terminal set address = ? where code = ?";
        return jdbcTemplate.update(sql, terminal.getAddress(), terminal.getCode());

    }

    public int changeTerminalStatus(String code, GeneralStatus status) {
        String sql = "update terminal set status = ? where code = ?";
        return jdbcTemplate.update(sql, status.name(), code);

    }

    public int deleteTerminal(String code) {
        String sql = "update terminal set visible = false where code = ?";
        return jdbcTemplate.update(sql, code);

    }


}
