package project.vilsoncake.botadminpanel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.dto.FeedbackDto;
import project.vilsoncake.botadminpanel.dto.FormattedFeedbackDto;
import project.vilsoncake.botadminpanel.entity.document.FeedbackDocument;
import project.vilsoncake.botadminpanel.repository.FeedbackRepository;
import project.vilsoncake.botadminpanel.service.FeedbackService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Value("${github.repository-link}")
    private String repositoryLink;

    @Value("${better-stack.link}")
    private String betterStackLink;

    @Override
    public String getFeedbackPage(Model model) {
        List<FormattedFeedbackDto> usersFeedback = feedbackRepository.findAll().stream().map(FormattedFeedbackDto::fromDocument).toList();

        model.addAttribute("usersFeedback", usersFeedback);
        model.addAttribute("repositoryLink", repositoryLink);
        model.addAttribute("betterStackLink", betterStackLink);

        return "feedback.html";
    }

    @Override
    public boolean addNewFeedback(FeedbackDto feedbackDto) {
        FeedbackDocument feedbackDocument = new FeedbackDocument();
        feedbackDocument.setId(UUID.randomUUID());
        feedbackDocument.setCreatedAt(new Date());
        feedbackDocument.setUsername(feedbackDto.getUsername());
        feedbackDocument.setText(feedbackDto.getText());
        feedbackRepository.save(feedbackDocument);

        log.info("User {} feedback saved", feedbackDto.getUsername());

        return true;
    }
}
