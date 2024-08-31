package ir.mhkapr.webtaxi.service.priceClasses;


public class Lux_Car_PriceCalculator implements CalculatorPrice{
    @Override
    public Double calculatePrice(Double distance) {
        return Math.floor(distance * 2500);
    }
}
