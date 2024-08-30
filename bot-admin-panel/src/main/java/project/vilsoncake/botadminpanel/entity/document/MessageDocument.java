package project.vilsoncake.botadminpanel.entity.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Document
public class MessageDocument {

    @Id
    private UUID id;
    private Date createdAt;
    private String englishText;
    private String russianText;
    private String ukrainianText;
}
