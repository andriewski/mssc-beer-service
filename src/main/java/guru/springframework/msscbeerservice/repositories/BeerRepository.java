package guru.springframework.msscbeerservice.repositories;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.sfg.brewery.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageRequest);

    Page<Beer> findAllByBeerName(String beerName, Pageable pageRequest);

    Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageRequest);

    Beer findByUpc(String upc);
}
