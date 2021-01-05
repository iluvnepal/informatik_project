import common.entity.TeamData;
import common.util.UnderstatDataParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Application {

    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        final UnderstatDataParser understatDataParser = new UnderstatDataParser();
        // todo: create proper logger slj4 or something.
        final Map<Integer, TeamData> teamDataFromJsonFile = understatDataParser.createTeamDataFromJsonFile("resources/Serie_A2019");
        //                understatDataParser.scrapeTeamsDataToJsonFile();
    }
}
