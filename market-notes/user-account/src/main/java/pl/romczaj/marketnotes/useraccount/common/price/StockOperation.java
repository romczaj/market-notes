package pl.romczaj.marketnotes.useraccount.common.price;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static pl.romczaj.marketnotes.useraccount.common.price.OperationPointerType.BREAK;
import static pl.romczaj.marketnotes.useraccount.common.price.OperationPointerType.UNDER;

public enum StockOperation {
    BUY_STOP() {
        @Override
        boolean achieveBreakPrice(Double pointerPrice, Double todayPrice, Double yesterdayPrice) {
            return yesterdayPrice < pointerPrice && pointerPrice < todayPrice;
        }

        @Override
        BiPredicate<Double, Double> underAchievedPrice() {
            return (pointerPrice, actualPrice) -> pointerPrice < actualPrice;
        }

        @Override
        Function<ArchivePriceCommand, Double> pointerValue() {
            return ArchivePriceCommand::buyStopPrice;
        }
    },
    SELL_STOP {
        @Override
        boolean achieveBreakPrice(Double pointerPrice, Double todayPrice, Double yesterdayPrice) {
            return yesterdayPrice > pointerPrice && pointerPrice > todayPrice;
        }

        @Override
        BiPredicate<Double, Double> underAchievedPrice() {
            return (pointerPrice, actualPrice) -> pointerPrice > actualPrice;
        }

        @Override
        Function<ArchivePriceCommand, Double> pointerValue() {
            return ArchivePriceCommand::sellStopPrice;
        }

    },
    BUY_LIMIT {
        @Override
        boolean achieveBreakPrice(Double pointerPrice, Double todayPrice, Double yesterdayPrice) {
            return yesterdayPrice > pointerPrice && pointerPrice > todayPrice;
        }

        @Override
        BiPredicate<Double, Double> underAchievedPrice() {
            return (pointerPrice, actualPrice) -> pointerPrice > actualPrice;
        }

        @Override
        Function<ArchivePriceCommand, Double> pointerValue() {
            return ArchivePriceCommand::buyLimitPrice;
        }


    },
    SELL_LIMIT {
        @Override
        boolean achieveBreakPrice(Double pointerPrice, Double todayPrice, Double yesterdayPrice) {
            return yesterdayPrice < pointerPrice && pointerPrice < todayPrice;
        }

        @Override
        BiPredicate<Double, Double> underAchievedPrice() {
            return (pointerPrice, actualPrice) -> pointerPrice < actualPrice;
        }

        @Override
        Function<ArchivePriceCommand, Double> pointerValue() {
            return ArchivePriceCommand::sellLimitPrice;
        }
    };

    abstract boolean achieveBreakPrice(Double pointerPrice, Double todayPrice, Double yesterdayPrice);

    abstract BiPredicate<Double, Double> underAchievedPrice();

    abstract Function<ArchivePriceCommand, Double> pointerValue();

    public static List<StockOperation> listBreak(ArchivePriceCommand command) {
        if (command.todayPrice() == null || command.yesterdayPrice() == null) {
            return List.of();
        }
        return collect(command, BREAK);
    }

    public static List<StockOperation> listUnder(ArchivePriceCommand command) {
        if (command.todayPrice() == null) {
            return List.of();
        }
        return collect(command, UNDER);
    }


    private static List<StockOperation> collect(ArchivePriceCommand command, OperationPointerType operationPointerType) {
        return Arrays.stream(StockOperation.values())
                .map(ap -> checkOperation(command, ap, operationPointerType))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static Optional<StockOperation> checkOperation(ArchivePriceCommand command, StockOperation stockOperation,
                                                           OperationPointerType operationPointerType) {

        boolean result = switch (operationPointerType) {
            case UNDER -> checkUnder(command, stockOperation);
            case BREAK -> checkBreak(command, stockOperation);
        };

        return result ? Optional.of(stockOperation) : Optional.empty();
    }

    private static boolean checkUnder(ArchivePriceCommand command, StockOperation stockOperation) {
        return Optional.ofNullable(stockOperation.pointerValue().apply(command))
                .filter(p -> stockOperation.underAchievedPrice().test(p, command.todayPrice()))
                .isPresent();
    }

    private static boolean checkBreak(ArchivePriceCommand command, StockOperation stockOperation) {
        return Optional.ofNullable(stockOperation.pointerValue().apply(command))
                .filter(p -> stockOperation.achieveBreakPrice(p, command.todayPrice(), command.yesterdayPrice()))
                .isPresent();
    }
}
