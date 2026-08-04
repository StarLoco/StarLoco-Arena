/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Formatter;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class atv
extends alp {
    private volatile long hf;
    private static final long cTU = -Calendar.getInstance().getTimeZone().getRawOffset();
    public static final String cTV = "%1$tH:%1$tM:%1$tS.%1$tL";
    private di cTW;

    private void noop() {
        di.a(null, 1);
    }

    protected atv(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIMediaDataUpcast(l2), bl2);
        this.hf = l2;
    }

    protected atv(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIMediaDataUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long c(atv atv2) {
        if (atv2 == null) {
            return 0L;
        }
        return atv2.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public atv FV() {
        if (this.hf == 0L) {
            return null;
        }
        return new atv(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof atv) {
            bl2 = ((atv)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public void a(byte[] byArray, int n2, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(byArray, n2, n3, n4);
    }

    public void a(int n2, byte[] byArray, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(n2, byArray, n3, n4);
    }

    public void b(char[] cArray, int n2, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.b(cArray, n2, n3, n4);
    }

    public void a(int n2, char[] cArray, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(n2, cArray, n3, n4);
    }

    public void a(short[] sArray, int n2, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(sArray, n2, n3, n4);
    }

    public void a(int n2, short[] sArray, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(n2, sArray, n3, n4);
    }

    public void a(int[] nArray, int n2, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(nArray, n2, n3, n4);
    }

    public void a(int n2, int[] nArray, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(n2, nArray, n3, n4);
    }

    public void a(long[] lArray, int n2, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(lArray, n2, n3, n4);
    }

    public void a(int n2, long[] lArray, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(n2, lArray, n3, n4);
    }

    public void a(float[] fArray, int n2, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(fArray, n2, n3, n4);
    }

    public void a(int n2, float[] fArray, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(n2, fArray, n3, n4);
    }

    public void a(double[] dArray, int n2, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(dArray, n2, n3, n4);
    }

    public void a(int n2, double[] dArray, int n3, int n4) {
        di di2 = this.aGB();
        if (di2 == null) {
            return;
        }
        di2.a(n2, dArray, n3, n4);
    }

    public ByteBuffer getByteBuffer() {
        return this.a(null);
    }

    public ByteBuffer a(AtomicReference atomicReference) {
        di di2;
        if (atomicReference != null) {
            atomicReference.set(null);
        }
        if ((di2 = this.aGB()) == null) {
            return null;
        }
        ByteBuffer byteBuffer = di2.a(0, this.getSize(), atomicReference);
        if (byteBuffer != null) {
            byteBuffer.position(0);
            byteBuffer.mark();
            byteBuffer.limit(this.getSize());
        }
        return byteBuffer;
    }

    public String aGz() {
        return this.jz(cTV);
    }

    public String jz(String string) {
        Formatter formatter = new Formatter();
        xv_1 xv_12 = this.HN();
        if (xv_12 == null) {
            xv_12 = xv_1.bc(1, (int)va_0.arK);
        }
        String string2 = formatter.format(string, (long)((double)this.getTimeStamp() * xv_12.getDouble() * 1000.0) + cTU).toString();
        xv_12.delete();
        return string2;
    }

    public di aGA() {
        di di2 = this.aGC();
        if (di2 == null) {
            if (this.cTW != null) {
                this.cTW.delete();
            }
            this.cTW = di2;
        } else if (this.cTW == null) {
            this.cTW = di2.fE();
        } else if (this.cTW.dv() != di2.dv()) {
            this.cTW.delete();
            this.cTW = di2.fE();
        }
        return di2;
    }

    public void d(di di2) {
        if (di2 == null) {
            if (this.cTW != null) {
                this.cTW.delete();
            }
            this.cTW = di2;
        } else if (this.cTW == null) {
            this.cTW = di2.fE();
        } else if (this.cTW.dv() != di2.dv()) {
            this.cTW.delete();
            this.cTW = di2.fE();
        }
        this.e(di2);
    }

    public di aGB() {
        if (this.cTW == null) {
            this.cTW = this.aGC();
        }
        return this.cTW;
    }

    public void delete() {
        if (this.cTW != null) {
            this.cTW.delete();
            this.cTW = null;
        }
        super.delete();
    }

    public long getTimeStamp() {
        return XugglerJNI.IMediaData_getTimeStamp(this.hf, this);
    }

    public void setTimeStamp(long l2) {
        XugglerJNI.IMediaData_setTimeStamp(this.hf, this, l2);
    }

    public xv_1 HN() {
        long l2 = XugglerJNI.IMediaData_getTimeBase(this.hf, this);
        return l2 == 0L ? null : new xv_1(l2, false);
    }

    public void k(xv_1 xv_12) {
        XugglerJNI.IMediaData_setTimeBase(this.hf, this, xv_1.b(xv_12), xv_12);
    }

    protected di aGC() {
        long l2 = XugglerJNI.IMediaData_getData_internal(this.hf, this);
        return l2 == 0L ? null : new di(l2, false);
    }

    public int getSize() {
        return XugglerJNI.IMediaData_getSize(this.hf, this);
    }

    public boolean aGD() {
        return XugglerJNI.IMediaData_isKey(this.hf, this);
    }

    protected void e(di di2) {
        XugglerJNI.IMediaData_setData_internal(this.hf, this, di.a(di2), di2);
    }
}

