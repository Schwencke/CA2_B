package dtos;

import entities.Symbol;

import java.util.HashMap;
import java.util.List;

public class ValutaSymbolDTO {
   String code;
   Symbol symbol;

    public ValutaSymbolDTO(String code, Symbol symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
