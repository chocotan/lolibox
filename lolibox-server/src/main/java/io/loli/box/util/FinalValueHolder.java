package io.loli.box.util;

/**
 * @author choco
 */
public class FinalValueHolder<T> {
    private T value;

    public FinalValueHolder(T t) {
        this.value = t;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
