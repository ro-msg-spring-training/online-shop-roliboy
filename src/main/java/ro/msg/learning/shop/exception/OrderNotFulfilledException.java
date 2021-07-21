package ro.msg.learning.shop.exception;

public class OrderNotFulfilledException extends RuntimeException {
    public OrderNotFulfilledException() {
        super("the order could not be fulfilled");
    }
}
