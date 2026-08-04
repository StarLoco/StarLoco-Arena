/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from wO
 */
public class wo_1
extends ee_2 {
    public wo_1() {
    }

    public wo_1(ee_2 ee_22) {
        this.aTv = ee_22;
        ee_22.LQ().a(this);
        this.setName(ee_22.getName());
        xq xq2 = ee_22.NY();
        this.b(ee_22.cc(), xq2.lV(), ee_22.lZ());
        this.P(ee_22.lY());
        this.Q(ee_22.lX());
        this.R(ee_22.Ns());
        this.a(xq2, this.aTv.d(Lr.bqA), xq2.ok());
    }

    private void a(xq xq2, int n2, int n3) {
        ll_0 ll_02 = this.baQ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            alm_0 alm_02 = (alm_0)ll_02.value();
            alm_02.atS();
        }
        this.a(Lr.bqx).at(xq2.ok());
        this.a(Lr.bqz).at(xq2.om());
        this.a(Lr.bqy).at(xq2.ol());
        this.a(Lr.bqy).aAF();
        this.a(Lr.bqx).set(n3);
        this.a(Lr.bqz).aAF();
        this.a(Lr.bqA).at(n2);
        this.a(Lr.bqA).aAF();
        this.a(Lr.brd).set(xq2.DT());
        this.a(Lr.bre).set(xq2.DU());
    }

    public boolean Dk() {
        return true;
    }

    public boolean b(ByteBuffer byteBuffer) {
        this.c(byteBuffer.getLong());
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        xq xq2 = xq.ej(byteBuffer.get());
        byte by = byteBuffer.get();
        byte by2 = byteBuffer.get();
        this.b(by2, xq2.lV(), by);
        this.a(xq2, byteBuffer.getInt(), byteBuffer.getInt());
        return true;
    }
}

