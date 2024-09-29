package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(),Account.class);
        Account addedUser = accountService.registerAccount(account);
        if(addedUser != null){
            ctx.json(addedUser);
        } else {
            ctx.status(400);
        }

    }

    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(),Account.class);
        Account authAccount =  accountService.loginAccount(account.getUsername(),account.getPassword());

        if(authAccount != null){
            ctx.json(authAccount);
        }else{
            ctx.status(401);
        }

    }
    
    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if(addedMessage != null){
            ctx.json(addedMessage);
        }else{
            ctx.status(400);
        }
    }


    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if(message != null){
            ctx.json(message);
        }else{
            ctx.status(200).result("");
        }

    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);
        if(deletedMessage != null){
            ctx.json(deletedMessage);
        }else{
            ctx.status(200).result("");
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getAllMessages());
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessageById(messageId, message);
        if(updatedMessage != null){
            ctx.json(updatedMessage);
        }else{
            ctx.status(400);
        }

    }

    private void getAllMessagesByUserHandler(Context ctx) throws JsonProcessingException{
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllMessagesByUser(accountId));
    }

    
}