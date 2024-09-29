package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;


public class AccountDAO {
    
    public Account createAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());


            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                account.setAccount_id(rs.getInt("account_id"));
                return account;
            }
        } catch (SQLException e) {
           System.out.println(e.getMessage());
            // TODO: handle exception
        }

        return null;
    }

    public Account getAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);


            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Account(rs.getInt("account_id"),
                                   rs.getString("username"),
                                   rs.getString("password")
                                    );
            }

        } catch (Exception e) {
            
            System.out.println(e.getMessage());
            // TODO: handle exception
        }

        return null;
    }




}
