/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aKq
 */
public class akq_0
extends Eq {
    private xb_2 bge;
    private byte[] Nw;
    private long Bf;

    public akq_0(int n2, int n3, int n4, long l2, int n5, byte[] byArray, el_2 el_22) {
        super(n2, n3, n4);
        this.Bf = l2;
        this.bge = el_22.a(apN.aDK().aDL().Np(), WF.ajj());
        this.Nw = byArray;
        this.bge.iQ(n5);
    }

    public void run() {
        ee_2 ee_22 = (ee_2)apN.aDK().aDL().eg(this.Bf);
        if (this.bge != null) {
            this.bge.ad(this.Nw);
            this.bge.h(ee_22);
            this.bge.aK();
        }
        this.Nn();
    }

    protected void ax() {
    }
}

