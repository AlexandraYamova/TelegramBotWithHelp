package Telegram.Commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class ServiceCommand extends BotCommand {

    //Суперкласс для сервисных команд
    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    //Отправка ответа пользователю
    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text){
        SendMessage msg = new SendMessage();
        msg.enableMarkdown(true);
        msg.setChatId(chatId.toString());
        msg.setText(text);
        try {
            absSender.execute(msg);
        }
        catch (TelegramApiException e){
            System.out.println("Response to command "+commandName+" for user "+userName+" not delivered due to telegram failure");
        }
    }
}
