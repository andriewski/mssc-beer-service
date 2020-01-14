package guru.springframework.msscbeerservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.UUID;

//@Component класс просто для примера и содержания static полей
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a7124914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");


//    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) {
        //loadBeerObjects();
    }

/*    private void loadBeerObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(BEER_1_UPC)
                .price(new BigDecimal("12.95"))
                .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .quantityToBrew(250)
                    .minOnHand(13)
                    .upc(BEER_2_UPC)
                    .price(new BigDecimal("11.95"))
                    .build());


            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Dog")
                    .beerStyle(BeerStyle.IPA)
                    .quantityToBrew(350)
                    .minOnHand(14)
                    .upc(BEER_3_UPC)
                    .price(new BigDecimal("5.95"))
                    .build());
        }
    }*/
}
