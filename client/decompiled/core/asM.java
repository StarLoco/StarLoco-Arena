/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.apache.log4j.Logger;

public class asM
implements alx_0 {
    protected static final Logger a = Logger.getLogger(asM.class);
    private static final asM cSr = new asM();
    private final Object no = new Object();
    private nv_2 cSs = new BY(new hh_0());

    private asM() {
        aam_1.aMF().a(this, 10000L, 123085382);
    }

    public static asM aFI() {
        return cSr;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ByteBuffer getByteBuffer(int n2) {
        Object object = this.no;
        synchronized (object) {
            int n3 = 0x1000000 | n2;
            try {
                return (ByteBuffer)this.cSs.i(n3);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ShortBuffer mc(int n2) {
        Object object = this.no;
        synchronized (object) {
            int n3 = 0x2000000 | n2;
            try {
                return (ShortBuffer)this.cSs.i(n3);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public IntBuffer md(int n2) {
        Object object = this.no;
        synchronized (object) {
            int n3 = 0x3000000 | n2;
            try {
                return (IntBuffer)this.cSs.i(n3);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public FloatBuffer me(int n2) {
        Object object = this.no;
        synchronized (object) {
            int n3 = 0x4000000 | n2;
            try {
                return (FloatBuffer)this.cSs.i(n3);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DoubleBuffer mf(int n2) {
        Object object = this.no;
        synchronized (object) {
            int n3 = 0x5000000 | n2;
            try {
                return (DoubleBuffer)this.cSs.i(n3);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
                return null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void K(ByteBuffer byteBuffer) {
        Object object = this.no;
        synchronized (object) {
            int n2 = 0x1000000 | byteBuffer.limit();
            try {
                this.cSs.c(n2, byteBuffer);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(ShortBuffer shortBuffer) {
        Object object = this.no;
        synchronized (object) {
            int n2 = 0x2000000 | shortBuffer.limit();
            try {
                this.cSs.c(n2, shortBuffer);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(IntBuffer intBuffer) {
        Object object = this.no;
        synchronized (object) {
            int n2 = 0x3000000 | intBuffer.limit();
            try {
                this.cSs.c(n2, intBuffer);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void d(FloatBuffer floatBuffer) {
        Object object = this.no;
        synchronized (object) {
            int n2 = 0x4000000 | floatBuffer.limit();
            try {
                this.cSs.c(n2, floatBuffer);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(DoubleBuffer doubleBuffer) {
        Object object = this.no;
        synchronized (object) {
            int n2 = 0x5000000 | doubleBuffer.limit();
            try {
                this.cSs.c(n2, doubleBuffer);
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String ajv() {
        Object object = this.no;
        synchronized (object) {
            String string = this.getClass().getName() + " statistics : \n";
            boolean bl2 = false;
            string = string + "\tout=" + this.cSs.jy() + ", in=" + this.cSs.jx();
            return string;
        }
    }

    public boolean a(pr_0 pr_02) {
        return false;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }
}

