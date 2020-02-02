package guru.springframework.msscbeerservice.services.brewing;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateBeerOrderRequest;
import guru.sfg.brewery.model.events.ValidateBeerOrderResult;
import guru.springframework.msscbeerservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator beerOrderValidator;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    void validateOrder(@Payload ValidateBeerOrderRequest validateBeerOrderRequest) {
        BeerOrderDto beerOrderDto = validateBeerOrderRequest.getBeerOrderDto();
        boolean isValidBeerOrder = beerOrderValidator.validateOrder(beerOrderDto);

        ValidateBeerOrderResult message = new ValidateBeerOrderResult(beerOrderDto != null ? beerOrderDto.getId() : null, isValidBeerOrder);
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, message);
    }
}
