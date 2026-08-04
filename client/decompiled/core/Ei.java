/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class Ei
extends ks_2
implements aho_0 {
    private static final String aQc = "breed";
    private static final String aQd = "spheres";
    private static final String aQe = "season";
    private static boolean DEBUG = true;
    public static final String[] ce = new String[]{"breed", "spheres", "season"};
    private akq_1 aQf;
    private int aQg;
    private int aQh;
    private int arG;
    private int ajP;
    private int fb;
    private int fc;
    private boolean aQi = false;

    public Ei(int n2, byte by, int n3, jg_0 jg_02, int n4, short s, short s2) {
        super(n2, by, n3, jg_02, n4, s, s2);
    }

    public void MQ() {
        ayr_0 ayr_02 = (ayr_0)this.Xm();
        if (!this.aQi) {
            ayr_02.aLa();
            this.aQi = true;
        }
        ee_2 ee_22 = adY.atu().dz(afb_1.auN().K());
        for (int j = 0; j < this.bpH.size(); ++j) {
            ayr_0 ayr_03 = (ayr_0)this.bpH.get(j);
            if (ayr_03.azm() && ee_22.NE().contains(ayr_03.getId()) || !ayr_03.azm() && ee_22.NE().contains(ayr_03.aLt()[0].getId()) && (ayr_03.aLt()[1] == null || ee_22.NE().contains(ayr_03.aLt()[1].getId()))) {
                ayr_03.eB(true);
            }
            if (ayr_03.aKX() == 0L) {
                a.error((Object)("Une sphere est dissoci\u00e9e du Sphere Board : " + ayr_03.getId() + " x : " + ayr_03.aut() + " y : " + ayr_03.auu() + " sphereBoard : " + this.aW));
                continue;
            }
            ayr_03.aLg();
            this.fb = Math.max(this.fb, ayr_03.aLe() + 1);
            this.fc = Math.max(this.fc, ayr_03.aLf() + 1);
        }
        akq_1 akq_12 = ((ayr_0)this.Xm()).aLy();
        this.arG = akq_12.getWidth();
        this.ajP = akq_12.getHeight();
    }

    public void a(ayr_0 ayr_02, ayr_0 ayr_03) {
        for (int j = 0; j < this.bpH.size(); ++j) {
            ((ayr_0)this.bpH.get(j)).aLv();
        }
        ayr_03.a(0, ayr_02, (ul_0)this);
        ayr_02.a(null, ayr_03, (ul_0)this);
    }

    public ayr_0 MR() {
        return (ayr_0)this.X(this.MU(), this.MV());
    }

    public ArrayList b(ayr_0 ayr_02) {
        ayr_0 ayr_03 = this.MR();
        if (ayr_03 == ayr_02) {
            return null;
        }
        return this.b(ayr_03, ayr_02);
    }

    public ArrayList b(ayr_0 ayr_02, ayr_0 ayr_03) {
        if (ayr_02 == null || ayr_03 == null) {
            return null;
        }
        if (ayr_02.aux() == ayr_03.aut() && ayr_02.auy() == ayr_03.auu()) {
            ArrayList<ayr_0> arrayList = new ArrayList<ayr_0>();
            arrayList.add(ayr_02);
            arrayList.add(ayr_03);
            return arrayList;
        }
        return this.a((ajM)ayr_02, (ajM)ayr_03);
    }

    public void fi(int n2) {
        this.aQg = n2;
    }

    public void fj(int n2) {
        this.aQh = n2;
    }

    public int MS() {
        return this.aQg;
    }

    public int MT() {
        return this.aQh;
    }

    public int MU() {
        return this.aQg + 1;
    }

    public int MV() {
        return this.aQh + 1;
    }

    public int getCellWidth() {
        return this.arG;
    }

    public int getCellHeight() {
        return this.ajP;
    }

    public int getWidth() {
        return this.fb;
    }

    public int getHeight() {
        return this.fc;
    }

    public akq_1 MW() {
        if (this.aQf == null) {
            try {
                ee_2 ee_22 = adY.atu().dz(afb_1.auN().K());
                int n2 = this.fp * 10 + ee_22.lZ();
                String string = String.format(mu_1.rM().getString("sphereBoardTokenPath"), n2);
                ef_1 ef_12 = cx_0.JY().a(arX.cQT.iE(), ej_0.aa(string), string, new adz_1(), false);
                this.aQf = new akq_1(ef_12);
                this.aQf.azR();
            }
            catch (aih_2 aih_22) {
                a.warn((Object)aih_22.getMessage());
            }
        }
        return this.aQf;
    }

    public void MX() {
        if (this.aQf != null) {
            this.aQf.setTexture(null);
            this.aQf = null;
        }
        for (int j = this.bpH.size() - 1; j >= 0; --j) {
            ((ayr_0)this.bpH.get(j)).MX();
            ((ayr_0)this.bpH.get(j)).eB(false);
        }
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aQc)) {
            return aon_0.aYc().a(5, this.fp, new Object[0]);
        }
        if (string.equals(aQd)) {
            return this.bpH;
        }
        if (string.equals(aQe)) {
            return this.fo;
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

