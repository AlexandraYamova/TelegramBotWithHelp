package Telegram.NonCommand;

//Обработка сообщений, не считающихся коммандой (не начинающизся с "/")


public class NonCommand {

    public String nonCommandExecute(){
        String answer;
        answer = "Ваше сообщение не распознано, возможно вам поможет команда /help";
        return answer;
    }
}
