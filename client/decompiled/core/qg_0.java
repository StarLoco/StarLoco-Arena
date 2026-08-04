/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;

/*
 * Renamed from qG
 */
public class qg_0 {
    private static final int afe = 10;
    protected byte[] aff;
    protected int afg;
    protected int m_size;
    protected int afh;

    public qg_0() {
        this.aff = new byte[10];
        this.afg = 10;
        this.m_size = 0;
        this.afh = 10;
    }

    public qg_0(qg_0 qg_02) {
        this.afg = this.m_size = qg_02.m_size;
        this.aff = new byte[qg_02.m_size];
        this.afh = qg_02.afh;
    }

    public qg_0(int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("La taille du tableau doit \u00eatre >= 0");
        }
        this.aff = new byte[n2];
        this.afg = n2;
        this.m_size = 0;
        this.afh = 10;
    }

    public qg_0(int n2, int n3) {
        if (n2 < 0) {
            throw new IllegalArgumentException("La taille du tableau doit \u00eatre >= 0");
        }
        if (n3 < 1) {
            throw new IllegalArgumentException("L'incr\u00e9ment de taille growth doit \u00eatre >= 1");
        }
        this.aff = new byte[n2];
        this.afg = n2;
        this.m_size = 0;
        this.afh = n3;
    }

    public static qg_0 s(byte[] byArray) {
        if (byArray == null) {
            throw new IllegalArgumentException("Le tableau ne peut \u00eatre nul");
        }
        qg_0 qg_02 = new qg_0();
        qg_02.aff = byArray;
        qg_02.afg = byArray.length;
        qg_02.afh = 10;
        qg_02.m_size = qg_02.afg;
        return qg_02;
    }

    public void x(byte by) {
        this.ensureCapacity(this.m_size + 1);
        this.aff[this.m_size] = by;
        ++this.m_size;
    }

    public void t(byte[] byArray) {
        int n2 = byArray.length;
        this.ensureCapacity(this.m_size + n2);
        System.arraycopy(byArray, 0, this.aff, this.m_size, n2);
        this.m_size += n2;
    }

    public void c(byte[] byArray, int n2) {
        this.ensureCapacity(this.m_size + n2);
        System.arraycopy(byArray, 0, this.aff, this.m_size, n2);
        this.m_size += n2;
    }

    public void b(byte[] byArray, int n2, int n3) {
        this.ensureCapacity(this.m_size + n3);
        System.arraycopy(byArray, n2, this.aff, this.m_size, n3);
        this.m_size += n3;
    }

    public void a(qg_0 qg_02) {
        this.b(qg_02.aff, 0, qg_02.m_size);
    }

    public void putBoolean(boolean bl2) {
        this.x(bl2 ? (byte)1 : 0);
    }

    public void c(char c) {
        this.ensureCapacity(this.m_size + 2);
        this.aff[this.m_size] = (byte)(0xFF & c >> 8);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFF & c);
        ++this.m_size;
    }

    public void S(short s) {
        this.ensureCapacity(this.m_size + 2);
        this.aff[this.m_size] = (byte)(0xFF & s >> 8);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFF & s);
        ++this.m_size;
    }

    public void putInt(int n2) {
        this.ensureCapacity(this.m_size + 4);
        this.aff[this.m_size] = (byte)(0xFF & n2 >> 24);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFF & n2 >> 16);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFF & n2 >> 8);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFF & n2);
        ++this.m_size;
    }

    public void aB(long l2) {
        this.ensureCapacity(this.m_size + 8);
        this.aff[this.m_size] = (byte)(0xFFL & l2 >> 56);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFFL & l2 >> 48);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFFL & l2 >> 40);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFFL & l2 >> 32);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFFL & l2 >> 24);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFFL & l2 >> 16);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFFL & l2 >> 8);
        ++this.m_size;
        this.aff[this.m_size] = (byte)(0xFFL & l2);
        ++this.m_size;
    }

    public boolean contains(byte by) {
        for (int j = 0; j < this.m_size; ++j) {
            if (this.aff[j] != by) continue;
            return true;
        }
        return false;
    }

    public void N(float f) {
        this.putInt(Float.floatToIntBits(f));
    }

    public void h(double d) {
        this.aB(Double.doubleToLongBits(d));
    }

    public byte get(int n2) {
        if (n2 >= this.m_size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.aff[n2];
    }

    public int size() {
        return this.m_size;
    }

    public byte[] vT() {
        return this.aff;
    }

    public byte[] toArray() {
        byte[] byArray = new byte[this.m_size];
        System.arraycopy(this.aff, 0, byArray, 0, this.m_size);
        return byArray;
    }

    private void ensureCapacity(int n2) {
        if (n2 > this.afg) {
            this.afg = n2 + this.afh;
            byte[] byArray = new byte[this.afg];
            System.arraycopy(this.aff, 0, byArray, 0, this.m_size);
            this.aff = byArray;
        }
    }

    public void clear() {
        Arrays.fill(this.aff, (byte)0);
        this.m_size = 0;
    }
}

