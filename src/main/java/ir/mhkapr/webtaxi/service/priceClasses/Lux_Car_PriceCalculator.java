package ir.mhkapr.webtaxi.service.priceClasses;


import org.springframework.stereotype.Component;

@Component("LUX_CAR")
public class Lux_Car_PriceCalculator implements CalculatorPrice{
    @Override
    public Double calculatePrice(Double distance) {
        return Math.floor(distance * 2500);
    }
}
