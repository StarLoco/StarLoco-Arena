/*
 * Decompiled with CFR 0.152.
 */
public class aaa
extends Eq {
    private long[] ceC;
    private long[] ceD;
    private int ceE;

    public aaa(int n2, int n3, int n4, long[] lArray, long[] lArray2, int n5) {
        super(n2, n3, n4);
        this.ceC = lArray;
        this.ceD = lArray2;
        this.ceE = n5;
    }

    public void run() {
        add_1.aOG().kO("replayDialog");
        add_1.aOG().kO("replayIdentificationCertificate");
        apN.aDK().b(avu_0.aIB());
        apN.aDK().b(bq_2.cF());
        if (!azs_0.aLV().getBooleanProperty("tutorialMode")) {
            aez_0 aez_02;
            cl_1 cl_12;
            int n2;
            for (n2 = 0; n2 < this.ceC.length; ++n2) {
                cl_12 = apN.aDK().aDL().ef(this.ceC[n2]);
                if (cl_12 == null) continue;
                aez_02 = (aez_0)cl_12;
                ajo_1.azb().i(aez_02);
            }
            for (n2 = 0; n2 < this.ceD.length; ++n2) {
                cl_12 = apN.aDK().aDL().ef(this.ceD[n2]);
                if (cl_12 == null) continue;
                aez_02 = (aez_0)cl_12;
                ajo_1.azb().h(aez_02);
            }
            ajo_1.azb().la(this.ceE);
            ajo_1.azb().dC(true);
            apN.aDK().a(ajo_1.azb());
        } else {
            RO.aer().stop();
            azs_0.aLV().g("tutorialMode", false);
        }
        this.Nn();
    }

    protected void ax() {
    }
}

