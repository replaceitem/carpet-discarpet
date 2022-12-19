package net.replaceitem.discarpet.script.parsable;

public interface Redirector<T> {
    Class<? extends T> redirect();
}
