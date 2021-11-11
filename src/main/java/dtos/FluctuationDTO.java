package dtos;

public class FluctuationDTO {
    Double start_rate;
    Double end_rate;
    Double change_pct;

    public FluctuationDTO(Double start_rate, Double end_rate, Double change_pct) {
        this.start_rate = start_rate;
        this.end_rate = end_rate;
        this.change_pct = change_pct;
    }

    public Double getStart_rate() {
        return start_rate;
    }

    public void setStart_rate(Double start_rate) {
        this.start_rate = start_rate;
    }

    public Double getEnd_rate() {
        return end_rate;
    }

    public void setEnd_rate(Double end_rate) {
        this.end_rate = end_rate;
    }

    public Double getChange_pct() {
        return change_pct;
    }

    public void setChange_pct(Double change_pct) {
        this.change_pct = change_pct;
    }
}
