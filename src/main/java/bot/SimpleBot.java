package bot;

import bot.handler.UpdateHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static bot.BotConfig.BOT_TOKEN;
import static bot.BotConfig.BOT_USERNAME;

public class SimpleBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        UpdateHandler.handle(update, this);
    }

    public SimpleBot() {
        super(BOT_TOKEN);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }
}
