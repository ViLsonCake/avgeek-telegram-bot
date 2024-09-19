package project.vilsoncake.botadminpanel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.dto.UserDto;
import project.vilsoncake.botadminpanel.repository.UserRepository;
import project.vilsoncake.botadminpanel.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${github.repository-link}")
    private String repositoryLink;

    @Value("${better-stack.link}")
    private String betterStackLink;

    @Value("${app.timezone}")
    private String timezone;

    @Override
    public String getUsersPage(Model model) {
        List<UserDto> users = userRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(user -> UserDto.fromEntity(user, timezone))
                .toList();

        model.addAttribute("users", users);
        model.addAttribute("usersCount", users.size());
        model.addAttribute("repositoryLink", repositoryLink);
        model.addAttribute("betterStackLink", betterStackLink);

        if (!users.isEmpty()) {
            model.addAttribute("lastAddedUser", users.get(0));
        }

        return "users.html";
    }
}
