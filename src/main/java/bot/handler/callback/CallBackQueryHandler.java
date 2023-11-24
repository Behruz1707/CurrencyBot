package bot.handler.callback;

import bot.Currency;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

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

        if (callbackQuery.getData().equals("dollar")) {
            List<Currency> ucDollar = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("US Dollar")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), ucDollar.get(0).getNbuCellPrice()));
        }

        else if (callbackQuery.getData().equals("euro")) {
            List<Currency> euro = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("EURO")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), euro.get(0).getNbuCellPrice()));
        }

        else if (callbackQuery.getData().equals("tenge")) {
            List<Currency> kazakhstanTenge = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("Kazakhstan Tenge")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), kazakhstanTenge.get(0).getNbuCellPrice()));
        }

        else if (callbackQuery.getData().equals("rouble")) {
            List<Currency> russianRouble = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("Russian Rouble")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), russianRouble.get(0).getNbuCellPrice()));
        }

        else if (callbackQuery.getData().equals("lira")) {
            List<Currency> newTurkishLira = currencies().stream()
                    .filter(currency -> currency.getTitle().equals("New Turkish Lira")).collect(Collectors.toList());
            bot.execute(new SendMessage(callbackQuery.getMessage().getChatId().toString(), newTurkishLira.get(0).getCode()));

        }
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
