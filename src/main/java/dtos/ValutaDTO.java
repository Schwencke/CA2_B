package dtos;

public class ValutaDTO {

    String code;
    Double value; //by default the GET return values based on a 100USD

    public ValutaDTO(String code, Double value) {
        this.code = code;
        this.value = value;
    }

    public ValutaDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
