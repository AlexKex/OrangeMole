import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;
import utils.configuration.Config;

/**
 * Created by apryakhin on 17.06.2016.
 */
public class OrangeMoleUser {
    private int id;
    private String firstname;
    private String lastname;

    public OrangeMoleUser(User user){
        id          = user.getId();
        firstname   = user.getFirstName();
        lastname    = user.getLastName();
    }

    public boolean hasAccess(){
        return false;
    }

    public boolean isAdmin(){
        boolean is_admin = false;

        // check config
        Config configuration = Config.i();
        String botname = configuration.getConfigurationMapItem("appname");
        int admin_id = Integer.parseInt(configuration.getConfigurationMapItem(botname + ".admin_id"));

        if(id == admin_id){
            is_admin = true;
        }

        return is_admin;
    }
}
