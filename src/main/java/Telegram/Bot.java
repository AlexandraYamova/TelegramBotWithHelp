package Telegram;

import Telegram.Commands.HelpCommand;
import Telegram.Commands.StartCommand;
import Telegram.NonCommand.NonCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public final class Bot extends TelegramLongPollingCommandBot {
    //Токен бота
    private final String BOT_TOKEN;
    //Имя бота
    private final String BOT_NAME;
    //Класс для обработки сообщений, не являющихся коммандой
    private final NonCommand nonCommand;

    @Override
    public String getBotUsername(){
        return BOT_NAME;
    }

    @Override
    public String getBotToken(){
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update){
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);

        String answer = nonCommand.nonCommandExecute();
        setAnswer(chatId, userName, answer);
    }

//    private static final Settings defaultSettings = new Settings();
    public Bot(String botName, String botToken){
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        //Вспомогательный класс для работы со строками,не являющимися командами
        this.nonCommand = new NonCommand();
        //Регестрируемые команды
        register(new StartCommand("/start", "Старт"));
        register(new HelpCommand("/help", "Помощь"));
    }

    //Формирование имени пользователя
    private String getUserName(Message msg){
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    //Формирование ответа
    private void setAnswer(Long chatId, String userName, String text){
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try{
            execute(answer);
        } catch (TelegramApiException e){
            System.out.println("Smth went wrong to send message to "+ userName);
        }
    }
}

