/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/*
 * Renamed from aIJ
 */
public class aij_1 {
    private OutputStream dQw;
    private ByteArrayOutputStream dQx;
    private int dQy;
    private int dQz;
    private boolean dQA = false;
    private long bcN;
    private boolean dQB;

    public aij_1(OutputStream outputStream) {
        this.dQw = outputStream;
    }

    public aij_1() {
        this.dQx = new ByteArrayOutputStream();
        this.dQw = this.dQx;
        this.dQB = true;
    }

    public aij_1(int n2) {
        this.dQx = new ByteArrayOutputStream(n2);
        this.dQw = this.dQx;
        this.dQB = true;
    }

    public byte[] getData() {
        if (!this.dQB) {
            throw new IllegalStateException("Use this method only with memory streams!");
        }
        try {
            this.dQw.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return this.dQx.toByteArray();
    }

    public static int P(double d) {
        if (d == 0.0) {
            return 1;
        }
        long l2 = (long)(d * 65536.0);
        return aij_1.eA(l2);
    }

    public long aVi() {
        return this.bcN;
    }

    public static int eA(long l2) {
        int n2 = l2 == 0L ? 0 : (int)(Math.floor(Math.log(Math.abs(l2)) / Math.log(2.0)) + 2.0);
        return n2;
    }

    public static int eB(long l2) {
        if (l2 < 1L) {
            return 0;
        }
        return (int)(Math.floor(Math.log(l2) / Math.log(2.0)) + 1.0);
    }

    public void aVj() {
        if (this.dQz > 0) {
            this.dQw.write(this.dQy);
            ++this.bcN;
            this.dQz = 0;
            this.dQy = 0;
        }
    }

    public void close() {
        this.aVj();
        this.dQw.close();
    }

    public void aVk() {
        if (!this.dQA) {
            this.dQw = new BufferedOutputStream(new DeflaterOutputStream(this.dQw, new Deflater(9)));
            this.dQA = true;
        }
    }

    public void flush() {
        this.dQw.flush();
    }

    public void fe(boolean bl2) {
        this.k(bl2 ? 1L : 0L, 1);
    }

    public void writeBytes(byte[] byArray) {
        this.aVj();
        if (byArray == null) {
            return;
        }
        this.dQw.write(byArray);
        this.bcN += (long)byArray.length;
    }

    public void writeDouble(double d) {
        long l2 = Double.doubleToLongBits(d);
        byte[] byArray = new byte[]{(byte)(l2 >> 32), (byte)(l2 >> 40), (byte)(l2 >> 48), (byte)(l2 >> 56), (byte)l2, (byte)(l2 >> 8), (byte)(l2 >> 16), (byte)(l2 >> 24)};
        this.writeBytes(byArray);
    }

    public void Q(double d) {
        this.writeShort((short)(d * 256.0));
    }

    public void b(double d, int n2) {
        long l2 = (long)(d * 65536.0);
        this.j(l2, n2);
    }

    public void writeFloat(float f) {
        this.writeInt(Float.floatToIntBits(f));
    }

    public void bW(float f) {
        int n2 = Float.floatToIntBits(f);
        int n3 = Math.abs((n2 & Integer.MIN_VALUE) >> 31);
        int n4 = (n2 & 0x7F800000) >> 23;
        int n5 = n2 & 0x7FFFFF;
        int n6 = 0;
        if (n4 != 0) {
            n6 = n4 == 255 ? 31 : n4 - 127 + 15;
        }
        int n7 = 0;
        if (n6 < 0) {
            n6 = 0;
        } else if (n6 > 31) {
            n6 = 31;
        } else {
            n7 = n5 >> 13;
        }
        int n8 = n3 << 15;
        n8 |= n6 << 10;
        this.oO(n8 |= n7);
    }

    public void writeShort(short s) {
        this.aVj();
        this.dQw.write(s & 0xFF);
        this.dQw.write(s >> 8);
        this.bcN += 2L;
    }

    public void writeInt(int n2) {
        this.aVj();
        this.dQw.write(n2 & 0xFF);
        this.dQw.write(n2 >> 8);
        this.dQw.write(n2 >> 16);
        this.dQw.write(n2 >> 24);
        this.bcN += 4L;
    }

    public void writeLong(long l2) {
        this.aVj();
        this.dQw.write((byte)(l2 & 0xFFL));
        this.dQw.write((byte)(l2 >> 8));
        this.dQw.write((byte)(l2 >> 16));
        this.dQw.write((byte)(l2 >> 24));
        this.dQw.write((byte)(l2 >> 32));
        this.dQw.write((byte)(l2 >> 40));
        this.dQw.write((byte)(l2 >> 48));
        this.dQw.write((byte)(l2 >> 56));
        this.bcN += 8L;
    }

    public void writeByte(byte by) {
        this.aVj();
        this.dQw.write(by);
        ++this.bcN;
    }

    public void j(long l2, int n2) {
        int n3 = aij_1.eA(l2);
        if (n2 < n3) {
            throw new IOException("At least " + n3 + " bits needed for representation of " + l2);
        }
        this.l(l2, n2);
    }

    public void writeString(String string) {
        this.writeBytes(aey_0.hH(string));
        this.dQw.write(0);
        ++this.bcN;
    }

    public void oO(int n2) {
        this.aVj();
        this.dQw.write(n2 & 0xFF);
        this.dQw.write(n2 >> 8);
        this.bcN += 2L;
    }

    public void eC(long l2) {
        this.aVj();
        this.dQw.write((int)(l2 & 0xFFL));
        this.dQw.write((int)(l2 >> 8));
        this.dQw.write((int)(l2 >> 16));
        this.dQw.write((int)(l2 >> 24));
        this.bcN += 4L;
    }

    public void ct(short s) {
        this.aVj();
        this.dQw.write(s);
        ++this.bcN;
    }

    public void k(long l2, int n2) {
        int n3 = aij_1.eB(l2);
        if (n2 < n3) {
            throw new IOException("At least " + n3 + " bits needed for representation of " + l2 + ". Used bits: " + n2);
        }
        this.l(l2, n2);
    }

    private void l(long l2, int n2) {
        for (int j = n2; j > 0; --j) {
            ++this.dQz;
            if ((1L << j - 1 & l2) != 0L) {
                this.dQy |= 1 << 8 - this.dQz;
            }
            if (this.dQz != 8) continue;
            this.dQw.write(this.dQy);
            ++this.bcN;
            this.dQz = 0;
            this.dQy = 0;
        }
    }

    public void h(byte[] byArray, int n2, int n3) {
        this.aVj();
        if (byArray == null) {
            return;
        }
        this.dQw.write(byArray, n2, n3);
        this.bcN += (long)n3;
    }
}

