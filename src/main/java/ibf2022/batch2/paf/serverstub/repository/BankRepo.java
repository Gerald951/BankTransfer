package ibf2022.batch2.paf.serverstub.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

@Repository
public class BankRepo {

    private final static String checkFunds = "select * from acc where acc_name = ?";
    private final static String getBalance = "select * from balance where account_id =?";
    private final static String updateBalance= "update balance set balance_amount=? where account_id=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Boolean checkBalance(String name, Float amount) {
        SqlRowSet result = jdbcTemplate.queryForRowSet(checkFunds, name);
        result.next();
        Integer acc_id = result.getInt("id");

        SqlRowSet retrieveBalance = jdbcTemplate.queryForRowSet(getBalance, acc_id);
        retrieveBalance.next();
        Float balance = retrieveBalance.getFloat("balance_amount");

        return balance >= amount ? true : false;
    }
    
    public Boolean deductFunds(String name, Float amount) {

        SqlRowSet checkId = jdbcTemplate.queryForRowSet(checkFunds, name);
        checkId.next();
        Integer acc_id = checkId.getInt("id");

        SqlRowSet retrieveBalance = jdbcTemplate.queryForRowSet(getBalance, acc_id);
        retrieveBalance.next();
        Float balance = retrieveBalance.getFloat("balance_amount");

        Integer updated = jdbcTemplate.update(updateBalance, balance-amount, acc_id);

        return updated>=0 ? true : false;

    }

    public Boolean addFunds(String name, Float amount) {
        SqlRowSet checkId = jdbcTemplate.queryForRowSet(checkFunds, name);
        checkId.next();
        Integer acc_id = checkId.getInt("id");

        SqlRowSet retrieveBalance = jdbcTemplate.queryForRowSet(getBalance, acc_id);
        retrieveBalance.next();
        Float balance = retrieveBalance.getFloat("balance_amount");

        Integer updated = jdbcTemplate.update(updateBalance, balance+amount, acc_id);

        return updated>=0 ? true : false;
    }
}
