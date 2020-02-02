package guru.springframework.msscbeerservice.services.brewing;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.BeerOrderLineDto;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public boolean validateOrder(BeerOrderDto beerOrderDto) {
        boolean isValidBeerOrder = false;

        if (beerOrderDto != null) {
            List<BeerOrderLineDto> beerOrderLineDto = beerOrderDto.getBeerOrderLines();

            if (beerOrderLineDto != null && beerOrderLineDto.size() > 0) {
                Optional<BeerOrderLineDto> nonValidBeerOrder = beerOrderLineDto.stream()
                        .filter(beerOrderLine -> beerRepository.findByUpc(beerOrderLine.getUpc()) == null)
                        .findAny();

                isValidBeerOrder = nonValidBeerOrder.isEmpty();
            }
        }

        return isValidBeerOrder;
    }
}
