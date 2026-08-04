/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.text.MessageFormat;

/*
 * Renamed from aKv
 */
public class akv_0
implements Comparable {
    private long aj;
    private int NB;
    private short NC;
    private boolean ND;
    private static final akv_0 dTB = new akv_0(0L, 0, -1, false);

    public akv_0() {
    }

    public akv_0(long l2, int n2, short s, boolean bl2) {
        this.ND = bl2;
        this.aj = l2;
        this.NB = n2;
        this.NC = s;
    }

    public akv_0(akv_0 akv_02) {
        this(akv_02.aj, akv_02.NB, akv_02.NC, akv_02.ND);
    }

    public static akv_0 aVB() {
        return dTB;
    }

    public boolean isInfinite() {
        return this.NC < 0;
    }

    public long K() {
        return this.aj;
    }

    public boolean aEn() {
        return this.ND;
    }

    public short aVC() {
        return this.NC;
    }

    public void setPosition(int n2) {
        this.NB = n2;
    }

    public int getPosition() {
        return this.NB;
    }

    public static akv_0 eF(long l2) {
        akv_0 akv_02 = new akv_0();
        akv_02.aj = l2;
        return akv_02;
    }

    public akv_0 pb(int n2) {
        this.NC = (short)n2;
        return this;
    }

    public akv_0 fi(boolean bl2) {
        this.ND = bl2;
        return this;
    }

    public static int w() {
        return 11;
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.putLong(this.aj);
        byteBuffer.putShort(this.NC);
        byteBuffer.put((byte)(this.ND ? 1 : 0));
    }

    public static akv_0 ab(ByteBuffer byteBuffer) {
        akv_0 akv_02 = new akv_0();
        akv_02.y(byteBuffer);
        return akv_02;
    }

    void y(ByteBuffer byteBuffer) {
        this.aj = byteBuffer.getLong();
        this.NC = byteBuffer.getShort();
        this.ND = byteBuffer.get() == 1;
    }

    public String toString() {
        return MessageFormat.format("@T{0}{2}({1})", this.NC, this.aj, this.ND ? "+" : "-");
    }

    public int c(akv_0 akv_02) {
        if (this == akv_02) {
            return 0;
        }
        if (this.aVD()) {
            return -1;
        }
        if (akv_02 == null || akv_02.aVD()) {
            return 1;
        }
        if (akv_02.aj != this.aj) {
            return 0;
        }
        int n2 = Integer.signum(this.NC - akv_02.NC);
        if (n2 != 0) {
            return n2;
        }
        return (this.ND ? 1 : 0) - (akv_02.ND ? 1 : 0);
    }

    public boolean aVD() {
        return this.aVC() == dTB.aVC() && this.K() == dTB.K() && this.aEn() == dTB.aEn();
    }
}

