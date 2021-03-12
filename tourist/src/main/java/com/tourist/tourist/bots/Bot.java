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
        City cityEntity = cityService.getByName(update.getMessage().getText());
        String chatId = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        try {
            if (cityEntity != null){
                execute(sendMessage.setChatId(chatId).setText(cityEntity.getDescription()));
            }else {

                execute(sendMessage.setChatId(chatId).setText("City not found! Sorry"));
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
