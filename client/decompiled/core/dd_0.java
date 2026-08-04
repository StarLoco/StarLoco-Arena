/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/*
 * Renamed from Dd
 */
public class dd_0
implements bc_1 {
    private final Bk ub = LD.p(this.getClass());
    public static final boolean DN = true;
    private final ReadableByteChannel aNg;
    private final WritableByteChannel aNh;
    private final boolean DQ;
    private Channel aNi = null;

    public dd_0(ReadableByteChannel readableByteChannel) {
        this(readableByteChannel, null, true);
    }

    public dd_0(WritableByteChannel writableByteChannel) {
        this(null, writableByteChannel, true);
    }

    public dd_0(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel, boolean bl2) {
        if (readableByteChannel == null && writableByteChannel == null) {
            throw new IllegalArgumentException("must pass one non null stream");
        }
        this.aNg = readableByteChannel;
        this.aNh = writableByteChannel;
        this.DQ = bl2;
    }

    public int de() {
        int n2 = 0;
        try {
            if (this.aNi != null && this.DQ) {
                this.aNi.close();
            }
        }
        catch (IOException iOException) {
            this.ub.e("could not close stream {}: {}", this.aNi, (Object)iOException);
            n2 = -1;
        }
        this.aNi = null;
        return n2;
    }

    public int d(String string, int n2) {
        if (this.aNi != null) {
            this.ub.j("attempting to open already open handler: {}", this.aNi);
            return -1;
        }
        switch (n2) {
            case 2: {
                this.ub.debug("do not support read/write mode for Java IO Handlers");
                return -1;
            }
            case 1: {
                this.aNi = this.aNh;
                if (this.aNi != null) break;
                this.ub.m("No OutputStream specified for writing: {}", string);
                return -1;
            }
            case 0: {
                this.aNi = this.aNg;
                if (this.aNi != null) break;
                this.ub.m("No InputStream specified for reading: {}", string);
                return -1;
            }
            default: {
                this.ub.m("Invalid flag passed to open: {}", string);
                return -1;
            }
        }
        return 0;
    }

    public int a(byte[] byArray, int n2) {
        int n3 = -1;
        if (this.aNi == null || !(this.aNi instanceof ReadableByteChannel)) {
            return -1;
        }
        try {
            ReadableByteChannel readableByteChannel = (ReadableByteChannel)this.aNi;
            ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
            n3 = readableByteChannel.read(byteBuffer);
            if (n3 > 0) {
                byteBuffer.flip();
                byteBuffer.get(byArray, 0, n3);
            }
            return n3;
        }
        catch (IOException iOException) {
            this.ub.e("Got IO exception reading from channel: {}; {}", this.aNi, (Object)iOException);
            return -1;
        }
    }

    public long a(long l2, int n2) {
        return -1L;
    }

    public int b(byte[] byArray, int n2) {
        if (this.aNi == null || !(this.aNi instanceof WritableByteChannel)) {
            return -1;
        }
        try {
            WritableByteChannel writableByteChannel = (WritableByteChannel)this.aNi;
            ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
            byteBuffer.put(byArray, 0, n2);
            byteBuffer.flip();
            return writableByteChannel.write(byteBuffer);
        }
        catch (IOException iOException) {
            this.ub.e("Got error writing to file: {}; {}", this.aNi, (Object)iOException);
            return -1;
        }
    }

    public boolean e(String string, int n2) {
        return true;
    }

    public ReadableByteChannel Lu() {
        return this.aNg;
    }

    public WritableByteChannel Lv() {
        return this.aNh;
    }

    public Channel Lw() {
        return this.aNi;
    }

    public boolean oM() {
        return this.DQ;
    }
}

