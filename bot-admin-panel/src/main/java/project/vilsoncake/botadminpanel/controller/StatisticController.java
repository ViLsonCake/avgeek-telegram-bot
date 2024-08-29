package project.vilsoncake.botadminpanel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.vilsoncake.botadminpanel.service.UserStatisticService;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final UserStatisticService userStatisticService;

    @GetMapping("/users")
    public String getUsersStatisticPage(Model model) {
        return userStatisticService.getUsersStatisticPage(model);
    }
}
