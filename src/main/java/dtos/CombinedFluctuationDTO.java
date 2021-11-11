package dtos;

public class CombinedFluctuationDTO {
    Double start_rate1;
    Double end_rate1;
    Double change_pct1;
    Double start_rate2;
    Double end_rate2;
    Double change_pct2;

    public CombinedFluctuationDTO(FluctuationDTO uno, FluctuationDTO dos) {
        this.start_rate1 = uno.getStart_rate();
        this.end_rate1 = uno.getEnd_rate();
        this.change_pct1 = uno.getChange_pct();
        this.start_rate2 = dos.getStart_rate();
        this.end_rate2 = dos.getEnd_rate();
        this.change_pct2 = dos.getChange_pct();
    }

    public Double getStart_rate1() {
        return start_rate1;
    }

    public void setStart_rate1(Double start_rate1) {
        this.start_rate1 = start_rate1;
    }

    public Double getEnd_rate1() {
        return end_rate1;
    }

    public void setEnd_rate1(Double end_rate1) {
        this.end_rate1 = end_rate1;
    }

    public Double getChange_pct1() {
        return change_pct1;
    }

    public void setChange_pct1(Double change_pct1) {
        this.change_pct1 = change_pct1;
    }

    public Double getStart_rate2() {
        return start_rate2;
    }

    public void setStart_rate2(Double start_rate2) {
        this.start_rate2 = start_rate2;
    }

    public Double getEnd_rate2() {
        return end_rate2;
    }

    public void setEnd_rate2(Double end_rate2) {
        this.end_rate2 = end_rate2;
    }

    public Double getChange_pct2() {
        return change_pct2;
    }

    public void setChange_pct2(Double change_pct2) {
        this.change_pct2 = change_pct2;
    }
}