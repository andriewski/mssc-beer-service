package guru.springframework.msscbeerservice.web.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by jt on 2019-04-20.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    @Override
    public Beer getBeerById(UUID beerId) {
        return beerRepository.findById(beerId).orElse(null);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        beerRepository.findById(beerId).ifPresent(beer -> {
            beer.setBeerName(beerDto.getBeerName());
            beer.setBeerStyle(beerDto.getBeerStyle());
            beer.setUpc(beerDto.getUpc());
            beer.setPrice(beerDto.getPrice());

            beerRepository.save(beer);
        });
    }

    @Override
    public void deleteBeerById(UUID beerId) {
        log.debug("Deleting a beer with id {}", beerId);
        Beer beer = beerRepository.findById(beerId).orElse(null);
        beerRepository.delete(beer);
    }
}
