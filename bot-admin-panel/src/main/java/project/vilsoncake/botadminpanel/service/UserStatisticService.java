package project.vilsoncake.botadminpanel.service;

import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.dto.UserStatisticDto;

public interface UserStatisticService {
    String getUsersStatisticPage(Model model);
    boolean addNewUserStatistic(UserStatisticDto userStatisticDto);
}
