package io.loli.box.exception;

/**
 * @author choco
 */
public class UserExistsException extends Exception {
    public UserExistsException() {
        super();
    }

    public UserExistsException(String reason) {
        super(reason);
    }
}
