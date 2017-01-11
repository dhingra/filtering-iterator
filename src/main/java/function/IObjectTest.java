package function;

/**
 * @author rohitdhingra.
 */
@FunctionalInterface
public interface IObjectTest<T> {
    boolean test(final T o);
}
