import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import common.util.UnderstatDataParser;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        final UnderstatDataParser understatDataParser = new UnderstatDataParser();
        understatDataParser.create_team_data();
//        understatDataParser.write_string_to_file();
    }
}
