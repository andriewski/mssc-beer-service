package guru.springframework.msscbeerservice.mappers;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.services.clients.inventory.BeerInventoryService;
import guru.sfg.brewery.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerMapper delegate;
    private BeerInventoryService beerInventoryService;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setDelegate(BeerMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        return delegate.beerToBeerDto(beer);
    }

    @Override
    public BeerDto beerToBeerDtoWithoutInventory(Beer beer) {
        BeerDto beerDto = delegate.beerToBeerDto(beer);
        beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));

        return beerDto;
    }


    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return delegate.beerDtoToBeer(beerDto);
    }
}
