package project.vilsoncake.botadminpanel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import project.vilsoncake.botadminpanel.dto.FormattedUserStatisticDto;
import project.vilsoncake.botadminpanel.entity.document.UserStatisticDocument;

import java.util.List;
import java.util.UUID;

public interface UserStatisticRepository extends MongoRepository<UserStatisticDocument, UUID> {
    @Aggregation(pipeline = {
            "{ $group: { _id: '$username', count: { $sum: 1 } } }",
            "{ $sort: { count: -1 } }",
            "{ $project: { _id: 0, username: '$_id', count: 1 } }"
    })
    List<FormattedUserStatisticDto> getFormattedUsersStatistic();
    Page<UserStatisticDocument> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
