import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

/**
 * Created by apryakhin on 16.06.2016.
 */

public class OrangeMole {

    public static void main(String[] args){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            OrangeMoleHandler mole = new OrangeMoleHandler();
            System.out.println(mole.getBotToken());
            telegramBotsApi.registerBot(mole);
        }
        catch(TelegramApiException e){
            e.printStackTrace();
        }
    }
}
