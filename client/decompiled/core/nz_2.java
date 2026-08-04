/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from NZ
 */
public class nz_2
implements atG {
    private static final Logger a = Logger.getLogger(nz_2.class);

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        Im im = (Im)pr_02.uy();
        switch (pr_02.getId()) {
            case 1: {
                ll_2 ll_22 = (ll_2)pr_02;
                a.info((Object)("User auth : l=" + ll_22.qc() + " / p=" + ll_22.getPassword()));
                if (ll_22.qc().equals("seb") && ll_22.getPassword().equals("pass")) {
                    tI tI2 = tI.zN();
                    tI2.aC(true);
                    im.b(tI2);
                    im.ig();
                    im.a(new aop_0());
                } else {
                    po_1 po_12 = po_1.uf();
                    po_12.t((byte)1);
                    im.b(po_12);
                }
                bl2 = false;
                break;
            }
            default: {
                if (!im.Uf()) {
                    im.bG(true);
                    po_1 po_13 = po_1.uf();
                    po_13.t((byte)2);
                    im.b(po_13);
                }
                bl2 = false;
            }
        }
        return bl2;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }
}

