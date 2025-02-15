package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * Finds Message by ID.
     * @param id id to be searched.
     * @return Message with searched id, null if not found
     */
    public Message findMessageByID(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Message WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return m;
            }

            
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }

    /**
     * Deletes Message by ID.
     * @param id id to be searched.
     * @return true if deleted, false otherwise
     */
    public boolean delMessageByID(int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "DELETE FROM Message WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            }

            
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return false;
    }

    /**
     * Deletes Message by ID.
     * @param id id to be searched.
     * @return true if deleted, false otherwise
     */
    public boolean updateMessageByID(String msg, int id) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "UPDATE Message SET message_text = ? where message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, msg);
            ps.setInt(2, id);
            
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            }

            
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return false;
    }

    /**
     * Finds specific Message given Message info
     * @param posted_by
     * @param msg
     * @param time
     * @return message with its message_id
     */
    public Message findMessage(int posted_by, String msg, Long time){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Message WHERE posted_by = ? and message_text = ? and time_posted_epoch = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, posted_by);
            ps.setString(2, msg);
            ps.setLong(3, time);

            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                return m;
            }

            
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }

    /**
     * Method returns all messages in the data table.
     * @return list of all Message in Message table.
     */
    public List<Message> getAllMessages(){
        List<Message> messageList = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Message";

            PreparedStatement ps = conn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Message m = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messageList.add(m);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messageList;
    }

    /**
     * Method returns all messages from specific account_id in data table.
     * @return list of all Message in Message table.
     */
    public List<Message> getMessagesFromAccount(int id){
        List<Message> messageList = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Message WHERE posted_by = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Message m = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch"));
                messageList.add(m);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return messageList;
    }

    /**
     * Method used for post request to send message to data table.
     * @param message message to be sent
     * @return message sent
     */
    public Message sendMessage(Message message) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            
            ps.executeUpdate();
            
            return this.findMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }
}
