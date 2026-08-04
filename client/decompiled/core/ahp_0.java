/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from aHp
 */
public class ahp_0 {
    private ArrayList EK = new ArrayList();
    private aur dLZ;
    private int EN;
    private int EP;
    private int EO = Integer.MAX_VALUE;
    private int EQ;
    private int bJn;
    private int bJo;
    private boolean vd = false;
    private int dkU;
    private float aC = 1.0f;
    private akq_1 arn = null;
    private boolean aQv = true;
    private float dMa;
    private float dMb;

    public ahp_0() {
        this.EN = Integer.MAX_VALUE;
        this.EQ = Integer.MIN_VALUE;
        this.EP = Integer.MIN_VALUE;
    }

    public ArrayList ph() {
        return this.EK;
    }

    public void a(aur aur2) {
        this.dLZ = aur2;
    }

    public void a(short s, short s2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.EK.add(new vb_1(s, s2, f, f2, f3, f4, f5, f6, f7, f8));
        this.EN = Math.min(this.EN, s);
        this.EP = Math.max(this.EP, s);
        this.EO = Math.min(this.EO, s2);
        this.EQ = Math.max(this.EQ, s2);
    }

    public void aJq() {
        vb_1 vb_12;
        int n2;
        Collections.sort(this.EK);
        int n3 = (this.EN + this.EP) / 2;
        int n4 = (this.EO + this.EQ) / 2;
        vb_1 vb_13 = null;
        for (n2 = this.EK.size() - 1; n2 >= 0; --n2) {
            vb_12 = (vb_1)this.EK.get(n2);
            if (vb_1.f(vb_12) != n3 || vb_1.g(vb_12) != n4) continue;
            vb_13 = vb_12;
            break;
        }
        if (vb_13 == null && this.EK.size() > 0) {
            for (n2 = this.EK.size() / 2; n2 >= 0; --n2) {
                vb_12 = (vb_1)this.EK.get(n2);
                if (vb_1.f(vb_12) != n3) continue;
                vb_13 = vb_12;
                break;
            }
        }
        if (vb_13 == null && this.EK.size() > 0) {
            vb_13 = (vb_1)this.EK.get(this.EK.size() / 2);
        }
        if (vb_13 != null) {
            this.bJn = vb_1.f(vb_13);
            this.bJo = vb_1.g(vb_13);
            this.dMa = (vb_13.ait() + vb_13.aix()) / 2.0f;
            this.dMb = (vb_13.aiu() + vb_13.aiy()) / 2.0f;
        }
    }

    public boolean contains(int n2, int n3) {
        int n4 = this.EK.size();
        for (int j = 0; j < n4; ++j) {
            if (((vb_1)this.EK.get(j)).pi() != n2 || ((vb_1)this.EK.get(j)).pj() != n3) continue;
            return true;
        }
        return false;
    }

    vb_1 bZ(int n2, int n3) {
        int n4 = this.EK.size();
        for (int j = 0; j < n4; ++j) {
            vb_1 vb_12 = (vb_1)this.EK.get(j);
            if (vb_1.f(vb_12) != n2 || vb_1.g(vb_12) != n3) continue;
            return vb_12;
        }
        return null;
    }

    public float[] aiF() {
        float[] fArray = new float[this.EK.size() * 8];
        int n2 = this.EK.size();
        for (int j = 0; j < n2; ++j) {
            vb_1 vb_12 = (vb_1)this.EK.get(j);
            fArray[j * 8] = vb_12.bSG;
            fArray[j * 8 + 1] = vb_12.bSH;
            fArray[j * 8 + 2] = vb_12.bSI;
            fArray[j * 8 + 3] = vb_12.bSJ;
            fArray[j * 8 + 4] = vb_12.bSK;
            fArray[j * 8 + 5] = vb_12.bSL;
            fArray[j * 8 + 6] = vb_12.bSM;
            fArray[j * 8 + 7] = vb_12.bSN;
        }
        return fArray;
    }

    public void a(ps_0 ps_02) {
        for (int j = this.EN - 1; j < this.EP + 1; ++j) {
            for (int i2 = this.EO - 1; i2 < this.EQ + 1; ++i2) {
                vb_1 vb_12 = this.bZ(j, i2);
                vb_1 vb_13 = this.bZ(j, i2 + 1);
                vb_1 vb_14 = this.bZ(j + 1, i2);
                if (vb_12 == null) {
                    if (vb_13 != null) {
                        ps_02.add(vb_13.bSI);
                        ps_02.add(vb_13.bSJ);
                        ps_02.add(vb_13.bSK);
                        ps_02.add(vb_13.bSL);
                    }
                    if (vb_14 == null) continue;
                    ps_02.add(vb_14.bSG);
                    ps_02.add(vb_14.bSH);
                    ps_02.add(vb_14.bSI);
                    ps_02.add(vb_14.bSJ);
                    continue;
                }
                if (vb_13 == null) {
                    ps_02.add(vb_12.bSG);
                    ps_02.add(vb_12.bSH);
                    ps_02.add(vb_12.bSM);
                    ps_02.add(vb_12.bSN);
                }
                if (vb_14 != null) continue;
                ps_02.add(vb_12.bSK);
                ps_02.add(vb_12.bSL);
                ps_02.add(vb_12.bSM);
                ps_02.add(vb_12.bSN);
            }
        }
    }

    public int getMinX() {
        return this.EN;
    }

    public int aTS() {
        return this.EP;
    }

    public int getMinY() {
        return this.EO;
    }

    public int aTT() {
        return this.EQ;
    }

    public vP getColor() {
        return this.dLZ.Sh();
    }

    public String Si() {
        return this.dLZ.Si();
    }

    public boolean isSelected() {
        return this.vd;
    }

    public void setSelected(boolean bl2) {
        if (this.vd == bl2) {
            return;
        }
        this.vd = bl2;
    }

    public int aLz() {
        return this.dkU;
    }

    public void mP(int n2) {
        this.dkU = n2;
    }

    public float getLineWidth() {
        return this.aC;
    }

    public void b(float f) {
        this.aC = f;
    }

    public int aea() {
        return this.bJn;
    }

    public int aeb() {
        return this.bJo;
    }

    public float aTU() {
        return this.dMa;
    }

    public float aTV() {
        return this.dMb;
    }

    public akq_1 getPixmap() {
        return this.arn;
    }

    public void setPixmap(akq_1 akq_12) {
        this.arn = akq_12;
    }

    public void cleanUp() {
        if (this.arn != null && this.arn.jI() != null) {
            this.arn.jI().HF();
            this.arn = null;
        }
    }

    public aur aTW() {
        return this.dLZ;
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public void setVisible(boolean bl2) {
        this.aQv = bl2;
    }
}

