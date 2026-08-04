/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from GW
 */
public class gw_0
extends Eq {
    long axC;
    int bde;
    int bdf;
    short bdg;
    qc_0 bdh;

    public gw_0(int n2, int n3, int n4, long l2, int n5, int n6, double d, qc_0 qc_02) {
        super(n2, n3, n4);
        this.axC = l2;
        this.bde = n5;
        this.bdf = n6;
        this.bdg = (short)d;
        this.bdh = qc_02;
    }

    public void run() {
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null) {
            Iterable iterable = adu_02.aKn();
            amg_1 amg_12 = null;
            ee_2 ee_22 = (ee_2)adu_02.eg(this.axC);
            if (ee_22 != null) {
                ee_22.m(this.bde, this.bdf, this.bdg);
                ee_22.b(this.bdh);
                amg_12 = ee_22.NW();
            } else {
                cl_1 cl_12 = adu_02.ef(this.axC);
                if (cl_12 != null) {
                    aez_0 aez_02 = (aez_0)cl_12;
                    aez_02.a(this.bde, (double)this.bdf, (double)this.bdg);
                    aez_02.b(this.bdh);
                    amg_12 = aez_02;
                    if (adu_02.ZA() == null) {
                        adu_02.a(new afj_0());
                    }
                    adu_02.ZA().b((byte)(adu_02.ZA().size() + 1), aez_02.aTI());
                }
            }
            if (amg_12 != null) {
                qg_2.g(amg_12);
            } else {
                a.error((Object)("L'acteur " + this.axC + " est inconnu !"));
            }
        } else {
            System.out.println("pas de fight");
            a.error((Object)"Aucun fight !");
        }
        this.Nn();
    }

    protected void ax() {
    }
}

