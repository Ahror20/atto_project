package org.example.repository;

import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.enums.GeneralStatus;
import org.example.enums.ProfileRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProfileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Profile getProfileByPhoneAndPassword(String phone, String password) {
        String sql = "select * from profile where phone=? and password=?";
        List<Profile> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class), phone,password);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    public Profile getProfileByPhone(String phone) {
        String sql = "select * from profile where phone=?";
        List<Profile> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class), phone);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);

    }


    public Boolean isPhoneExist(String phone) {
        String sql = "select id from profile where phone=?";
        List<Profile> list =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class), phone);
        if (list.isEmpty()){
            return false;
        }
        return true;
    }

    public Integer saveProfile(Profile profile) {
        String sql = "insert into profile (name,surname,phone,password,role,status,created_date) values(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, profile.getName(), profile.getSurname(), profile.getPhone(), profile.getPassword(), profile.getRole().name(), profile.getStatus().name(), profile.getCreatedDate());

    }


    public List<Profile> getProfileList() {
        String sql = "select * from profile";
        List<Profile> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));
        return list;

    }

    public Integer changeProfileStatus(String phone, GeneralStatus status) {
        String sql = "update profile set status = ? where phone = ?";
       return jdbcTemplate.update(sql, status.name(), phone);

    }
}
