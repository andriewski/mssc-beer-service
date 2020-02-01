package guru.springframework.msscbeerservice.web.controller;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyle;
import guru.springframework.msscbeerservice.web.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false,
                                                       defaultValue = "false") Boolean showInventoryOnHand) {
        return new ResponseEntity<>(beerService.getBeerById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("upc") String upc) {
        return new ResponseEntity<>(beerService.getBeerByUpc(upc), HttpStatus.OK);
    }

    @PostMapping("/beer")
    public ResponseEntity<BeerDto> saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("/beer/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDto beerDto) {
        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/beer/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeerById(@PathVariable("beerId") UUID beerId) {
        beerService.deleteBeerById(beerId);
    }

    @GetMapping(name = "/beer", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false,
                                                           defaultValue = "false") boolean showInventoryOnHand) {
        int defaultPageNumber = 0;
        int defaultPageSize = 25;
        pageNumber = (pageNumber == null || pageNumber < 0) ? defaultPageNumber : pageNumber;
        pageSize = (pageSize == null || pageSize < 0) ? defaultPageSize : pageSize;

        BeerPagedList beerPagedList = beerService.getAllBeer(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerPagedList, HttpStatus.OK);
    }
}
