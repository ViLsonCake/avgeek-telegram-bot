package project.vilsoncake.botadminpanel.service;

import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.dto.FeedbackDto;

public interface FeedbackService {
    String getFeedbackPage(Model model);
    boolean addNewFeedback(FeedbackDto feedbackDto);
}
