package project.vilsoncake.botadminpanel.dto;

import lombok.Data;
import project.vilsoncake.botadminpanel.entity.document.FeedbackDocument;
import project.vilsoncake.botadminpanel.utils.LocalApplicationDate;

@Data
public class FormattedFeedbackDto {
    private String username;
    private String text;
    private String createdAt;

    public static FormattedFeedbackDto fromDocument(FeedbackDocument feedbackDocument, String timezone) {
        FormattedFeedbackDto formattedFeedbackDto = new FormattedFeedbackDto();
        formattedFeedbackDto.setCreatedAt(new LocalApplicationDate(feedbackDocument.getCreatedAt(), timezone).getTime());
        formattedFeedbackDto.setUsername(feedbackDocument.getUsername());
        formattedFeedbackDto.setText(feedbackDocument.getText());

        return formattedFeedbackDto;
    }
}
