package ro.msg.learning.shop.exception;

public class SupplierNotFoundException extends RuntimeException {
    public SupplierNotFoundException(Integer id) {
        super(String.format("supplier with id '%d' does not exist", id));
    }
    public SupplierNotFoundException(String name) {
        super(String.format("supplier '%s' not found", name));
    }
}
