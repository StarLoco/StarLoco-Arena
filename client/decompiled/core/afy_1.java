/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;

/*
 * Renamed from aFY
 */
public class afy_1
extends ep_2 {
    public static final float dHJ = -1.0f;
    private static final String aKN = "\n";
    private static final int dHK = 2;
    private Font dHL;
    private boolean dHM;
    private boolean rG;
    private String IJ = null;
    public static final float[] dHN = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
    public static final Color dHO = new Color(dHN[0], dHN[1], dHN[2], dHN[3]);
    public static final Color dHP = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Color dHQ = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    private float[] dHR = dHN;
    private float dHS = -1.0f;
    private float dHT = 0.0f;
    private float dHU = 0.0f;
    private float Hk = 0.0f;
    private float Hl = 0.0f;
    private List dHV = new ArrayList();
    private int dHW = 0;
    private af_1 adY;
    private boolean aLe = true;
    private boolean dHX = true;
    private float[] dHY = new float[16];
    private int[] ceg = new int[4];
    private int aNV = 0;
    private int aNW = 0;
    private float ceh = 0.0f;
    private float cei = 0.0f;
    private float eV = 1.0f;
    private float eW = 1.0f;
    private float dHZ;
    private float dIa;
    private float dIb;
    private float dIc;

    public afy_1(Font font) {
        this(font, false);
    }

    public afy_1(Font font, boolean bl2) {
        this(font, bl2, false);
    }

    public afy_1(Font font, boolean bl2, boolean bl3) {
        this.dHL = font;
        this.dHM = bl2;
        this.rG = bl3;
        this.aSi();
    }

    public boolean aRZ() {
        return this.dHM;
    }

    public void eP(boolean bl2) {
        this.dHM = bl2;
        this.aSi();
    }

    public boolean aSa() {
        return this.rG;
    }

    public void eQ(boolean bl2) {
        this.rG = bl2;
        this.aSi();
    }

    public Font getFont() {
        return this.dHL;
    }

    public void setFont(Font font) {
        this.dHL = font;
        this.aSi();
    }

    public float getMaxWidth() {
        return this.dHS;
    }

    public void setMaxWidth(float f) {
        this.dHS = f;
        this.aLe = true;
    }

    public float aSb() {
        return this.dHT;
    }

    public void bE(float f) {
        this.dHT = f;
    }

    public void bF(float f) {
        this.dHU = f;
    }

    public String getText() {
        return this.IJ;
    }

    public void setText(String string) {
        this.IJ = string;
        this.aLe = true;
    }

    public void setColor(float f, float f2, float f3, float f4) {
        this.dHR = new float[]{f, f2, f3, f4};
        this.dHX = true;
    }

    public float[] Aa() {
        return this.dHR;
    }

    public float getX() {
        return this.Hk;
    }

    public void x(float f) {
        this.Hk = f;
    }

    public float getY() {
        return this.Hl;
    }

    public void y(float f) {
        this.Hl = f;
    }

    public void r(float f, float f2) {
        this.x(f);
        this.y(f2);
    }

    public float aSc() {
        return this.eV;
    }

    public float aSd() {
        return this.eW;
    }

    public float aSe() {
        return this.dIa;
    }

    public float aSf() {
        return this.dHZ;
    }

    public float aSg() {
        if (this.aLe) {
            this.aSj();
        }
        return this.dIb;
    }

    public float aSh() {
        if (this.aLe) {
            this.aSj();
        }
        return this.dIc;
    }

    public void i(GL gL) {
        if (this.aLe) {
            this.aSj();
        }
        if (this.dHX) {
            this.adY.setColor(this.dHR[0], this.dHR[1], this.dHR[2], this.dHR[3]);
            this.dHX = false;
        }
    }

    protected void j(GL gL) {
        gL.glGetFloatv(2983, this.dHY, 0);
        gL.glGetIntegerv(2978, this.ceg, 0);
        this.aNV = this.ceg[2] - this.ceg[0];
        this.aNW = this.ceg[3] - this.ceg[1];
        this.ceh = (float)this.aNV / 2.0f;
        this.cei = (float)this.aNW / 2.0f;
        this.eV = (this.dHY[0] + this.dHY[4]) * this.ceh;
        this.eW = (this.dHY[1] + this.dHY[5]) * this.cei;
    }

    public void b(GL gL) {
        this.adY.beginRendering(this.aNV, this.aNW);
        int n2 = (int)(this.getX() * this.eV + this.ceh);
        int n3 = (int)(this.getY() * this.eW - (float)this.dHW + this.cei);
        for (int j = this.dHV.size() - 1; j >= 0; --j) {
            char[] cArray = (char[])this.dHV.get(j);
            if (cArray == null) continue;
            this.adY.a(cArray, n2, n3 += this.dHW);
        }
        this.adY.endRendering();
    }

    private void aSi() {
        this.adY = this.rG ? new vg_2(this.dHL, this.dHM, true, uu.aqx) : new vg_2(this.dHL, this.dHM, true);
    }

    private void aSj() {
        String[] stringArray = this.IJ.split(aKN);
        this.dHV.clear();
        this.dHW = 0;
        int n2 = 0;
        float f = 0.0f;
        for (String string : stringArray) {
            int n3 = string.length();
            if (n3 == 0) continue;
            int n4 = 0;
            while (n4 < n3) {
                int n5 = this.adY.a(string.substring(n4), (int)this.dHS);
                String string2 = string.substring(n4, n5);
                this.dHV.add(string2.toCharArray());
                ++n2;
                this.dHW += this.adY.h(string2);
                f = Math.max(f, (float)this.adY.g(string2));
                n4 = n5;
            }
            this.dHV.add(null);
        }
        this.dHW = (int)((float)this.dHW / (float)n2) + 2;
        this.dIb = f;
        this.dIc = this.dHW * n2;
        this.dHZ = Math.max(this.dHT, f);
        this.dIa = Math.max(this.dHU, this.dIc);
        this.aLe = false;
    }
}

