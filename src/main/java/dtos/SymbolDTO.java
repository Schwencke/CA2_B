package dtos;

import entities.Symbol;

public class SymbolDTO {
    String code;
    String description;

    public SymbolDTO(Symbol symbol) {
        this.code = symbol.getCode();
        this.description = symbol.getDescription();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
