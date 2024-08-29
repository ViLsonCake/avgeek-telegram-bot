package project.vilsoncake.botadminpanel.entity.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Document
public class UserStatisticDocument {
    private UUID id;
    private Date createdAt;
    private String username;
    private String commandName;
}
