/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from PD
 */
public class pd_0
extends Eq {
    public pd_0(int n2, int n3, int n4) {
        super(n2, n3, n4);
    }

    public void run() {
        try {
            adu_0 adu_02 = apN.aDK().aDL();
            if (adu_02 != null) {
                ee_2 ee_22 = (ee_2)adu_02.ass().nP();
                if (ee_22 != null) {
                    if (ee_22.getId() == this.Nl()) {
                        adu_02.ap(ee_22.getId());
                    } else {
                        a.info((Object)("fin de tour pr\u00e9matur\u00e9e du client : " + ee_22.getId() + " au lieu de " + this.Nl()));
                    }
                } else {
                    adu_02.ap(this.Nl());
                }
            }
        }
        catch (Exception exception) {
            a.error((Object)"Error : ", (Throwable)exception);
        }
        this.Nn();
    }

    protected void ax() {
    }
}

