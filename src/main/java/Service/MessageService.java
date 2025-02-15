package Service;

import Model.*;
import DAO.*;

import java.util.ArrayList;
import java.util.List;

public class MessageService {

    public MessageDAO messageDAO;
    
    /**
     * No Args constructor for MessageService creating new MessageDAO.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Method used to send a message posting it to Message table
     * @param message
     * @return message sent, null if message length is blank or > 255 in length
     */
    public Message sender(Message message){
        if (message.getMessage_text().length() <= 0 || message.getMessage_text().length() > 255){
            return null;
        }
        return this.messageDAO.sendMessage(message);
    }

    /**
     * Method to get all messages.
     * @return list of all messages
     */
    public List<Message> getMessages(){
        return this.messageDAO.getAllMessages();
    }

    /**
     * Finds a certain message by its message_id
     * @param id message_id to be searched
     * @return message if found
     */
    public Message findMessage(int id){
        return this.messageDAO.findMessageByID(id);
    }

    /**
     * Deletes a messages using its message_id
     * @param id message_id to be searched
     * @return true if message is found and deleted, false otherwise
     */
    public boolean delMessage(int id){
        return this.messageDAO.delMessageByID(id);
    }

    /**
     * Updates a message_text with String msg, using message_id
     * @param msg new message_text
     * @param id message_id to be searched
     * @return true if message update successful, false if msg is blank or > 255 chars or if messages does not exist.
     */
    public boolean UpdMessage(String msg, int id){
        if (msg.length() <= 0 || msg.length() > 255){
            return false;
        }
        return this.messageDAO.updateMessageByID(msg, id);
    }
    
    /**
     * Method to find all messages from account with a certain account_id
     * @param id account_id to be searched
     * @return list of messages from account with account_id id
     */
    public List<Message> getMessagesID(int id){
        return this.messageDAO.getMessagesFromAccount(id);
    }
}
