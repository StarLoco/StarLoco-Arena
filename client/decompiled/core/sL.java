/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;

class sL
implements aMO {
    final /* synthetic */ jg_2 alR;
    final /* synthetic */ pl_2 alS;

    sL(pl_2 pl_22, jg_2 jg_22) {
        this.alS = pl_22;
        this.alR = jg_22;
    }

    public void a(byte[] byArray, byte[] byArray2) {
        gc_0 gc_02 = new gc_0();
        gc_02.am(this.alR.mb());
        gc_02.G(this.alR.lW());
        gc_02.bq(Arrays.equals(byArray2, this.alR.lW()));
        apN.aDK().vJ().b(gc_02);
        pl_2.a(this.alS, byArray);
    }
}

