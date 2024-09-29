package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message){
        if(message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) return null;

        if(messageDAO.getMessageById(message.getMessage_id()) == null) return messageDAO.createMessage(message);
        return null;
    }

    public Message deleteMessage(int message_id){
        
        Message existingMessage = messageDAO.getMessageById(message_id);
        
        if(existingMessage != null){

        boolean isDeleted = messageDAO.deleteMessageById(message_id);
            
        if (isDeleted) {
            return existingMessage;
        }
    }
        return null;
    }

    public Message getMessageById(int message_id){
        Message message = messageDAO.getMessageById(message_id);
        
        if(message != null) return message;
        
        return null;

    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message updateMessageById(int message_id, Message message){
        String message_text = message.getMessage_text();
        if(message_text.length() > 255 || message_text.isBlank()) return null;
        
        Message updatedMessage = messageDAO.getMessageById(message_id);
        
        if(updatedMessage != null) {
            boolean isUpdated = messageDAO.updateMessageById(message_id, message_text);
            if(isUpdated){
                updatedMessage.setMessage_text(message_text);
                
                return updatedMessage; 
            }
            
        }
        
        return null;
    }

    public List<Message> getAllMessagesByUser(int accountId){
        return messageDAO.getAllMessagesByUser(accountId);
    }


}
