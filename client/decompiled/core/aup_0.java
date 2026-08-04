/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aup
 */
public class aup_0
extends atD {
    private ack_1 cWc;
    private long cWd;

    protected void c(OZ oZ) {
        oZ.a(this);
    }

    aup_0() {
    }

    public ack_1 aHr() {
        return this.cWc;
    }

    public long aHs() {
        return this.cWd;
    }

    void m(ack_1 ack_12) {
        this.cWc = ack_12;
    }

    void dR(long l2) {
        this.cWd = l2;
    }

    protected int TI() {
        return 16;
    }

    protected void A(ByteBuffer byteBuffer) {
        byteBuffer.putLong(this.cWc.getId());
        byteBuffer.putLong(this.cWd);
    }

    protected void c(ahh_0 ahh_02, ByteBuffer byteBuffer) {
        this.cWc = ahh_02.ey(byteBuffer.getLong());
        this.cWd = byteBuffer.getLong();
    }

    public aup_0(ack_1 ack_12, long l2) {
        this();
        this.cWc = ack_12;
        this.cWd = l2;
    }

    public static aup_0 a(ack_1 ack_12, long l2) {
        return new aup_0(ack_12, l2);
    }

    static aup_0 aHt() {
        return new aup_0();
    }

    public long TH() {
        return this.cWd;
    }
}

