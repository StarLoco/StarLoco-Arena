/*
 * Decompiled with CFR 0.152.
 */
public class yb
extends Eq {
    private tO Eb;

    public yb(int n2, int n3, int n4, tO tO2) {
        super(n2, n3, n4);
        this.Eb = tO2;
    }

    public void run() {
        adu_0 adu_02 = apN.aDK().aDL();
        azs_0.aLV().g("fight.eventCards", adu_02.asv().toArray());
        if (adu_02.ass().JI() == 0) {
            adu_02.ass().start();
        } else {
            adu_02.e(adu_02.ass().nP());
        }
        adu_02.ass().JJ();
        apN.aDK().aDL().a(this.Eb);
        this.Nn();
    }

    protected void ax() {
    }
}

