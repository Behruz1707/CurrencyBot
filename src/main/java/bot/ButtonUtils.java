package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class ButtonUtils {

    public static final InlineKeyboardMarkup START_MARKUP = InlineKeyboardMarkup.builder()
            .keyboardRow(List.of(
                    InlineKeyboardButton.builder().text("US Dollar").callbackData("dollar").build(),
                    InlineKeyboardButton.builder().text("Euro").callbackData("euro").build()
            ))
            .keyboardRow(List.of(
                    InlineKeyboardButton.builder().text("Tenge").callbackData("tenge").build(),
                    InlineKeyboardButton.builder().text("Rouble").callbackData("rouble").build()
            ))
            .keyboardRow(List.of(
                    InlineKeyboardButton.builder().text("➡").callbackData("next").build()
            )).build();
}
