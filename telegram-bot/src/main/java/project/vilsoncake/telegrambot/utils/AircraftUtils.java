package project.vilsoncake.telegrambot.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import project.vilsoncake.telegrambot.dto.AircraftFamilyDto;
import project.vilsoncake.telegrambot.entity.AircraftEntity;
import project.vilsoncake.telegrambot.entity.enumerated.AircraftFamily;
import project.vilsoncake.telegrambot.exception.AircraftNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AircraftUtils {

    public InlineKeyboardMarkup getAircraftButtons(List<AircraftFamilyDto> aircraft) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        int count = 0;
        for (AircraftFamilyDto aircraftFamilyDto : aircraft) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(aircraftFamilyDto.getText());
            button.setCallbackData(aircraftFamilyDto.getCallbackData().toString());

            rowInline.add(button);
            count++;

            if (count % 3 == 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }
        }

        InlineKeyboardButton acceptInlineButton = new InlineKeyboardButton();
        acceptInlineButton.setText("✅");
        acceptInlineButton.setCallbackData("ACCEPT");

        InlineKeyboardButton cancelInlineButton = new InlineKeyboardButton();
        cancelInlineButton.setText("❌");
        cancelInlineButton.setCallbackData("CANCEL");

        rowInline.add(cancelInlineButton);
        rowInline.add(acceptInlineButton);

        rowsInline.add(rowInline);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowsInline);

        return inlineKeyboardMarkup;
    }

    public String getAircraftNameByCallback(List<AircraftFamilyDto> aircraft, AircraftFamily aircraftFamily) {
        return aircraft.stream()
                .filter(aircraftData -> aircraftData.getCallbackData().equals(aircraftFamily))
                .map(AircraftFamilyDto::getText)
                .findFirst()
                .orElseThrow(() -> new AircraftNotFoundException(String.format("Aircraft family %s not found", aircraftFamily)));
    }

    public String getUserAircraftList(List<AircraftEntity> aircraftEntities, List<AircraftFamilyDto> aircraft) {
        List<String> userAircraftNames = aircraftEntities.stream()
                .map(aircraftEntity -> getAircraftNameByCallback(aircraft, aircraftEntity.getFamily()))
                .toList();

        StringBuilder aircraftList = new StringBuilder();

        for (String userAircraftName : userAircraftNames) {
            aircraftList
                    .append("*")
                    .append(userAircraftName)
                    .append("*")
                    .append("\n");
        }

        return aircraftList.toString();
    }

    public List<String> getUserSelectedAircraftFamiliesCodes(List<AircraftEntity> aircraftEntities, List<Map<String, List<String>>> aircraftFamiliesCodes) {
        List<String> userAircraftFamiliesCodes = new ArrayList<>();
        List<AircraftFamily> aircraftFamilies = aircraftEntities.stream().map(AircraftEntity::getFamily).toList();

        for (Map<String, List<String>> aircraftFamiliesCode : aircraftFamiliesCodes) {
            for (String key : aircraftFamiliesCode.keySet()) {
                if (aircraftFamilies.contains(AircraftFamily.valueOf(key))) {
                    userAircraftFamiliesCodes.addAll(aircraftFamiliesCode.get(key));
                }
            }
        }

        return userAircraftFamiliesCodes;
    }
}
