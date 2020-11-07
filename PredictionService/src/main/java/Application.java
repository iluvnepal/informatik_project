import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.util.UnderstatDataParser;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

public class Application {



    private final String[] HISTORY_COLUMNS = { "h_a", "xG", "xGA", "npxG", "npxGA", "ppda_att", "ppda_def", "ppda_allowed_att", "ppda_allowed_def", "deep",
                                               "deep_allowed", "scored", "missed", "xpts", "result", "date", "wins", "draws", "loses", "pts", "npxGD" };

    public static void main(String[] args) {
        final UnderstatDataParser understatDataParser = new UnderstatDataParser();
        understatDataParser.create_team_data();
    }
}
