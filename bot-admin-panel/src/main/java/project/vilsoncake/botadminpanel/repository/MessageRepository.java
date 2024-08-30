package project.vilsoncake.botadminpanel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.vilsoncake.botadminpanel.entity.document.MessageDocument;

import java.util.UUID;

public interface MessageRepository extends MongoRepository<MessageDocument, UUID> {
}
