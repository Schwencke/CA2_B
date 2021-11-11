package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "symbol")
@Entity
public class Symbol {
    @Id
    @Column(name = "code", nullable = false, length = 5)
    private String code;
    private String description;

    public Symbol() {
    }

    public Symbol(String code, String description) {
        this.code = code;
        this.description = description;
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