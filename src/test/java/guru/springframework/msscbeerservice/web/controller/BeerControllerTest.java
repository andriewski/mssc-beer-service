package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.mappers.BeerMapper;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyle;
import guru.springframework.msscbeerservice.web.services.BeerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = {
        "guru.springframework.msscbeerservice"
})
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @MockBean
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void getBeerById() throws Exception {
        Beer beer = beerMapper.beerDtoToBeer(getValidBeerDto());
        beer.setCreatedDate(new Timestamp(new Date().getTime()));
        beer.setLastModifiedDate(new Timestamp(new Date().getTime()));
        beer.setVersion(1);
        beer.setQuantityToBrew(100);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        given(beerService.getBeerById(any())).willReturn(beer);

        mockMvc.perform(get("/api/v1/beer/{beerId}", beer.getId())
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get")
                        ),
                        responseFields(
                                fields.withPath("id").description("Id of Beer"),
                                fields.withPath("version").description("Beer version"),
                                fields.withPath("createdDate").description("The Date when beer was created"),
                                fields.withPath("lastModifiedDate").description("The last day when beer was updated"),
                                fields.withPath("beerName").description("Name of Beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Upc of Beer"),
                                fields.withPath("price").description("Price of beer"),
                                fields.withPath("quantityOnHand").description("Quantity on hand")
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        beerDto.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of Beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Upc of Beer"),
                                fields.withPath("price").description("Price of beer"),
                                fields.withPath("quantityOnHand").ignored()
                        )
                ));
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        UUID id = beerDto.getId();
        beerDto.setId(null);
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(put("/api/v1/beer/{beerId}", id)
                .contentType(APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer-update",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to update")
                        ),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of Beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("Upc of Beer"),
                                fields.withPath("price").description("Price of beer"),
                                fields.withPath("quantityOnHand").ignored()
                        )
                ));
    }

    @Test
    void deleteBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDto();

        mockMvc.perform(delete("/api/v1/beer/{beerId}", beerDto.getId()))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer-delete",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to delete")
                        )
                ));
    }

    private BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("My beer")
                .beerStyle(BeerStyle.ALE)
                .price(new BigDecimal("1.01"))
                .upc(123123123123L)
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        private ConstrainedFields(Class<?> clazz) {
            this.constraintDescriptions = new ConstraintDescriptions(clazz);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }
}