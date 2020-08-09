package guru.springframework.msscbeerservice.services.clients.inventory;

import guru.springframework.msscbeerservice.config.FeignClientConfig;
import guru.springframework.msscbeerservice.services.clients.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(
        name = "inventory-service",
        fallback = InventoryServiceFeignClientFailover.class,
        configuration = FeignClientConfig.class
)
public interface InventoryServiceFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = BeerInventoryServiceRestTemplateImpl.INVENTORY_PATH,
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(@PathVariable UUID beerId);
}
