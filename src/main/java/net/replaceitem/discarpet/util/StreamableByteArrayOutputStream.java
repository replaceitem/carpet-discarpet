package net.replaceitem.discarpet.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * Used to avoid having to copy all bytes from the output stream with {@link #toByteArray()}.
 */
public class StreamableByteArrayOutputStream extends ByteArrayOutputStream {
    /**
     * Creates an input stream of the bytes stored in this output stream.
     * The bytes are not copied and the returned stream is only a view on the bytes stored here.
     */
    public ByteArrayInputStream createInputStream() {
        return new ByteArrayInputStream(this.buf, 0, this.count);
    }
}
