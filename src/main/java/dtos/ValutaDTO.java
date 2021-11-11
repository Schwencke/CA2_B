package dtos;

import java.util.HashMap;

public class ValutaDTO {

    String date;
    HashMap<String, Double> rates;

    public ValutaDTO(String date, HashMap<String, Double> rates) {
        this.date = date;
        this.rates = rates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }
}
