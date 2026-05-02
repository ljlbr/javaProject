package by.psu.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Excursion extends TourService {

    private String guideName;
    private String excursionType;
    private boolean lunchIncluded;

    public Excursion() {
        super();
    }

    public Excursion(Integer id, String name, BigDecimal price, LocalDate from, LocalDate to,
                     String guideName, String excursionType, boolean lunchIncluded) {
        super(id, name, price, from, to);
        this.guideName = guideName;
        this.excursionType = excursionType;
        this.lunchIncluded = lunchIncluded;
    }

    @Override
    public BigDecimal calculateTotalPrice(int participants) {
        BigDecimal base = getPrice().multiply(BigDecimal.valueOf(participants));
        return participants > 10
                ? base.subtract(base.divide(BigDecimal.valueOf(10), MathContext.DECIMAL128))
                : base;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getExcursionType() {
        return excursionType;
    }

    public void setExcursionType(String excursionType) {
        this.excursionType = excursionType;
    }

    public boolean isLunchIncluded() {
        return lunchIncluded;
    }

    public void setLunchIncluded(boolean lunchIncluded) {
        this.lunchIncluded = lunchIncluded;
    }

    @Override
    public String toString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DecimalFormat money = new DecimalFormat("#0.00");

        return "Excursion{" +
                "id=" + getId() +
                ", name=\"" + getName() + "\"" +
                ", price=" + (getPrice() != null ? money.format(getPrice()) : "null") +
                ", from=" + (getFrom() != null ? df.format(getFrom()) : "null") +
                ", to=" + (getTo() != null ? df.format(getTo()) : "null") +
                ", guideName=\"" + guideName + "\"" +
                ", excursionType=\"" + excursionType + "\"" +
                ", lunchIncluded=" + lunchIncluded +
                "}";
    }
}