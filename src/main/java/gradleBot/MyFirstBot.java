package gradleBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public class MyFirstBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var messageText = update.getMessage().getText();
            var chatID = update.getMessage().getChatId();
            var fromID = update.getMessage().getFrom().getId();
            var messageID = update.getMessage().getMessageId();
//            if (messageText.equals("/start")){
//
//            }
            if (messageText.equals("create")){
                DBConnection.createDB();
                sendMessage("Done DB Created!!",chatID,messageID);
            }
            if (messageText.equals("send")){
                sendMessage("Wait",chatID,messageID);
                sendFile(chatID,"Your DB");
            }
        }
    }

    private void sendMessage(String messageText, long userID, int messageID) {
        SendMessage sendMessage = new SendMessage()
                .setText(messageText)
                .setChatId(userID)
                .setReplyToMessageId(messageID);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendFile(long chatID,String caption){
        SendDocument sendDocument = new SendDocument()
                .setDocument(new File("./database.shafiq"))
                .setChatId(chatID)
                .setCaption(caption);
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendMessage(e.toString(),chatID,0);
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "1385028929:AAFUB18Ap1Nq3mw-3Sj5aSzUID4nTq787iU";
    }
}
