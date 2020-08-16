package guru.springframework.msscbeerservice.services.clients.inventory;

import guru.springframework.msscbeerservice.services.clients.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Profile({"local-discovery", "digitalocean"})
@Component
@RequiredArgsConstructor
public class InventoryServiceFeignClientFailover implements InventoryServiceFeignClient {

    private final InventoryFailOverFeignClient failOverFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(UUID beerId) {
        return failOverFeignClient.getOnHandInventory();
    }
}
