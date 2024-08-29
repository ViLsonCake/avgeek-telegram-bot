package project.vilsoncake.botadminpanel.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.dto.UserStatisticDto;
import project.vilsoncake.botadminpanel.entity.document.UserStatisticDocument;
import project.vilsoncake.botadminpanel.repository.UserStatisticRepository;
import project.vilsoncake.botadminpanel.service.UserStatisticService;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserStatisticServiceImpl implements UserStatisticService {

    private final UserStatisticRepository userStatisticRepository;

    @Value("${github.repository-link}")
    private String repositoryLink;

    @Override
    public String getUsersStatisticPage(Model model) {
        model.addAttribute("repositoryLink", repositoryLink);
        model.addAttribute("usersActivity", userStatisticRepository.getFormattedUsersStatistic());
        model.addAttribute("usersLastActivity", userStatisticRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 20)).getContent());

        return "users-statistic.html";
    }

    @Override
    public boolean addNewUserStatistic(UserStatisticDto userStatisticDto) {
        UserStatisticDocument userStatisticDocument = new UserStatisticDocument();
        userStatisticDocument.setId(UUID.randomUUID());
        userStatisticDocument.setCreatedAt(new Date());
        userStatisticDocument.setUsername(userStatisticDto.getUsername());
        userStatisticDocument.setCommandName(userStatisticDto.getCommandName());

        userStatisticRepository.save(userStatisticDocument);

        log.info("User {} statistic added", userStatisticDto.getUsername());

        return true;
    }
}
