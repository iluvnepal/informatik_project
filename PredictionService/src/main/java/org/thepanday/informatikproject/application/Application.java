package org.thepanday.informatikproject.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        //        final UnderstatDataParser understatDataParser = new UnderstatDataParser();
        //        understatDataParser.create_team_data();
        //        understatDataParser.write_string_to_file();
    }
}
