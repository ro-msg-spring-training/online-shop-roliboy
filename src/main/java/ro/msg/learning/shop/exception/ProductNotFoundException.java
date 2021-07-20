package ro.msg.learning.shop.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Integer id) {
        super(String.format("product with id '%d' does not exist", id));
    }
    public ProductNotFoundException(String name) {
        super(String.format("product '%s' not found", name));
    }
}
