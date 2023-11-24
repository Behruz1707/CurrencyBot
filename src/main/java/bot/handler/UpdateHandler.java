package bot.handler;

import bot.handler.callback.CallBackQueryHandler;
import bot.handler.message.MessageHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateHandler {
    public static void handle(final Update update, final TelegramLongPollingBot bot) {
        if (update.hasCallbackQuery()) {
            CallBackQueryHandler.handle(update.getCallbackQuery(), bot);
        } else if (update.hasMessage()) {
            MessageHandler.handle(update.getMessage(), bot);
        }

    }
}
