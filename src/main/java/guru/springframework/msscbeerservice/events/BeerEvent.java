package guru.springframework.msscbeerservice.events;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 5048586933405034620L;

    private final BeerDto beerDto;
}
