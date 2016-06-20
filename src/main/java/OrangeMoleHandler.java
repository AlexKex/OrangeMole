import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import utils.configuration.Config;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by apryakhin on 16.06.2016.
 */
public class OrangeMoleHandler extends TelegramLongPollingBot {
    private final String BOTNAME = "orangemole";
    private ArrayList<String> misunderstand = new ArrayList<String>() {{
                    add("No comprende");
                    add("Не понимаю");
                    add("WAT?!");
                    add("Это выше моих сил");
                    add("Много букав, ниасилил");
    }};

    private String botToken;
    private String botName;

    private Config configuration;

    protected boolean is_multithread = false;
    protected int thread_count = 1;

    public OrangeMoleHandler(){
        super();

        configuration = Config.i();
        configuration.addConfig(BOTNAME);

        botToken = configuration.getConfigurationMapItem(BOTNAME + ".token");
        botName = configuration.getConfigurationMapItem(BOTNAME + ".name");
    }

    /**
     * Метод обработки полученного сообщения
     * @param update
     */
    public void onUpdateReceived(Update update) {
        System.out.println(update.getUpdateId()); // id поступившего сообщения

        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            OrangeMoleUser user = new OrangeMoleUser(message.getFrom());

            System.out.println(message.getChat().getId());

            String msg = message.getText();
            String[] command = msg.split(" ");

            if(user.isAdmin()){
                switch(command[0]){
                    case "/start":
                        sendOrangeMoleMessage(message, "I fight for the users!");
                    case "/help":
                        StringBuilder sb = new StringBuilder();
                        sb.append("Here's the list of basic commands");
                        sb.append("\n");
                        sb.append("/useradd %telegram user id% - will add user to the access list");
                        sb.append("\n");
                        sb.append("/userdel %telegram user id% - will remove user from the access list");
                        sb.append("\n");
                        sb.append("/actionlist - will return the list of available actions");
                        sb.append("\n");
                        sb.append("/userlist - will return the list of users");
                        sb.append("\n");
                        sb.append("/doaction %action id% - will collect data with logic if selected action");
                        sb.append("\n");
                        sb.append("/userpermissionlist %user name% - will return the list of available actions for user name");
                        sb.append("\n");
                        sb.append("/setpermissionbynickname %user name% %role id% - will set roleid to user name");
                        sb.append("\n");
                        sb.append("/setpermissionbyuserid %user id% %role id% - will set role id to user id");
                        sb.append("\n");
                        sb.append("/revokepermissionbynickname %user name% %role id% - will revoke role id from username");
                        sb.append("\n");
                        sb.append("/revokepermissionbyid %user name% %role id% - will revoke role id from user name");

                        sendOrangeMoleMessage(message, sb.toString());
                        break;
                    case "userdel":
                        // TODO check permissions for action

                        break;
                    case "useradd":
                        // TODO set permissions for action

                        break;
                    case "doaction":
                        // TODO set permissions for action

                        break;
                    default:
                        int rand = ThreadLocalRandom.current().nextInt(0, misunderstand.size());

                        sendOrangeMoleMessage(message, misunderstand.get(rand));
                        break;
                }
            }
            else{
                sendOrangeMoleMessage(message, "Нет доступа к функционалу бота");
            }


        }
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotPath() {
        return BOTNAME;
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
