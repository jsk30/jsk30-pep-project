package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    /**
     * Login authentication
     * @param String username
     * @param String password
     * @return Account object if username and password matches object
     */
    public Account login(String user, String pass){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Account WHERE username = ? and password = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));
                return account;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }


    /**
     * Registers account, adding account to the Account table.
     * @param account Object of type Account
     * @return Account registered.
     */
    public Account register(String user, String pass){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "INSERT INTO Account(username, password) VALUES (?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            
            ps.executeUpdate();
            return this.findUser(user);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds account in table by username.
     * @param user username
     * @return account of user if username is found.
     */
    public Account findUser(String user){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Account WHERE username = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));
                return account;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }

    /**
     * Finds Account by ID
     * @param id id to be searched
     * @return account if found
     */
    public Account findUserByID(int id){
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT * FROM Account WHERE account_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));
                return account;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    
        return null;
    }

}