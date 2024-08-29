package project.vilsoncake.botadminpanel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticDto {
    private String username;
    private String commandName;
}