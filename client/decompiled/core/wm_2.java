/*
 * Decompiled with CFR 0.152.
 */
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Renamed from WM
 */
public class wm_2
implements bc_1 {
    private final Bk ub = LD.p(this.getClass());
    public static final boolean DN = true;
    private final InputStream bVj;
    private final OutputStream bVk;
    private final boolean DQ;
    private Closeable bVl = null;

    public wm_2(InputStream inputStream) {
        this(inputStream, null, true);
    }

    public wm_2(OutputStream outputStream) {
        this(null, outputStream, true);
    }

    public wm_2(InputStream inputStream, OutputStream outputStream, boolean bl2) {
        if (inputStream == null && outputStream == null) {
            throw new IllegalArgumentException("must pass one non null stream");
        }
        this.bVj = inputStream;
        this.bVk = outputStream;
        this.DQ = bl2;
    }

    public int de() {
        int n2 = 0;
        try {
            if (this.bVl != null && this.DQ) {
                this.bVl.close();
            }
        }
        catch (IOException iOException) {
            this.ub.e("could not close stream {}: {}", this.bVl, (Object)iOException);
            n2 = -1;
        }
        this.bVl = null;
        return n2;
    }

    public int d(String string, int n2) {
        if (this.bVl != null) {
            this.ub.j("attempting to open already open handler: {}", this.bVl);
            return -1;
        }
        switch (n2) {
            case 2: {
                this.ub.debug("do not support read/write mode for Java IO Handlers");
                return -1;
            }
            case 1: {
                this.bVl = this.bVk;
                if (this.bVl != null) break;
                this.ub.m("No OutputStream specified for writing: {}", string);
                return -1;
            }
            case 0: {
                this.bVl = this.bVj;
                if (this.bVl != null) break;
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
        if (this.bVl == null || !(this.bVl instanceof InputStream)) {
            return -1;
        }
        try {
            InputStream inputStream = (InputStream)this.bVl;
            n3 = inputStream.read(byArray, 0, n2);
            return n3;
        }
        catch (IOException iOException) {
            this.ub.e("Got IO exception reading from stream: {}; {}", this.bVl, (Object)iOException);
            return -1;
        }
    }

    public long a(long l2, int n2) {
        return -1L;
    }

    public int b(byte[] byArray, int n2) {
        if (this.bVl == null || !(this.bVl instanceof OutputStream)) {
            return -1;
        }
        try {
            OutputStream outputStream = (OutputStream)this.bVl;
            outputStream.write(byArray, 0, n2);
            return n2;
        }
        catch (IOException iOException) {
            this.ub.e("Got error writing to file: {}; {}", this.bVl, (Object)iOException);
            return -1;
        }
    }

    public boolean e(String string, int n2) {
        return true;
    }

    public InputStream getInputStream() {
        return this.bVj;
    }

    public OutputStream getOutputStream() {
        return this.bVk;
    }

    public Closeable ajo() {
        return this.bVl;
    }

    public boolean oM() {
        return this.DQ;
    }
}

