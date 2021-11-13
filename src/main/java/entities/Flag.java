package entities;

import javax.persistence.*;
import java.sql.Blob;

@Table(name = "flag")
@Entity
public class Flag {
    @Id
    @Column(name = "code", nullable = false, length = 1500)
    private String code;

    @Lob @Basic
    @Column(name="svg", length = 1500)
    private String svg;

    public Flag() {
    }

    public Flag(String code, String svg) {
        this.code = code;
        this.svg = svg;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSvg() {
        return svg;
    }

    public void setSvg(String svg) {
        this.svg = svg;
    }
}