import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import utils.ConfigReader;

/**
 * Created by apryakhin on 16.06.2016.
 */
public class OrangeMoleHandler extends TelegramLongPollingBot {
    protected String botToken;
    protected boolean is_multithread = false;
    protected int thread_count = 1;

    public OrangeMoleHandler(){
        super();

        ConfigReader cr = new ConfigReader("orangemole");
        cr.readConfig();

        botToken = cr.getConfigurationMapItem("orangemole.token");
    }

    /**
     * Метод обработки полученного сообщения
     * @param update
     */
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help"))
                sendOrangeMoleMessage(message, "Привет, я робот");
            else
                sendOrangeMoleMessage(message, "Я не знаю что ответить на это");
        }
    }

    public String getBotUsername() {
        return "OrangeMoleBot";
    }

    public String getBotToken() {
        return botToken;
    }

    private void sendOrangeMoleMessage(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplayToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
