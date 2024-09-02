package project.vilsoncake.botadminpanel.dto;

import lombok.Data;
import project.vilsoncake.botadminpanel.entity.document.FeedbackDocument;

import java.text.SimpleDateFormat;

@Data
public class FormattedFeedbackDto {
    private String username;
    private String text;
    private String createdAt;

    public static FormattedFeedbackDto fromDocument(FeedbackDocument feedbackDocument) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss-dd.MM.yyyy");

        FormattedFeedbackDto formattedFeedbackDto = new FormattedFeedbackDto();
        formattedFeedbackDto.setCreatedAt(simpleDateFormat.format(feedbackDocument.getCreatedAt()));
        formattedFeedbackDto.setUsername(feedbackDocument.getUsername());
        formattedFeedbackDto.setText(feedbackDocument.getText());

        return formattedFeedbackDto;
    }
}
