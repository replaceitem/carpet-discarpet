package Discarpet.script.parsable;

public interface Redirector<T> {
    Class<? extends T> redirect();
}
