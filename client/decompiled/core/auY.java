/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class auY
implements aho_0 {
    public static final String cXU = "title";
    public static final String cXV = "description";
    public static final String cXW = "descNormal";
    public static final String cXX = "descRepro";
    public static final String cXY = "types";
    public static final String cXZ = "selectedType";
    private String cYa;
    private String cYb;
    private String cYc;
    private String cYd;
    private String cYe;
    private asn_0 cYf;
    private long Pl;
    private int cYg;
    private int cYh;
    private boolean cYi;
    private String aca;
    private String cYj;
    private long cYk;
    private String cYl;
    private String cYm;
    private String cYn;
    private String ahG = aon_0.aYc().getString("ReportBug.graphismeMapSon");
    private String cYo;
    public static final String[] ce = new String[]{"title", "description", "descNormal", "descRepro", "types", "selectedType"};

    public void jF(String string) {
        this.cYa = string;
    }

    public void jG(String string) {
        this.cYb = string;
    }

    public void jH(String string) {
        this.cYc = string;
    }

    public void jI(String string) {
        this.cYd = string;
    }

    public void jJ(String string) {
        this.cYe = string;
    }

    public void c(asn_0 asn_02) {
        this.cYf = asn_02;
    }

    public void em(boolean bl2) {
        this.cYi = bl2;
    }

    public void at(long l2) {
        this.Pl = l2;
    }

    public void ms(int n2) {
        this.cYg = n2;
    }

    public void mt(int n2) {
        this.cYh = n2;
    }

    public void bx(String string) {
        this.aca = string;
    }

    public void jK(String string) {
        this.cYj = string;
    }

    public void dT(long l2) {
        this.cYk = l2;
    }

    public void jL(String string) {
        this.cYl = string;
    }

    public void jM(String string) {
        this.cYm = string;
    }

    public void setType(String string) {
        this.ahG = string;
    }

    public void setVersion(String string) {
        this.cYo = string;
    }

    public void jN(String string) {
        this.cYn = string;
    }

    public String aHM() {
        return this.cYa;
    }

    public String aHN() {
        return this.cYb;
    }

    public String aHO() {
        return this.cYc;
    }

    public String aHP() {
        return this.cYd;
    }

    public String aHQ() {
        return this.cYe;
    }

    public asn_0 lb() {
        return this.cYf;
    }

    public long Ke() {
        return this.Pl;
    }

    public int aHR() {
        return this.cYg;
    }

    public int aHS() {
        return this.cYh;
    }

    public boolean aHT() {
        return this.cYi;
    }

    public String uj() {
        return this.aca;
    }

    public String aHU() {
        return this.cYj;
    }

    public long aHV() {
        return this.cYk;
    }

    public String aHW() {
        return this.cYl;
    }

    public String aHX() {
        return this.cYm;
    }

    public String getType() {
        return this.ahG;
    }

    public String getVersion() {
        return this.cYo;
    }

    public String[] getFields() {
        return ce;
    }

    public String aHY() {
        return this.cYn;
    }

    public Object getFieldValue(String string) {
        if (string.equals(cXU)) {
            return this.aHM();
        }
        if (string.equals(cXV)) {
            return this.aHN();
        }
        if (string.equals(cXW)) {
            return this.aHO();
        }
        if (string.equals(cXX)) {
            return this.aHP();
        }
        if (string.equals(cXY)) {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(aon_0.aYc().getString("ReportBug.graphismeMapSon"));
            arrayList.add(aon_0.aYc().getString("ReportBug.interfaceTextTraduction"));
            arrayList.add(aon_0.aYc().getString("ReportBug.invocationSpellIA"));
            arrayList.add(aon_0.aYc().getString("ReportBug.itemEquipmentSet"));
            arrayList.add(aon_0.aYc().getString("ReportBug.other"));
            return arrayList.toArray();
        }
        if (string.equals(cXZ)) {
            return this.getType();
        }
        return null;
    }

    public void a(String string, Object object) {
        if (string.equals(cXU)) {
            this.jF(object.toString());
        } else if (string.equals(cXV)) {
            this.jG(object.toString());
        } else if (string.equals(cXW)) {
            this.jH(object.toString());
        } else if (string.equals(cXX)) {
            this.jI(object.toString());
        } else if (string.equals(cXZ)) {
            this.setType(object.toString());
        } else if (string.equals(cXY)) {
            this.setType(object.toString());
        }
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        if (string.equals(cXU)) {
            return true;
        }
        if (string.equals(cXV)) {
            return true;
        }
        if (string.equals(cXW)) {
            return true;
        }
        if (string.equals(cXX)) {
            return true;
        }
        if (string.equals(cXY)) {
            return false;
        }
        return string.equals(cXZ);
    }
}

