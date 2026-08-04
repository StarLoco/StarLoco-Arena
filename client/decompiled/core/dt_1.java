/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Dt
 */
public class dt_1
extends Eq {
    private er_1 aNG;
    private int aNH;
    private int aNI;
    private int aNJ;
    private short nG;

    public dt_1(int n2, int n3, int n4, int n5, int n6, int n7, short s) {
        super(n2, n3, n4);
        this.aNH = n5;
        this.aNI = n6;
        this.aNJ = n7;
        this.nG = s;
    }

    public void run() {
        this.aNG = (er_1)ame_1.aWP().eN(this.aNH);
        this.aNG.m(this.aNI, this.aNJ, this.nG);
        vt_0.aiU().activate();
        vt_0.aiU().l(this.aNG);
        this.Nn();
    }

    protected void ax() {
    }
}

