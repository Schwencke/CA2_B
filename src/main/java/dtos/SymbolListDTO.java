package dtos;

import entities.Symbol;

import java.util.List;

public class SymbolListDTO {
    List<List<Symbol>> symbols;

    public SymbolListDTO(List<List<Symbol>> symbols) {
        this.symbols = symbols;
    }

    public List<List<Symbol>> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<List<Symbol>> symbols) {
        this.symbols = symbols;
    }
}
