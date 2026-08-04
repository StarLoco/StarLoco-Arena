/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from aEA
 */
public abstract class aea_0 {
    protected static final Logger a = Logger.getLogger(aea_0.class);
    private static final int dBo = 32;
    private fb_1 dBp;
    protected boolean dBq = false;
    public static final aea_0 dBr = new lx_1();
    private final int dBs;

    public aea_0() {
        this.dBs = 32;
    }

    public aea_0(int n2) {
        this.dBs = n2;
    }

    public fb_1 aQx() {
        return this.dBp;
    }

    public void a(fb_1 fb_12) {
        this.dBp = fb_12;
    }

    protected void clear() {
        this.dBq = false;
    }

    public void aQy() {
        if (this.dBp != null) {
            this.dBp.Pc();
        }
    }

    public void aQz() {
        if (this.dBp != null) {
            this.dBp.Pd();
        }
    }

    protected void aQA() {
        this.dBq = false;
    }

    protected void kY(String string) {
        a.error((Object)string);
        this.dBq = true;
    }

    protected void a(String string, Exception exception) {
        a.error((Object)string, (Throwable)exception);
        this.dBq = true;
    }

    public boolean hasError() {
        return this.dBq;
    }

    void clearError() {
        this.dBq = false;
    }

    public int lF() {
        return this.dBs;
    }

    public abstract void c(ByteBuffer var1);

    public abstract void f(ByteBuffer var1);
}

