/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mn
 */
public class mn_2
extends dz_2 {
    public static final String TAG = "tld";
    private int Jy;
    private int Jz;
    private BT JA = null;
    private BT JB = null;
    public static final int JC = "row".hashCode();
    public static final int JD = "column".hashCode();
    public static final int JE = "horizontalAlign".hashCode();
    public static final int JF = "verticalAlign".hashCode();

    public int getRow() {
        return this.Jy;
    }

    public void setRow(int n2) {
        this.Jy = n2;
    }

    public int getColumn() {
        return this.Jz;
    }

    public void setColumn(int n2) {
        this.Jz = n2;
    }

    public BT getHorizontalAlign() {
        return this.JA;
    }

    public void setHorizontalAlign(BT bT) {
        this.JA = bT;
    }

    public BT getVerticalAlign() {
        return this.JB;
    }

    public void setVerticalAlign(BT bT) {
        this.JB = bT;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        mn_2 mn_22 = (mn_2)air_12;
        mn_22.setRow(this.Jy);
        mn_22.setColumn(this.Jz);
        mn_22.setHorizontalAlign(this.JA);
        mn_22.setVerticalAlign(this.JB);
    }

    public void j() {
        super.j();
        this.JA = null;
        this.JB = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == JC) {
            this.setRow(Gr.R(string));
        } else if (n2 == JD) {
            this.setColumn(Gr.R(string));
        } else if (n2 == JE) {
            this.setHorizontalAlign(BT.dv(string));
        } else if (n2 == JF) {
            this.setVerticalAlign(BT.dv(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }
}

