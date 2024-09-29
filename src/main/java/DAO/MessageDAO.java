package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;
import io.javalin.util.ConcurrencyUtil;

public class MessageDAO{

    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message( posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            

            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3,message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                message.setMessage_id(rs.getInt("message_id"));
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());

            // TODO: handle exception
        }
        return null;
    }


    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedstatment = connection.prepareStatement(sql);

            ResultSet rs = preparedstatment.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            messages.add(message);
                
            }
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
        return messages;
    }

    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }

        return null;
    }

    public boolean deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);
            int rowsAffected = preparedStatement.executeUpdate();
            
            if(rowsAffected > 0) return true;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
    
        return false;
    
    }   

    public boolean updateMessageById(int message_id, String message_text){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);
            int rowsAffected = preparedStatement.executeUpdate();
            
            if(rowsAffected > 0) return true;


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
    
        return false;
    
    }   

    public List<Message> getAllMessagesByUser(int accountId){
        Connection connection = ConnectionUtil.getConnection();

        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message where posted_by = ?";
            PreparedStatement preparedstatment = connection.prepareStatement(sql);
            preparedstatment.setInt(1, accountId);
            ResultSet rs = preparedstatment.executeQuery();
            while(rs.next()){
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            messages.add(message);
                
            }
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
        }
        return messages;
        
    }


}