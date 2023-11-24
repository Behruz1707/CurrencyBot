package org.example;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Main {
    public static void main( String[] args ) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI("https://cbu.uz/uz/arkhiv-kursov-valyut/veb-masteram/"))
                .GET()
                .build();


        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonObject = """
                    {
                    "id": 69,
                    "Code": "840",
                    "Ccy": "USD",
                    "CcyNm_RU": "Доллар США",
                    "CcyNm_UZ": "AQSH dollari",
                    "CcyNm_UZC": "АҚШ доллари",
                    "CcyNm_EN": "US Dollar",
                    "Nominal": "1",
                    "Rate": "12270.07",
                    "Diff": "-7.35",
                    "Date": "22.11.2023"
                    }
                """;
        String jsonArray = """
                    [
                    {
                        "id": 69,
                        "Code": "840",
                        "Ccy": "USD",
                        "CcyNm_RU": "Доллар США",
                        "CcyNm_UZ": "AQSH dollari",
                        "CcyNm_UZC": "АҚШ доллари",
                        "CcyNm_EN": "US Dollar",
                        "Nominal": "1",
                        "Rate": "12270.07",
                        "Diff": "-7.35",
                        "Date": "22.11.2023"
                      },
                      {
                        "id": 21,
                        "Code": "978",
                        "Ccy": "EUR",
                        "CcyNm_RU": "Евро",
                        "CcyNm_UZ": "EVRO",
                        "CcyNm_UZC": "EВРО",
                        "CcyNm_EN": "Euro",
                        "Nominal": "1",
                        "Rate": "13428.36",
                        "Diff": "0.55",
                        "Date": "22.11.2023"
                    }
                    ]
                """;

//        Currency currency = gson.fromJson(jsonObject, Currency.class);
//        System.out.println(currency);

        Type type = TypeToken.getParameterized(List.class, Currency.class).getType();
        List<Currency> list = gson.fromJson(jsonArray, type);
        list.forEach(System.out::println);

    }
}
