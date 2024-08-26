package project.vilsoncake.botadminpanel;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class BotAdminPanelApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotAdminPanelApplication.class, args);
    }

}
