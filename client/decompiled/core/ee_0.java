/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ee
 */
public class ee_0
extends na_1 {
    public static final String TAG = "tableColumn";
    private String m_name;
    private String aPH;
    private String aPI;
    private int arG;
    private int aPJ;
    private boolean aPK;
    private sn_0 dR = new sn_0();
    public static final int aru = "name".hashCode();
    public static final int aPL = "field".hashCode();
    public static final int aPM = "sortable".hashCode();
    public static final int aPN = "columnIndex".hashCode();
    public static final int aPO = "columnId".hashCode();
    public static final int aPP = "cellWidth".hashCode();

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof ie) {
            this.dR.a((ie)na_12);
        }
    }

    public String getTag() {
        return TAG;
    }

    public sn_0 getRendererManager() {
        return this.dR;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public String getField() {
        return this.aPH;
    }

    public void setField(String string) {
        this.aPH = string;
    }

    public boolean getSortable() {
        return this.aPK;
    }

    public void setSortable(boolean bl2) {
        this.aPK = bl2;
    }

    public int getColumnIndex() {
        return this.aPJ;
    }

    public void setColumnIndex(int n2) {
        this.aPJ = n2;
    }

    public String getColumnId() {
        return this.aPI;
    }

    public void setColumnId(String string) {
        this.aPI = string;
    }

    public int getCellWidth() {
        return this.arG;
    }

    public void setCellWidth(int n2) {
        this.arG = n2;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        ee_0 ee_02 = (ee_0)air_12;
        ee_02.setName(this.m_name);
        ee_02.setField(this.aPH);
        ee_02.setSortable(this.aPK);
        ee_02.setColumnIndex(this.aPJ);
        ee_02.setColumnId(this.aPI);
        ee_02.setCellWidth(this.arG);
    }

    public void j() {
        super.j();
        this.m_name = null;
        this.aPH = null;
        this.aPI = null;
    }

    public void b() {
        super.b();
        this.aPJ = -1;
        this.arG = 30;
        this.aPK = true;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aru) {
            this.setName(if_12.eM(string));
        } else if (n2 == aPL) {
            this.setField(string);
        } else if (n2 == aPM) {
            this.setSortable(Gr.getBoolean(string));
        } else if (n2 == aPN) {
            this.setColumnIndex(Gr.R(string));
        } else if (n2 == aPO) {
            this.setColumnId(string);
        } else if (n2 == aPP) {
            this.setCellWidth(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == aru) {
            this.setName((String)object);
        } else if (n2 == aPL) {
            this.setField((String)object);
        } else if (n2 == aPM) {
            this.setSortable(Gr.getBoolean(object));
        } else if (n2 == aPN) {
            this.setColumnIndex(Gr.R(object));
        } else if (n2 == aPO) {
            this.setColumnId((String)object);
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

