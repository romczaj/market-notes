package pl.romczaj.marketnotes.useraccount.common.price;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.romczaj.marketnotes.useraccount.common.price.StockOperation.*;

class StockOperationTest {


    static Stream<Arguments> breakBreakCheckOperationPricePriceArguments() {
        return Stream.of(
                Arguments.of(BUY_STOP, 22.0, 22.1, 20.0, true),
                Arguments.of(BUY_STOP, 22.0, 21.0, 20.0, false),
                Arguments.of(SELL_STOP, 18.0, 17.9, 20.0, true),
                Arguments.of(SELL_STOP, 18.0, 18.5, 20.0, false),
                Arguments.of(BUY_LIMIT, 18.0, 17.0, 20.0, true),
                Arguments.of(BUY_LIMIT, 18.0, 18.5, 20.0, false),
                Arguments.of(SELL_LIMIT, 22.0, 22.1, 20.0, true),
                Arguments.of(SELL_LIMIT, 22.0, 18.9, 20.0, false)
        );
    }

    @ParameterizedTest
    @MethodSource("breakBreakCheckOperationPricePriceArguments")
    void shouldCorrectCountBreakBreakCheckOperationPricePricePrice(
            StockOperation stockOperation, Double priceValue,
            Double todayPrice, Double yesterdayPrice, boolean expected) {

        //when
        boolean result = stockOperation.achieveBreakPrice(priceValue, todayPrice, yesterdayPrice);

        //then
        assertEquals(expected, result);
    }

    static Stream<Arguments> underCheckOperationPricePriceArguments() {
        return Stream.of(
                Arguments.of(BUY_STOP, 22.0, 22.1, true),
                Arguments.of(BUY_STOP, 22.0, 21.0, false),
                Arguments.of(SELL_STOP, 18.0, 17.9, true),
                Arguments.of(SELL_STOP, 18.0, 18.5, false),
                Arguments.of(BUY_LIMIT, 18.0, 17.0, true),
                Arguments.of(BUY_LIMIT, 18.0, 18.5, false),
                Arguments.of(SELL_LIMIT, 22.0, 22.1, true),
                Arguments.of(SELL_LIMIT, 22.0, 18.9, false)
        );
    }

    @ParameterizedTest
    @MethodSource("underCheckOperationPricePriceArguments")
    void shouldCorrect(StockOperation stockOperation, Double pointerPrice, Double todayPrice, boolean expected) {

        //when
        BiPredicate<Double, Double> doubleDoubleBiPredicate = stockOperation.underAchievedPrice();
        boolean result = doubleDoubleBiPredicate.test(pointerPrice, todayPrice);

        //then
        assertEquals(expected, result);
    }


    @Test
    @DisplayName("Should return only fulfillment of the condition ArchivePrice and be prove for nulls")
    void shouldReturnCorrectCheckOperationPrice() {
        //given
        ArchivePriceCommand archivePriceCommand = new ArchivePriceCommand(
                null, null, 10.0, 10.0, null, 11.0);

        //when
        List<StockOperation> aa = listUnder(archivePriceCommand);

        //then
        Assertions.assertEquals(List.of(SELL_LIMIT), aa);
    }

}