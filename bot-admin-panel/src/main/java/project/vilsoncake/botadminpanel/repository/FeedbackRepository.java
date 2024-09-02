package project.vilsoncake.botadminpanel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import project.vilsoncake.botadminpanel.entity.document.FeedbackDocument;

import java.util.UUID;

public interface FeedbackRepository extends MongoRepository<FeedbackDocument, UUID> {
}
