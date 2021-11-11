package dtos;

import entities.Symbol;

import java.util.ArrayList;
import java.util.List;

public class SymbolsDTO {

    private List<SymbolDTO> all = new ArrayList<>();


    public SymbolsDTO(List<Symbol> symbolEntities) {

        symbolEntities.forEach((p) -> {
            all.add(new SymbolDTO(p));
        });
    }

    public List<SymbolDTO> getAll() {
        return all;
    }

}
