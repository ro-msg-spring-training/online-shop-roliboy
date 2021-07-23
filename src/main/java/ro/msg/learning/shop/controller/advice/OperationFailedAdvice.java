package ro.msg.learning.shop.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ro.msg.learning.shop.exception.OrderNotFulfilledException;

@ControllerAdvice
public class OperationFailedAdvice {
    @ResponseBody
    @ExceptionHandler(OrderNotFulfilledException.class)
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    String orderNotFulfilled(OrderNotFulfilledException exception) {
        return exception.getMessage();
    }
}
