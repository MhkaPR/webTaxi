package ir.mhkapr.webtaxi.service.priceClasses;

public class TrackPriceCalculator implements CalculatorPrice{
    @Override
    public Double calculatePrice(Double distance) {
        return distance*2000 + 5000;
    }
}
