package project.vilsoncake.botadminpanel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.vilsoncake.botadminpanel.service.FeedbackService;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public String getFeedbackPage(Model model) {
        return feedbackService.getFeedbackPage(model);
    }
}
