package dtos;

public class CombinedValutaDTO {
    private String code1;
    private Double value1;
    private String code2;
    private Double value2;

    public CombinedValutaDTO(ValutaDTO valutaDTO1, ValutaDTO valutaDTO2) {
        this.code1 = valutaDTO1.getCode();
        this.value1 = valutaDTO1.getValue();
        this.code2 = valutaDTO2.getCode();
        this.value2 = valutaDTO2.getValue();
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public Double getValue1() {
        return value1;
    }

    public void setValue1(Double value1) {
        this.value1 = value1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public Double getValue2() {
        return value2;
    }

    public void setValue2(Double value2) {
        this.value2 = value2;
    }
}
