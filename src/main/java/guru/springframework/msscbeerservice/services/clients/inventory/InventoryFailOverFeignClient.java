package guru.springframework.msscbeerservice.services.clients.inventory;

import guru.springframework.msscbeerservice.services.clients.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "inventory-failover-service")
public interface InventoryFailOverFeignClient {

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/inventory-failover",
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();
}
