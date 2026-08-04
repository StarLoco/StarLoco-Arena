/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aoo
 */
public class aoo_0
extends yd_2 {
    protected long aj = 0L;
    protected byte cKR = 0;
    protected long cKS = 0L;

    protected void c(OZ oZ) {
        ((aej_0)oZ).a(this);
    }

    protected int TI() {
        return 0;
    }

    protected void A(ByteBuffer byteBuffer) {
    }

    protected void c(ahh_0 ahh_02, ByteBuffer byteBuffer) {
    }

    public long TH() {
        return this.aj;
    }

    public void aW(long l2) {
        this.aj = l2;
    }

    public long aCL() {
        return this.cKS;
    }

    public byte On() {
        return this.cKR;
    }

    public static aoo_0 a(long l2, byte by, long l3) {
        aoo_0 aoo_02 = new aoo_0();
        aoo_02.aj = l2;
        aoo_02.cKR = by;
        aoo_02.cKS = l3;
        return aoo_02;
    }
}

