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
        List<UserEntity> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "users.html";
    }
}
