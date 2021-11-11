package utils;

import com.google.gson.*;
import dtos.ValutaDTO;
//import dtos.ChuckDTO;
//import dtos.CombinedDTO;
//import dtos.DadDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class HttpUtils {
    private static Gson gson = new Gson();


    public static void main(String[] args) throws IOException {
        convertFromTo("USD","DKK");
    }
//    public static CombinedDTO fetchDataSequential() throws IOException {
//        String chuck = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
//        String dad = HttpUtils.fetchData("https://icanhazdadjoke.com");
//
//        ChuckDTO chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
//        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
//        return new CombinedDTO(chuckDTO,dadDTO);
//    }
//
//    public static CombinedDTO fetchDataParrallel() throws ExecutionException, InterruptedException, TimeoutException {
//        ExecutorService es = Executors.newCachedThreadPool();
//        Future<ChuckDTO> chuckDTOFuture = es.submit(
//                () -> gson.fromJson(HttpUtils.fetchData("https://api.chucknorris.io/jokes/random"),ChuckDTO.class)
//        );
//        Future<DadDTO> dadDTOFuture = es.submit(
//                () -> gson.fromJson(HttpUtils.fetchData("https://icanhazdadjoke.com"),DadDTO.class)
//        );
//
//        ChuckDTO chuckDTO = chuckDTOFuture.get();
//        DadDTO dadDTO = dadDTOFuture.get();
//
//        return new CombinedDTO(chuckDTO, dadDTO);
//    }

    public static List<ValutaDTO> getLatestRatesFromBase(String base)throws IOException{
        String params = "?base="+base;
        JsonObject result = HttpUtils.fetchJson("https://api.exchangerate.host/latest" +params);
        HashMap rates = gson.fromJson(result.get("rates"), HashMap.class);
        List<ValutaDTO> rateList = new ArrayList<>();

        rates.forEach((k,v) -> {
            ValutaDTO valutaDTO = new ValutaDTO();
            String key = k.toString();
            Double value = Double.parseDouble(v.toString());
            valutaDTO.setValue(value * 100);
            valutaDTO.setCode(key);
            rateList.add(valutaDTO);
        });
      return rateList;
    }

    public static Double convertFromTo(String from, String to) throws IOException {
        String params = "?from="+from+"&"+"to="+to;
        JsonObject result = HttpUtils.fetchJson("https://api.exchangerate.host/convert"+params);

       Double value = result.get("result").getAsDouble();
        System.out.println(value);
        return value;
    }


    public static ValutaDTO getSingleValutaValue(String code) throws IOException {
        JsonObject valuta = HttpUtils.fetchJson("https://api.exchangerate.host/latest?base=USD&amount=100&symbols="+code);
        HashMap val = gson.fromJson(valuta.get("rates"), HashMap.class);

        ValutaDTO valutaDTO = new ValutaDTO();

        val.forEach((k,v) -> {
            String key = k.toString();
            Double value = Double.parseDouble(v.toString());
            valutaDTO.setValue(value);
            valutaDTO.setCode(key);
        });
        return valutaDTO;
    }


    public static JsonObject fetchJson(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        return jsonobj;
    }

    public static String fetchData(String _url) throws MalformedURLException, IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        //con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }
}
