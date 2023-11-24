package bot.handler.command;

import bot.ButtonUtils;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class CommandHandler {
    public static void handle(final Message message, final TelegramLongPollingBot bot) {
        switch (CommandEnum.of(message.getText())) {
            case START -> handleCommandStart(message, bot);
            case HELP -> handleCommandHelp(message, bot);
        }
    }

    @SneakyThrows
    private static void handleCommandStart(final Message message, final TelegramLongPollingBot bot) {
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), "Hello");
        sendMessage.setReplyMarkup(ButtonUtils.START_MARKUP);
        bot.execute(sendMessage);
    }

    private static void handleCommandHelp(final Message message, final TelegramLongPollingBot bot) {

    }

}
