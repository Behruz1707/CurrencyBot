package bot.handler.callback;

import bot.ButtonUtils;
import bot.Currency;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class CallBackQueryHandler {
    @SneakyThrows
    public static void handle(final CallbackQuery callbackQuery, final TelegramLongPollingBot bot) {
        String data = callbackQuery.getData();

        if (data.equals("next")) {
            handleNext(callbackQuery, bot);
        } else if (data.equals("back")) {
            EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                    .messageId(callbackQuery.getMessage().getMessageId())
                    .chatId(callbackQuery.getMessage().getChatId())
                    .replyMarkup(ButtonUtils.START_MARKUP)
                    .build();
            bot.execute(editMessageReplyMarkup);
        } else if (data.equals("dollar")) {
            List<Currency> ucDollar = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("US Dollar")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "1 US Dollar - " + ucDollar.get(0).getNbuCellPrice() + " UZS"));
        } else if (data.equals("euro")) {
            List<Currency> euro = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("EURO")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "1 Euro - " + euro.get(0).getNbuCellPrice() + " UZS"));
        } else if (data.equals("tenge")) {
            List<Currency> kazakhstanTenge = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("Kazakhstan Tenge")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "1 Tenge - " + kazakhstanTenge.get(0).getNbuCellPrice() + " UZS"));
        } else if (data.equals("rouble")) {
            List<Currency> russianRouble = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("Russian Rouble")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "1 Rouble - " + russianRouble.get(0).getNbuCellPrice() + " UZS"));
        } else if (data.equals("dirham")) {
            List<Currency> uaeDirham = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("UAE Dirham")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "1 Dirham - " + uaeDirham.get(0).getCbPrice() + " UZS"));
        } else if (data.equals("yuan")) {
            List<Currency> chineseYuan = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("Chinese Yuan")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "1 Yuan - " + chineseYuan.get(0).getCbPrice() + " UZS"));
        } else if (data.equals("won")) {
            List<Currency> southKoreanWon = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("South Korean Won")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), "1 Won - " + southKoreanWon.get(0).getCbPrice() + " UZS"));
        }

        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        Integer messageId = callbackQuery.getMessage().getMessageId();
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
        try {
            bot.execute(deleteMessage);
        }catch(TelegramApiException tae) {
            throw new RuntimeException(tae);
        }

    }

    @SneakyThrows
    private static void handleNext(final CallbackQuery callbackQuery, final TelegramLongPollingBot bot) {
        InlineKeyboardMarkup build = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder().text("UAE Dirham").callbackData("dirham").build(),
                        InlineKeyboardButton.builder().text("Chinese Yuan").callbackData("yuan").build()
                ))
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder().text("South Korean Won").callbackData("won").build()
                ))
                .keyboardRow(List.of(
                        InlineKeyboardButton.builder().text("⬅").callbackData("back").build()
                )).build();

        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .messageId(callbackQuery.getMessage().getMessageId())
                .chatId(callbackQuery.getMessage().getChatId())
                .replyMarkup(build)
                .build();

        bot.execute(editMessageReplyMarkup);
    }

    public static List<Currency> currencies() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://nbu.uz/en/exchange-rates/json/"))
                .GET()
                .build();
        HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());

        Type type = new TypeToken<List<Currency>>() {
        }.getType();

        return new GsonBuilder().create().fromJson(send.body(), type);
    }
}
