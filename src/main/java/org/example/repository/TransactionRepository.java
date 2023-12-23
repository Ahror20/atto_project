package org.example.repository;

import org.example.dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class TransactionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

   public int createTransaction(Transaction transaction) {
       String sql = "insert into transaction (card_id, terminal_id,amount,type,created_date) values(?,?,?,?,?)";
       jdbcTemplate.update(sql, transaction.getCardId(),transaction.getTerminalId(), transaction.getAmount(), transaction.getTransactionType().name(),  transaction.getCreatedDate());
//        try (Connection connection = DataBase.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(
//                    "insert into transaction(card_id,terminal_id,amount,type,created_date) " +
//                            "values (?,?,?,?,?)");
//            statement.setInt(1, transaction.getCardId());
//
//            if (transaction.getTerminalId() != null) {
//                statement.setInt(2, transaction.getTerminalId());
//            } else {
//                statement.setObject(2, null);
//            }
//
//            statement.setDouble(3, transaction.getAmount());
//            statement.setString(4, transaction.getTransactionType().name());
//            statement.setTimestamp(5, Timestamp.valueOf(transaction.getCreatedDate()));
//
//            int resultSet = statement.executeUpdate();
//            return resultSet;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return 0;

    }
    public List<Transaction> getAll(){
        String sql = " select * from transaction";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }

    public List<Transaction> transactionBetweenDay(LocalDateTime fromDate, LocalDateTime toDate){
        String sql = "SELECT * FROM transaction WHERE created_date BETWEEN ? AND ?";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class),fromDate,toDate);
        return list;
    }


}
