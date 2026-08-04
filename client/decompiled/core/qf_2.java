/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Renamed from qF
 */
public class qf_2
extends a_0 {
    public static final String TAG = "tl";
    private int aeS;
    private int aeT;
    private BT cG;
    private boolean aeU = true;
    private adg_2[][] aeV;
    private int[] aeW;
    private int[] aeX;
    private boolean[] aeY;
    private boolean[] aeZ;
    private int afa = 0;
    private int afb = 0;
    private int fb = 0;
    private int fc = 0;
    public static final int afc = "rows".hashCode();
    public static final int afd = "columns".hashCode();
    public static final int cJ = "align".hashCode();

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        int n2;
        this.b(aht_12);
        for (n2 = 0; n2 < this.aeT; ++n2) {
            for (int j = 0; j < this.aeS; ++j) {
                adg_2 adg_22 = this.aeV[n2][j];
                if (adg_22 == null || !adg_22.isVisible()) continue;
                agj_1 agj_12 = adg_22.getPrefSize();
                this.aeW[n2] = Math.max(this.aeW[n2], agj_12.width);
                this.aeX[j] = Math.max(this.aeX[j], agj_12.height);
                if (!adg_22.isExpandable()) continue;
                if (!this.aeY[n2]) {
                    ++this.afa;
                }
                if (!this.aeZ[j]) {
                    ++this.afb;
                }
                this.aeY[n2] = true;
                this.aeZ[j] = true;
            }
        }
        this.fb = 0;
        this.fc = 0;
        for (n2 = 0; n2 < this.aeT; ++n2) {
            this.fb += this.aeW[n2];
        }
        for (n2 = 0; n2 < this.aeS; ++n2) {
            this.fc += this.aeX[n2];
        }
        return new agj_1(this.fb, this.fc);
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        return this.getContentPreferedSize(aht_12);
    }

    public void a(aht_1 aht_12) {
        Object object;
        Object object2;
        na_1 na_12;
        int n2;
        int n3;
        int n4;
        int n5 = aht_12.getAppearance().getContentWidth();
        int n6 = aht_12.getAppearance().getContentHeight();
        int n7 = n5 - this.fb;
        int n8 = n7 / ej_0.a(this.afa, 1, new int[0]);
        int n9 = 0;
        int n10 = 0;
        for (n4 = 0; n4 < this.aeT; ++n4) {
            n3 = this.aeW[n4];
            if (this.aeY[n4]) {
                if (++n9 == this.afa) {
                    n3 += n7;
                    n7 = 0;
                } else {
                    n3 += n8;
                    n7 -= n8;
                }
            }
            for (n2 = 0; n2 < this.aeS; ++n2) {
                adg_2 adg_22 = this.aeV[n4][n2];
                if (adg_22 == null || !adg_22.isVisible()) continue;
                na_12 = (mn_2)adg_22.getLayoutData();
                object2 = ((mn_2)na_12).getHorizontalAlign();
                if (object2 != null) {
                    object = adg_22.getPrefSize();
                    adg_22.setX(n10 + object2.ag(object.width, n3));
                    adg_22.setWidth(object.width);
                    continue;
                }
                adg_22.setX(n10);
                adg_22.setWidth(n3);
            }
            n10 += n3;
        }
        n7 = n6 - this.fc;
        n8 = n7 / ej_0.a(this.afb, 1, new int[0]);
        n9 = 0;
        n4 = n6;
        for (n3 = 0; n3 < this.aeS; ++n3) {
            n2 = this.aeX[n3];
            if (this.aeZ[n3]) {
                if (++n9 == this.afb) {
                    n2 += n7;
                    n7 = 0;
                } else {
                    n2 += n8;
                    n7 -= n8;
                }
            }
            n4 -= n2;
            for (int j = 0; j < this.aeT; ++j) {
                na_12 = this.aeV[j][n3];
                if (na_12 == null || !((adg_2)na_12).isVisible()) continue;
                object2 = (mn_2)((adg_2)na_12).getLayoutData();
                object = ((mn_2)object2).getVerticalAlign();
                if (object != null) {
                    agj_1 agj_12 = ((adg_2)na_12).getPrefSize();
                    ((adg_2)na_12).setY(n4 + ((BT)((Object)object)).ah(agj_12.height, n2));
                    ((adg_2)na_12).setHeight(agj_12.height);
                    continue;
                }
                ((adg_2)na_12).setY(n4);
                ((adg_2)na_12).setHeight(n2);
            }
        }
    }

    private void b(aht_1 aht_12) {
        if (this.aeU) {
            this.aeV = new adg_2[this.aeT][];
            for (int j = 0; j < this.aeT; ++j) {
                this.aeV[j] = new adg_2[this.aeS];
            }
            this.aeW = new int[this.aeT];
            this.aeX = new int[this.aeS];
            this.aeY = new boolean[this.aeT];
            this.aeZ = new boolean[this.aeS];
            this.aeU = false;
        }
        this.vQ();
        this.vR();
        ArrayList arrayList = aht_12.getWidgetChildren();
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            mn_2 mn_22;
            adg_2 adg_22 = (adg_2)arrayList.get(j);
            if (!(adg_22.getLayoutData() instanceof mn_2) || (mn_22 = (mn_2)adg_22.getLayoutData()).getRow() >= this.aeS || mn_22.getColumn() >= this.aeT) continue;
            this.aeV[mn_22.getColumn()][mn_22.getRow()] = adg_22;
        }
    }

    private void vQ() {
        if (this.aeV == null) {
            return;
        }
        for (int j = 0; j < this.aeT; ++j) {
            for (int i2 = 0; i2 < this.aeS; ++i2) {
                this.aeV[j][i2] = null;
            }
        }
    }

    private void vR() {
        if (this.aeW != null) {
            Arrays.fill(this.aeW, 0);
        }
        if (this.aeX != null) {
            Arrays.fill(this.aeX, 0);
        }
        if (this.aeY != null) {
            Arrays.fill(this.aeY, false);
        }
        if (this.aeZ != null) {
            Arrays.fill(this.aeZ, false);
        }
        this.afa = 0;
        this.afb = 0;
    }

    public int getRows() {
        return this.aeS;
    }

    public void setRows(int n2) {
        this.aeS = n2;
        this.aeU = true;
    }

    public int getColumns() {
        return this.aeT;
    }

    public void setColumns(int n2) {
        this.aeT = n2;
        this.aeU = true;
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlign(BT bT) {
        this.cG = bT;
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        qf_2 qf_22 = (qf_2)air_12;
        qf_22.setAlign(this.cG);
        qf_22.setRows(this.aeS);
        qf_22.setColumns(this.aeT);
    }

    public qf_2 vS() {
        qf_2 qf_22 = new qf_2();
        qf_22.b();
        this.a((air_1)qf_22);
        return qf_22;
    }

    public void b() {
        super.b();
        this.aeU = true;
    }

    public void j() {
        super.j();
        this.aeV = null;
        this.aeW = null;
        this.aeX = null;
        this.aeY = null;
        this.aeZ = null;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == afc) {
            this.setRows(Gr.R(string));
        } else if (n2 == afd) {
            this.setColumns(Gr.R(string));
        } else if (n2 == cJ) {
            this.setAlign(BT.dv(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }
}

