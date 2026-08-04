/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from amt
 */
public class amt_2
extends yd_2 {
    protected long cGS = 0L;
    protected long Bf = 0L;
    protected fv cGT = null;

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
        return this.cGS;
    }

    public void aW(long l2) {
        this.cGS = l2;
    }

    public fv aBJ() {
        return this.cGT;
    }

    public long mS() {
        return this.Bf;
    }

    public static amt_2 a(gn_0 gn_02, gn_0 gn_03, fv fv2) {
        amt_2 amt_22 = new amt_2();
        amt_22.cGS = gn_02.getId();
        amt_22.Bf = gn_03.getId();
        amt_22.cGT = fv2;
        return amt_22;
    }
}

