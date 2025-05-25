package net.replaceitem.discarpet.script.schema;

public interface Redirector<T> {
    Class<? extends T> redirect();
}
