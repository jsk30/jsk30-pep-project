package Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * Method that defines the Javalin Social Media API using handler methods to manage context objects and jsons.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::sendMsgHandler);
        app.get("/messages", this::getAllMsgHandler);
        app.get("/messages/{message_id}", this::getMsgIDHandler);
        app.delete("/messages/{message_id}", this::delMsgHandler);
        app.patch("/messages/{message_id}", this::UpdateMsgHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMsgFromIDHandler);


        return app;
    }

    /**
     * This is the register handler.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account registered = accountService.register(account);
        if (registered != null){
            context.json(om.writeValueAsString(registered)).status(200);
        }else{
            context.status(400);
        }
    }

    /**
     * This is the login handler.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account login = accountService.login(account);
        if (login != null){
            context.json(om.writeValueAsString(login)).status(200);
        }else{
            context.status(401);
        }
    }
    
    /**
     * Message post handler
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void sendMsgHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        if (accountService.accountDAO.findUserByID(message.getPosted_by()) != null){
            Message sendMsg = messageService.sender(message);
            if (sendMsg != null){
                context.json(om.writeValueAsString(sendMsg)).status(200);
            }else{
                context.status(400);
            }
        }else{
            context.status(400);
        }
    }

    /**
     * Get all messages handler.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMsgHandler(Context context){
        List<Message> msgs = messageService.getMessages();
        context.json(msgs).status(200);
    }

    /**
     * Get all messages from account handler.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMsgFromIDHandler(Context context){
        int id = Integer.parseInt(context.pathParam("account_id"));
        List<Message> msgs = messageService.getMessagesID(id);
        context.json(msgs).status(200);
    }

    /**
     * Get Message by ID handler.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMsgIDHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message msg = messageService.findMessage(id);
        if (msg == null){
            context.status(200);
        }else{
            context.json(msg).status(200);
        }
        
    }

    /**
     * Delete Message handler.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void delMsgHandler(Context context){
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message msg = messageService.findMessage(id);
        boolean deleted = messageService.delMessage(id);
        if (deleted){
            context.json(msg).status(200);
        }else{
            context.status(200);
        }
        
    }

    /**
     * Update Message handler.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void UpdateMsgHandler(Context context) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        int id = Integer.parseInt(context.pathParam("message_id"));
        boolean updated = messageService.UpdMessage(message.getMessage_text(), id);
        if (updated){
            Message updated_msg = messageService.findMessage(id);
            context.json(updated_msg).status(200);
        }else{
            context.status(400);
        }
        
    }
}