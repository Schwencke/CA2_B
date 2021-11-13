package utils;

import com.google.gson.*;
import dtos.CombinedFluctuationDTO;
import dtos.FluctuationDTO;
import dtos.SymbolsDTO;
import dtos.ValutaDTO;
import entities.Symbol;
import facades.ValutaFacade;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javax.persistence.EntityManagerFactory;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUtils {
    private static Gson gson = new Gson();

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final ValutaFacade VALUTA_FACADE = ValutaFacade.getValutaFacade(EMF);


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

    public static void iconScraber() throws Exception {
        List<String> codeList = new ArrayList<>();
        SymbolsDTO fromDB = VALUTA_FACADE.getAllSymbolsFromDB();
        HashMap<String,Elements> svgMap = new HashMap<>();

        fromDB.getAll().forEach(dtos -> codeList.add(dtos.getCode()));

        codeList.forEach(codes -> {
            String url = "https://www.valutakurser.dk/images/flags/"+codes+".svg";
            try {
                Document doc = Jsoup.connect(url).ignoreContentType(true).get();
                Elements svg = doc.select("svg");
                System.out.println("Scraped svg for " + codes);
                svgMap.put(codes,svg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VALUTA_FACADE.persistFlags(svgMap);

    }


    public static CombinedFluctuationDTO parralellFetch(String valuta1, String valuta2, String from, String to) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();
        Future<FluctuationDTO> one = es.submit(
                () -> getFluctuationRates(from,to,valuta1)
        );
        Future<FluctuationDTO> two = es.submit(
                () -> getFluctuationRates(from,to,valuta2)
        );
        FluctuationDTO uno = one.get();
        FluctuationDTO dos = two.get();

        return new CombinedFluctuationDTO(uno,dos);
    }

    public static FluctuationDTO getFluctuationRates(String from, String to, String valuta)throws IOException{
        String params = "?start_date="+from+"&"+"end_date="+to+"&"+"symbols="+valuta+"&"+"places="+4;
        JsonObject result = HttpUtils.fetchJson("https://api.exchangerate.host/fluctuation" +params);
        HashMap val = gson.fromJson(result.get("rates"), HashMap.class);

        FluctuationDTO dto = gson.fromJson(String.valueOf(val.get(valuta)),FluctuationDTO.class);

        return dto;
    }

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

    public static Double convertFromTo(String from, String to, Double amount) throws IOException {
        String params = "?from="+from+"&"+"to="+to+"&"+"amount="+amount;
        JsonObject result = HttpUtils.fetchJson("https://api.exchangerate.host/convert"+params);
        return result.get("result").getAsDouble();
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
