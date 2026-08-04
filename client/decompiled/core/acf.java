/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.EOFException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.apache.log4j.Logger;

public class acf {
    public static final ByteOrder cjm = ByteOrder.LITTLE_ENDIAN;
    private static final Logger a = Logger.getLogger(acf.class);
    private final ByteBuffer apS;
    private int cjn = -1;
    private byte cjo = (byte)-1;
    private byte cjp = 0;
    private static final ByteBuffer cjq = ByteBuffer.allocate(0);

    protected acf(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("ByteBuffer can't be null");
        }
        this.apS = byteBuffer;
        this.apS.order(cjm);
    }

    public acf(InputStream inputStream) {
        this(acf.o(inputStream));
    }

    public static acf H(ByteBuffer byteBuffer) {
        return new acf(byteBuffer);
    }

    public static acf a(ByteBuffer byteBuffer, ByteOrder byteOrder) {
        byteBuffer.order(byteOrder);
        return new acf(byteBuffer);
    }

    public static acf n(InputStream inputStream) {
        ByteBuffer byteBuffer = acf.o(inputStream);
        return new acf(byteBuffer);
    }

    public static acf a(InputStream inputStream, ByteOrder byteOrder) {
        ByteBuffer byteBuffer = acf.o(inputStream);
        byteBuffer.order(byteOrder);
        return new acf(byteBuffer);
    }

    public static acf T(byte[] byArray) {
        return new acf(ByteBuffer.wrap(byArray));
    }

    public static acf a(byte[] byArray, ByteOrder byteOrder) {
        return new acf(ByteBuffer.wrap(byArray).order(byteOrder));
    }

    protected static ByteBuffer o(InputStream inputStream) {
        byte[] byArray = null;
        while (inputStream.available() != 0) {
            int n2;
            int n3 = inputStream.available();
            byte[] byArray2 = new byte[n3];
            for (int j = 0; j != n3; j += n2) {
                n2 = inputStream.read(byArray2, j, n3 - j);
                if (n2 != -1) continue;
                throw new EOFException("Less data than assumed in the stream. " + n3 + " expected, " + j + " read");
            }
            if (byArray == null) {
                byArray = byArray2;
                continue;
            }
            byte[] byArray3 = new byte[byArray.length + byArray2.length];
            System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
            System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
            byArray = byArray3;
        }
        if (byArray != null) {
            return ByteBuffer.wrap(byArray);
        }
        return ByteBuffer.allocate(0);
    }

    public final void a(ByteOrder byteOrder) {
        this.apS.order(byteOrder);
    }

    public final ByteOrder order() {
        return this.apS.order();
    }

    public final int jD(int n2) {
        if (n2 <= 0) {
            return 0;
        }
        int n3 = this.apS.remaining();
        int n4 = Math.min(n3, n2);
        this.apS.position(this.apS.position() + n4);
        return n4;
    }

    public final int available() {
        return this.apS.remaining();
    }

    public void close() {
    }

    public final int g(byte[] byArray, int n2, int n3) {
        int n4 = Math.min(this.available(), Math.min(byArray.length - n2, n3));
        this.apS.get(byArray, n2, n4);
        return n4;
    }

    public final int U(byte[] byArray) {
        int n2 = Math.min(this.available(), byArray.length);
        this.apS.get(byArray, 0, n2);
        return n2;
    }

    public final byte[] jE(int n2) {
        byte[] byArray = new byte[n2];
        this.apS.get(byArray);
        return byArray;
    }

    public final float readFloat() {
        return this.apS.getFloat();
    }

    public final short readShort() {
        return this.apS.getShort();
    }

    public final int readUnsignedShort() {
        return this.apS.getShort() & 0xFFFF;
    }

    public final int readInt() {
        return this.apS.getInt();
    }

    public final long readUnsignedInt() {
        return (long)this.apS.getInt() & 0xFFFFFFFFL;
    }

    public final long readLong() {
        return this.apS.getLong();
    }

    public final byte readByte() {
        return this.apS.get();
    }

    public final short aqD() {
        return (short)((short)this.apS.get() & 0xFF);
    }

    public final boolean aqE() {
        int n2 = this.apS.position();
        if (n2 == this.cjn && this.cjo <= 6) {
            this.cjo = (byte)(this.cjo + 1);
            return (this.cjp & 1 << 7 - this.cjo) != 0;
        }
        this.cjo = 0;
        this.cjn = n2 + 1;
        this.cjp = this.apS.get();
        int n3 = this.cjp & 0x80;
        return n3 != 0;
    }

    public final String readString() {
        int n2;
        int n3 = this.apS.limit();
        for (n2 = this.apS.position(); n2 < n3 && this.apS.get(n2) != 0; ++n2) {
        }
        if (n2 >= n3) {
            throw new EOFException("Unable to find a valid Null terminated UTF-8 string end.");
        }
        int n4 = n2 - this.apS.position();
        if (n4 > 0) {
            byte[] byArray = new byte[n4];
            this.apS.get(byArray);
            this.apS.get();
            return aey_0.V(byArray);
        }
        this.apS.get();
        return "";
    }

    public final int getOffset() {
        return this.apS.position();
    }

    public final void setOffset(int n2) {
        this.apS.position(n2);
    }
}

