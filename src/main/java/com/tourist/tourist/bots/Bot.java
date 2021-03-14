package com.tourist.tourist.bots;

import com.tourist.tourist.entity.City;
import com.tourist.tourist.service.CityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {
    private final CityService cityService;
    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public Bot(CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String inputText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();

        try {
            if (inputText.startsWith("/start")) {
                sendMessage.setText("Hello.\nEnter the name of the city and I'll tell you where can to go there.");
            }
            execute(sendMessage.setChatId(chatId).setText(getDescriptionOfCity(inputText)));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private String getDescriptionOfCity(String city) {
        String message = "City not found! Sorry";
        City cityEntity = cityService.getByName(city);
        if (cityEntity != null) {
            message = cityEntity.getDescription();
        }
        return message;
    }
}
