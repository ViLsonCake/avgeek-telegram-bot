package project.vilsoncake.botadminpanel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.vilsoncake.botadminpanel.entity.UserEntity;
import project.vilsoncake.botadminpanel.repository.UserRepository;
import project.vilsoncake.botadminpanel.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String getUsersPage(Model model) {
        List<UserEntity> users = userRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("users", users);
        model.addAttribute("usersCount", users.size());

        if (!users.isEmpty()) {
            model.addAttribute("lastAdded", users.get(0));
        }

        return "users.html";
    }
}
