/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from Ch
 */
public final class ch_2
implements Iterable {
    private static final int aKK = 1;
    private static final int aKL = Integer.MAX_VALUE;
    private static final String aKM = "...";
    private static final String aKN = "\n";
    private yt_1 aKO = null;
    private Color aKP = Color.BLACK;
    private boolean aKQ = false;
    private BP aKR = BP.aJA;
    private BP aKS = BP.aJx;
    private aiq_0 aKT = aiq_0.cxW;
    private final jz aCQ;
    private final ArrayList aKU = new ArrayList();
    private aef_2 aKV = null;
    private int aKW = Integer.MAX_VALUE;
    private int aKX = -1;
    private boolean aKY = false;
    private boolean mX = false;
    private boolean aKZ = false;
    private int aLa = 0;
    private final agj_1 aLb = new agj_1(0, 0);
    private final agj_1 aLc = new agj_1(0, 0);
    private final agj_1 aLd = new agj_1(0, 0);
    private boolean aLe = true;
    private boolean aLf = true;
    private boolean aLg = true;
    private Boolean aLh = false;
    private int aLi = 0;
    private int ajD = 0;
    private boolean aLj = true;
    private long aLk = 0L;
    private int aLl = 0;
    private int aLm = 0;

    public ch_2(jz jz2) {
        this.aCQ = jz2;
    }

    public void IV() {
        for (int j = this.aKU.size() - 1; j >= 0; --j) {
            ((aef_2)this.aKU.get(j)).release();
        }
        this.aKU.clear();
    }

    public void a(yt_1 yt_12) {
        this.aKO = yt_12;
    }

    public yt_1 IW() {
        return this.aKO;
    }

    public af_1 mH() {
        return this.aCQ == null ? null : this.aCQ.mH();
    }

    public void a(af_1 af_12) {
        if (this.aCQ.mH() != af_12) {
            this.aCQ.a(af_12);
            this.aLe = true;
            this.aLg = true;
            this.aKO.setNeedsToPreProcess();
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public boolean mI() {
        return this.aCQ.mI();
    }

    public void setUseHighContrast(boolean bl2) {
        this.aCQ.setUseHighContrast(bl2);
    }

    public Color IX() {
        return this.aKP;
    }

    public void e(vP vP2) {
        this.aKP = vP2 != null ? new Color(vP2.Cm(), vP2.Cn(), vP2.Co(), vP2.Cl()) : null;
    }

    public BP IY() {
        return this.aKR;
    }

    public void b(BP bP) {
        this.aKR = bP;
        this.aLe = true;
        this.aKO.setNeedsToMiddleProcess();
    }

    public BP IZ() {
        return this.aKS;
    }

    public void setVerticalAlignment(BP bP) {
        this.aKS = bP;
    }

    public aiq_0 getOrientation() {
        return this.aKT;
    }

    public void setOrientation(aiq_0 aiq_02) {
        if (aiq_02 != null) {
            this.aKT = aiq_02;
            this.aLg = true;
            this.aLe = true;
            this.aKO.setNeedsToPreProcess();
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public boolean Ja() {
        return this.aKQ;
    }

    public void setJustify(boolean bl2) {
        this.aKQ = bl2;
    }

    public Boolean Jb() {
        return this.aLh;
    }

    public void setAutoHorizontalScrolled(Boolean bl2) {
        this.aLh = bl2;
    }

    public void Jc() {
        this.aLl = 0;
    }

    public jz Fh() {
        return this.aCQ;
    }

    public String mJ() {
        if (this.aCQ != null) {
            return this.aCQ.mJ();
        }
        return null;
    }

    public void aD(String string) {
        if (this.aCQ != null) {
            this.aCQ.aD(string);
            this.aLg = true;
            this.aLe = true;
            this.aKO.setNeedsToPreProcess();
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public int Jd() {
        if (this.aCQ != null) {
            return this.aCQ.ms();
        }
        return 0;
    }

    public void aE(String string) {
        if (this.aCQ != null) {
            this.aCQ.aE(string);
            this.aLg = true;
            this.aLe = true;
            this.aKO.setNeedsToPreProcess();
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public void mq() {
        if (this.aCQ != null) {
            this.aCQ.mq();
            this.aLg = true;
            this.aLe = true;
            this.aKO.setNeedsToPreProcess();
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public void c(yb_0 yb_02, int n2) {
        if (this.aCQ != null) {
            this.aLf |= this.aCQ.a(yb_02, n2);
            if (this.aLf) {
                this.aKO.setNeedsToMiddleProcess();
            }
        }
    }

    public void Je() {
        if (this.aCQ != null) {
            this.aLf |= this.aCQ.mB();
            if (this.aLf) {
                this.aKO.setNeedsToMiddleProcess();
            }
        }
    }

    public void d(yb_0 yb_02, int n2) {
        if (this.aCQ != null) {
            this.aLf |= this.aCQ.b(yb_02, n2);
            if (this.aLf) {
                this.aKO.setNeedsToMiddleProcess();
            }
        }
    }

    public void Jf() {
        if (this.aCQ != null) {
            this.aLf |= this.aCQ.mF();
            if (this.aLf) {
                this.aKO.setNeedsToMiddleProcess();
            }
        }
    }

    public ArrayList Jg() {
        return this.aKU;
    }

    public int getMaxWidth() {
        return this.aKW;
    }

    public void setMaxWidth(int n2) {
        this.aKW = n2;
        this.aLg = true;
        this.aKO.setNeedsToPreProcess();
    }

    public int getMinWidth() {
        return this.aKX;
    }

    public void setMinWidth(int n2) {
        this.aKX = n2;
        this.aLg = true;
        this.aKO.setNeedsToPreProcess();
    }

    public boolean Jh() {
        return this.aKY;
    }

    public void setMultiline(boolean bl2) {
        this.aKY = bl2;
        this.aLg = true;
        this.aKO.setNeedsToPreProcess();
    }

    public boolean gs() {
        return this.mX || this.aKZ;
    }

    public void setSelectable(boolean bl2) {
        this.mX = bl2;
        this.aLg = true;
        this.aLf = true;
        this.aKO.setNeedsToPreProcess();
        this.aKO.setNeedsToMiddleProcess();
    }

    public boolean isEditable() {
        return this.aKZ;
    }

    public void setEditable(boolean bl2) {
        this.aKZ = bl2;
        this.aLg = true;
        this.aLf = true;
        this.aKO.setNeedsToPreProcess();
        this.aKO.setNeedsToMiddleProcess();
    }

    public boolean Ji() {
        return this.aLj;
    }

    public void setEnableShrinking(boolean bl2) {
        this.aLj = bl2;
    }

    public int Jj() {
        if (this.Jl()) {
            return this.aLa;
        }
        return 0;
    }

    public void eO(int n2) {
        this.aLa = Math.min(Math.max(0, n2), this.Jk());
    }

    public int Jk() {
        return this.aKU.size() - 1;
    }

    public boolean Jl() {
        return this.gs() && this.aKY;
    }

    public boolean Jm() {
        return this.gs() && !this.aKY;
    }

    public boolean Jn() {
        return this.aLj && (this.Jl() || this.Jm());
    }

    public agj_1 getSize() {
        return (agj_1)this.aLd.clone();
    }

    public void setSize(int n2, int n3) {
        n2 = Math.max(0, n2);
        n3 = Math.max(0, n3);
        if ((double)n2 != this.aLd.getWidth() || (double)n3 != this.aLd.getHeight()) {
            this.aLd.setSize(n2, n3);
            this.aLe = true;
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public int getOrientedWidth() {
        return this.aKT.isHorizontal() ? this.aLd.width : this.aLd.height;
    }

    public int getOrientedHeight() {
        return this.aKT.isHorizontal() ? this.aLd.height : this.aLd.width;
    }

    public agj_1 getMinSize() {
        return (agj_1)this.aLb.clone();
    }

    public agj_1 Jo() {
        return this.aLc.awl();
    }

    public boolean Jp() {
        return this.aLg;
    }

    public boolean Jq() {
        return this.aLe;
    }

    public boolean Jr() {
        return this.aLf;
    }

    public pf_0 ai(int n2, int n3) {
        pf_0 pf_02 = new pf_0(null, (Object)sy_0.ake);
        n3 = this.Js() - this.getOrientedHeight() - n3;
        int n4 = this.aKU.size();
        for (int j = 0; j < n4; ++j) {
            aef_2 aef_22 = (aef_2)this.aKU.get(j);
            if (!((double)n3 >= aef_22.getBounds().getMinY()) || !((double)n3 <= aef_22.getBounds().getMaxY())) continue;
            int n5 = aef_22.getX();
            if (aef_22.getBounds().contains(n2, n3)) {
                ArrayList arrayList = aef_22.aQO();
                for (int i2 = 0; i2 < arrayList.size(); ++i2) {
                    aFH aFH2 = (aFH)arrayList.get(i2);
                    int n6 = aFH2.getX() + n5;
                    if (n6 > n2 || n6 + aFH2.getWidth() < n2) continue;
                    pf_02.ac(aFH2);
                    return pf_02;
                }
            }
            if (n2 < n5) {
                pf_02.ac(aef_22.aQL());
                pf_02.ad((Object)sy_0.akd);
            } else {
                pf_02.ac(aef_22.aQM());
                pf_02.ad((Object)sy_0.akf);
            }
            return pf_02;
        }
        if (!this.aKU.isEmpty()) {
            pf_02.ac(((aef_2)this.aKU.get(this.aKU.size() - 1)).aQM());
            pf_02.ad((Object)sy_0.akg);
        }
        return pf_02;
    }

    public int Js() {
        int n2 = 0;
        if (!this.aKU.isEmpty()) {
            if (this.Jl()) {
                aef_2 aef_22 = (aef_2)this.aKU.get(this.Jj());
                if (aef_22 != null) {
                    n2 = aef_22.getY() + aef_22.getHeight();
                }
            } else {
                aef_2 aef_23 = (aef_2)this.aKU.get(this.aKU.size() - 1);
                if (this.aKS.IC()) {
                    n2 = this.getOrientedHeight() + aef_23.getY();
                } else if (!this.aKS.IB()) {
                    n2 = (this.getOrientedHeight() + aef_23.getY()) / 2;
                }
            }
        }
        return n2;
    }

    public void Jt() {
        if (this.aCQ != null) {
            this.aLf |= this.aCQ.mK();
            if (this.aLf) {
                this.aKO.setNeedsToMiddleProcess();
            }
        }
    }

    public void dy(String string) {
        if (this.aCQ != null) {
            this.aCQ.aF(string);
            this.aLe = true;
            this.aLg = true;
            this.aKO.setNeedsToPreProcess();
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public void mM() {
        if (this.aCQ != null) {
            this.aCQ.mM();
            this.aLe = true;
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public void mN() {
        if (this.aCQ != null) {
            this.aCQ.mN();
            this.aLe = true;
            this.aKO.setNeedsToMiddleProcess();
        }
    }

    public void Ju() {
        if (this.aCQ != null) {
            this.aLf |= this.aCQ.mO();
            if (this.aLf) {
                this.aKO.setNeedsToMiddleProcess();
            }
        }
    }

    public void Jv() {
        if (this.aCQ != null) {
            this.aLf |= this.aCQ.mP();
            if (this.aLf) {
                this.aKO.setNeedsToMiddleProcess();
            }
        }
    }

    public void Jw() {
        aFH aFH2;
        if (this.aKV != null && !this.aKV.isEmpty() && (aFH2 = this.aKV.aQL()) != null) {
            this.d(aFH2.De(), aFH2.getStartIndex());
        }
    }

    public void Jx() {
        if (this.aKV == null) {
            return;
        }
        if (this.aKV.isEmpty()) {
            return;
        }
        aFH aFH2 = this.aKV.aQM();
        if (aFH2 != null) {
            this.d(aFH2.De(), aFH2.getEndIndex());
        }
    }

    public void Jy() {
        double d = 0.0;
        double d2 = 0.0;
        if (!(this.aCQ.isEmpty() || this.Jn() || this.Jh())) {
            short s = 0;
            for (yb_0 yb_02 : this.aCQ) {
                switch (yb_02.Fg()) {
                    case mt: {
                        yb_0 yb_03 = (aoz_2)yb_02;
                        d += (double)((aoz_2)yb_03).getWidth();
                        d2 = Math.max(d2, (double)((aoz_2)yb_03).getHeight());
                        s = 0;
                        break;
                    }
                    case ms: {
                        yb_0 yb_03 = (adv_0)yb_02;
                        af_1 af_12 = ((adv_0)yb_03).Da();
                        if (af_12 == null) {
                            af_12 = this.mH();
                        }
                        if (af_12 == null) break;
                        String string = ((adv_0)yb_03).att();
                        if (string != null && string.length() != 0) {
                            int n2 = af_12.g(string);
                            int n3 = af_12.h(string);
                            d += (double)n2;
                            d2 = Math.max(d2, (double)n3);
                        } else {
                            d += (double)af_12.aB();
                            d2 = Math.max(d2, (double)af_12.aC());
                        }
                        s = af_12.getFont().qL();
                        break;
                    }
                }
            }
            d += (double)s;
        } else if (!this.aCQ.isEmpty() && !this.Jn() && this.Jh() && this.aKX > 0) {
            Point point = new Point(0, 0);
            boolean bl2 = false;
            aef_2 aef_22 = null;
            BP bP = this.aKR;
            int n4 = this.Jm() ? Integer.MAX_VALUE : (this.aKW != Integer.MAX_VALUE ? Math.max(this.aKX, this.aKW) : this.aKX);
            for (yb_0 yb_04 : this.aCQ) {
                BP bP2 = yb_04.Fi();
                if (bP2 == null) {
                    bP2 = this.aKR;
                }
                if (bP2 != bP) {
                    bP = bP2;
                    bl2 = true;
                }
                int n5 = 0;
                switch (yb_04.Fg()) {
                    case mt: {
                        int n6;
                        aoz_2 aoz_22 = (aoz_2)yb_04;
                        if (point.x != 0 && (n6 = n4 - point.x) < aoz_22.getWidth()) {
                            bl2 = true;
                        }
                        if (bl2 || aef_22 == null) {
                            if (aef_22 != null) {
                                point.x = 0;
                                point.y -= aef_22.getHeight();
                                d2 += (double)aef_22.getHeight();
                                d = Math.max((double)point.x, d);
                                aef_22.release();
                            }
                            aef_22 = aef_2.aQF();
                            aef_22.a(bP);
                            bl2 = false;
                        }
                        aef_22.setHeight(Math.max(aoz_22.getHeight(), aef_22.getHeight()));
                        aef_22.a(aoz_22, point.x);
                        point.x += aoz_22.getWidth();
                        break;
                    }
                    case ms: {
                        adv_0 adv_02 = (adv_0)yb_04;
                        af_1 af_13 = adv_02.Da();
                        if (af_13 == null) {
                            af_13 = this.mH();
                        }
                        String string = adv_02.att();
                        if (af_13 == null || string == null) break;
                        int n7 = 0;
                        while (n7 != -1) {
                            int n8;
                            int n9;
                            String string2;
                            int n10 = n7;
                            String string3 = string2 = (n7 = string.indexOf(aKN, n7 + 1)) == -1 ? string.substring(n10) : string.substring(n10, n7);
                            if (point.x != 0 && string2.length() != 0 && (n9 = af_13.a(string2, n8 = n4 - point.x, false)) == 0) {
                                bl2 = true;
                            }
                            boolean bl3 = bl2 = bl2 || n5 != 0 || aef_22 == null || string2.startsWith(aKN);
                            if (string2.length() == 0) {
                                if (bl2) {
                                    if (aef_22 != null) {
                                        point.x = 0;
                                        point.y -= aef_22.getHeight();
                                        d = Math.max((double)point.x, d);
                                        d2 += (double)aef_22.getHeight();
                                    }
                                    if (aef_22 != null) {
                                        aef_22.release();
                                    }
                                    aef_22 = aef_2.aQF();
                                    aef_22.a(bP);
                                    bl2 = false;
                                }
                                aef_22.setHeight(Math.max(af_13.aC(), aef_22.getHeight()));
                                aef_22.a("", adv_02, n5, n5, point.x, 0);
                                continue;
                            }
                            n8 = 0;
                            while (n8 < string2.length()) {
                                if (bl2) {
                                    if (aef_22 != null) {
                                        d = Math.max((double)point.x, d);
                                        point.y -= aef_22.getHeight();
                                        d2 += (double)aef_22.getHeight();
                                    }
                                    point.x = af_13.getFont().qL();
                                    if (aef_22 != null) {
                                        aef_22.release();
                                    }
                                    aef_22 = aef_2.aQF();
                                    aef_22.a(bP);
                                    bl2 = false;
                                }
                                if ((n9 = n4 - point.x) < 0) {
                                    n9 = Integer.MAX_VALUE;
                                }
                                int n11 = n8 + af_13.a(string2.substring(n8), n9);
                                String string4 = string2.substring(n8, n11);
                                int n12 = af_13.g(string4);
                                int n13 = af_13.h(string4);
                                aef_22.setHeight(Math.max(n13, aef_22.getHeight()));
                                aef_22.ny(Math.max(aef_22.aQJ(), af_13.i(string4)));
                                aef_22.a(string4, adv_02, n5, n5 + string4.length(), point.x, n12);
                                n5 += string4.length();
                                n8 = n11;
                                if (n8 != string2.length()) {
                                    bl2 = true;
                                    d = Math.max(d, (double)(point.x + n12));
                                    continue;
                                }
                                point.x += n12;
                            }
                        }
                        break;
                    }
                }
            }
            if (!bl2 && aef_22 != null) {
                d2 += (double)aef_22.getHeight();
                d = Math.max((double)point.x, d);
            }
        } else if (!this.aCQ.isEmpty() && !this.Jn() && this.Jh() && this.aKX <= 0) {
            Point point = new Point(0, 0);
            boolean bl4 = false;
            aef_2 aef_23 = null;
            BP bP = this.aKR;
            int n14 = 0;
            for (yb_0 yb_05 : this.aCQ) {
                BP bP3 = yb_05.Fi();
                if (bP3 == null) {
                    bP3 = this.aKR;
                }
                if (bP3 != bP) {
                    bP = bP3;
                    bl4 = true;
                }
                int n15 = 0;
                switch (yb_05.Fg()) {
                    case mt: {
                        aoz_2 aoz_23 = (aoz_2)yb_05;
                        if (bl4 || aef_23 == null) {
                            if (aef_23 != null) {
                                point.x = 0;
                                point.y -= aef_23.getHeight();
                                d2 += (double)aef_23.getHeight();
                            }
                            if (aef_23 != null) {
                                aef_23.release();
                            }
                            aef_23 = aef_2.aQF();
                            aef_23.a(bP);
                            bl4 = false;
                        }
                        aef_23.setHeight(Math.max(aoz_23.getHeight(), aef_23.getHeight()));
                        aef_23.a(aoz_23, point.x);
                        point.x += aoz_23.getWidth();
                        n14 = 0;
                        break;
                    }
                    case ms: {
                        adv_0 adv_03 = (adv_0)yb_05;
                        af_1 af_14 = adv_03.Da();
                        if (af_14 == null) {
                            af_14 = this.mH();
                        }
                        n14 = af_14.getFont().qL();
                        String string = adv_03.att();
                        if (af_14 == null || string == null) break;
                        int n16 = 0;
                        while (n16 != -1) {
                            int n17 = n16;
                            String string5 = (n16 = string.indexOf(aKN, n16 + 1)) == -1 ? string.substring(n17) : string.substring(n17, n16);
                            boolean bl5 = bl4 = bl4 || n15 != 0 || aef_23 == null || string5.startsWith(aKN);
                            if (string5.length() == 0) {
                                if (bl4) {
                                    if (aef_23 != null) {
                                        d = Math.max((double)(point.x + n14), d);
                                        point.x = 0;
                                        point.y -= aef_23.getHeight();
                                        d2 += (double)aef_23.getHeight();
                                    }
                                    if (aef_23 != null) {
                                        aef_23.release();
                                    }
                                    aef_23 = aef_2.aQF();
                                    aef_23.a(bP);
                                    bl4 = false;
                                }
                                aef_23.setHeight(Math.max(af_14.aC(), aef_23.getHeight()));
                                aef_23.a("", adv_03, n15, n15, point.x, 0);
                                continue;
                            }
                            if (bl4) {
                                if (aef_23 != null) {
                                    d = Math.max((double)(point.x + n14), d);
                                    point.x = 0;
                                    point.y -= aef_23.getHeight();
                                    d2 += (double)aef_23.getHeight();
                                }
                                if (aef_23 != null) {
                                    aef_23.release();
                                }
                                aef_23 = aef_2.aQF();
                                aef_23.a(bP);
                            }
                            aef_23.setHeight(Math.max(af_14.h(string5), aef_23.getHeight()));
                            point.x += af_14.g(string5);
                            aef_23.ny(Math.max(aef_23.aQJ(), af_14.i(string5)));
                            aef_23.a(string5, adv_03, n15, n15 + string5.length(), point.x, af_14.g(string5));
                            n15 += string5.length();
                            bl4 = n16 != -1;
                        }
                        break;
                    }
                }
            }
            if (aef_23 != null) {
                d = Math.max(d, (double)(point.x + n14));
                d2 += (double)aef_23.getHeight();
                point.x = 0;
            }
        } else {
            af_1 af_15 = this.mH();
            if (af_15 != null) {
                d = af_15.aB();
                d2 = af_15.aC();
            }
        }
        if (this.aKT.isHorizontal()) {
            this.aLc.setSize(d, d2);
            this.aLb.setSize(Math.max(Math.min((double)this.aKW, d), (double)this.aKX), d2);
        } else {
            this.aLc.setSize(d2, d);
            this.aLb.setSize(d2, Math.max(Math.min((double)this.aKW, d), (double)this.aKX));
        }
        this.aLg = false;
    }

    public void ba(boolean bl2) {
        Object object;
        int n2;
        this.IV();
        Point point = new Point(0, 0);
        boolean bl3 = false;
        boolean bl4 = false;
        aef_2 aef_22 = null;
        BP bP = this.aKR;
        int n3 = this.Jm() ? Integer.MAX_VALUE : this.getOrientedWidth();
        for (Object object2 : this.aCQ) {
            BP bP2 = ((yb_0)object2).Fi();
            if (bP2 == null) {
                bP2 = this.aKR;
            }
            if (bP2 != bP) {
                bP = bP2;
                bl3 = true;
                bl4 = false;
            }
            n2 = 0;
            switch (((yb_0)object2).Fg()) {
                case mt: {
                    int n4;
                    object = (aoz_2)object2;
                    if (point.x != 0 && (n4 = this.getOrientedWidth() - point.x) < ((aoz_2)object).getWidth()) {
                        bl3 = true;
                        bl4 = true;
                    }
                    if (bl3 || aef_22 == null) {
                        aef_2 aef_23 = this.a(aef_22, bl4, point, 0, bP);
                        if (aef_23 == null) {
                            return;
                        }
                        aef_22 = aef_23;
                        bl3 = false;
                    }
                    aef_22.setHeight(Math.max(((aoz_2)object).getHeight(), aef_22.getHeight()));
                    aef_22.a((aoz_2)object, point.x);
                    point.x += ((aoz_2)object).getWidth();
                    break;
                }
                case ms: {
                    adv_0 adv_02 = (adv_0)object2;
                    af_1 af_12 = adv_02.Da();
                    if (af_12 == null) {
                        af_12 = this.mH();
                    }
                    String string = adv_02.att();
                    if (af_12 == null || string == null) break;
                    int n5 = 0;
                    while (n5 != -1) {
                        int n6;
                        int n7;
                        String string2;
                        int n8 = n5;
                        String string3 = string2 = (n5 = string.indexOf(aKN, n5 + 1)) == -1 ? string.substring(n8) : string.substring(n8, n5);
                        if (point.x != 0 && string2.length() != 0 && (n7 = af_12.a(string2, n6 = n3 - point.x, false)) == 0) {
                            bl3 = true;
                        }
                        bl3 = bl3 || n2 != 0 || aef_22 == null || string2.startsWith(aKN);
                        boolean bl5 = bl4 = bl3 && !string2.startsWith(aKN);
                        if (string2.length() == 0) {
                            if (bl3) {
                                aef_2 aef_24 = this.a(aef_22, bl4, point, af_12.getFont().qL(), bP);
                                if (aef_24 == null) {
                                    return;
                                }
                                point.y -= aef_24.getHeight();
                                aef_22 = aef_24;
                                bl3 = false;
                            }
                            aef_22.setHeight(Math.max(af_12.aC(), aef_22.getHeight()));
                            aef_22.a("", adv_02, n2, n2, point.x, 0);
                            continue;
                        }
                        n6 = 0;
                        while (n6 < string2.length()) {
                            if (bl3) {
                                aef_2 aef_25 = this.a(aef_22, bl4, point, af_12.getFont().qL(), bP);
                                if (aef_25 == null) {
                                    return;
                                }
                                aef_22 = aef_25;
                                bl3 = false;
                            }
                            if ((n7 = n3 - point.x) < 0) {
                                n7 = Integer.MAX_VALUE;
                            }
                            int n9 = n6 + af_12.a(string2.substring(n6), n7);
                            int n10 = af_12.g(string2.substring(n6, n9)) - af_12.getFont().qL();
                            String string4 = string2.substring(n6, n9);
                            int n11 = af_12.h(string4);
                            aef_22.setHeight(Math.max(n11, aef_22.getHeight()));
                            aef_22.ny(Math.max(aef_22.aQJ(), af_12.i(string4)));
                            if (this.aLh.booleanValue()) {
                                if (this.aLi != 0) {
                                    point.x = this.ajD > 0 && this.aLi > 0 || this.ajD < 0 && this.aLi < 0 ? this.aLi : this.ajD + this.aLi;
                                    this.ajD = point.x;
                                }
                                this.aLi = 0;
                            }
                            aef_22.a(string4, adv_02, n2, n2 + string4.length(), point.x, n10);
                            n2 += string4.length();
                            n6 = n9;
                            if (n6 != string2.length()) {
                                bl3 = true;
                                bl4 = true;
                                continue;
                            }
                            point.x += n10;
                        }
                    }
                    break;
                }
            }
        }
        if (!bl3 && aef_22 != null) {
            aef_22.setY(point.y - aef_22.getHeight());
            aef_22.setX(aef_22.Fi().ag(aef_22.getWidth(), this.getOrientedWidth()));
            this.aKU.add(aef_22);
            aef_22 = null;
        }
        if (aef_22 != null) {
            aef_22.release();
        }
        if (this.aLh.booleanValue()) {
            Object object2;
            nm_0 nm_02 = nm_0.sl();
            object2 = nm_0.sl();
            n2 = this.aKU.size();
            for (int j = 0; j < n2; ++j) {
                object = ((aef_2)this.aKU.get(j)).getBounds();
                ((nm_0)object2).setBounds(((Rectangle)object).x, ((Rectangle)object).y, ((Rectangle)object).width, ((Rectangle)object).height);
                nm_02.b((nm_0)object2);
            }
            if (nm_02.getX() >= 0 && nm_02.getX() + nm_02.getWidth() <= this.getOrientedWidth()) {
                this.ajD = 0;
                this.aLi = 0;
            }
            nm_02.release();
            ((nm_0)object2).release();
        }
        this.bb(bl2);
        this.aLe = false;
    }

    public void bb(boolean bl2) {
        if (this.gs()) {
            this.aKV = null;
            yb_0 yb_02 = this.aCQ.my();
            yb_0 yb_03 = this.aCQ.mC();
            int n2 = this.aCQ.mA();
            int n3 = this.aCQ.mE();
            boolean bl3 = this.isEditable() && this.aCQ.isSelectionEmpty();
            boolean bl4 = yb_02 != null && yb_03 != null;
            boolean bl5 = false;
            for (aef_2 aef_22 : this.aKU) {
                aef_22.aQH();
                if (!bl4) continue;
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                ArrayList arrayList = aef_22.aQO();
                for (int j = 0; j < arrayList.size(); ++j) {
                    aFH aFH2 = (aFH)arrayList.get(j);
                    boolean bl6 = false;
                    if (this.aKV == null && !bl5 && aFH2.De() == yb_02 && aFH2.getStartIndex() <= n2 && aFH2.getEndIndex() >= n2) {
                        bl5 = true;
                        bl6 = true;
                        n4 = n3 >= this.aLm ? aFH2.b(this.mH(), n2 - aFH2.getStartIndex()) : aFH2.getX() + aFH2.b(this.mH(), n2 - aFH2.getStartIndex());
                    }
                    if (this.aKV == null && bl5 && aFH2.De() == yb_03 && aFH2.getStartIndex() <= n3 && aFH2.getEndIndex() >= n3) {
                        bl5 = false;
                        n6 = bl3 ? 1 : aFH2.getX() + aFH2.c(this.mH(), n3 - aFH2.getStartIndex()) - n4;
                        this.aKV = aef_22;
                    }
                    if (!bl5) continue;
                    if (!bl6) {
                        n6 += aFH2.getWidth();
                        continue;
                    }
                    n6 += aFH2.getWidth() - n4 + aFH2.getX();
                }
                if (n6 == 0) continue;
                if (this.aLh.booleanValue() && bl2) {
                    n5 = n4 + n6 - 1;
                    if (n4 < this.aKO.getAppearance().getLeftInset() && this.ajD != 0) {
                        this.aLi = -n4;
                    } else if (n5 >= this.aKO.getAppearance().getContentWidth() - this.aKO.getAppearance().getRightInset() && this.aLl < n5) {
                        this.aLi = this.aKO.getAppearance().getContentWidth() - this.aKO.getAppearance().getRightInset() - n5;
                    }
                }
                aef_22.f(n4, n6, bl3);
                if (this.aLi == 0) continue;
                this.aLl = n5;
                this.aLm = n3;
                this.ba(false);
            }
        }
        this.aLf = false;
    }

    private aef_2 a(aef_2 aef_22, boolean bl2, Point point, int n2, BP bP) {
        point.x = n2;
        if (aef_22 != null) {
            boolean bl3;
            point.y -= aef_22.getHeight();
            aef_22.setY(point.y);
            if (this.aKQ && this.aKY && bl2) {
                Object object;
                wC wC2;
                Object object2;
                aFH aFH2 = aef_22.aQM();
                if (aFH2 != null && aFH2.aRY() == nf_2.NN && (object2 = (wC2 = (wC)aFH2).getText()) != null && ((String)object2).endsWith(" ")) {
                    wC2.setText(((String)object2).substring(0, ((String)object2).length() - 1));
                    int n3 = aef_22.getWidth();
                    int n4 = wC2.getWidth();
                    object = wC2.Da();
                    if (object == null) {
                        object = this.mH();
                    }
                    String string = wC2.getText();
                    int n5 = ((af_1)object).g(string);
                    wC2.setWidth(((af_1)object).g(string));
                    aef_22.aQN();
                    int n6 = aef_22.getWidth();
                    boolean bl4 = false;
                }
                int n7 = 0;
                for (aFH aFH3 : aef_22.aQO()) {
                    wC wC3;
                    if (aFH3.aRY() != nf_2.NN || (object = (wC3 = (wC)aFH3).CY()) == null) continue;
                    int n8 = aey_0.a(' ', (char[])object);
                    wC3.ec(n8);
                    n7 += n8;
                }
                aef_22.ec(n7);
            }
            aef_22.setX(aef_22.Fi().ag(aef_22.getWidth(), this.getOrientedWidth()));
            boolean bl5 = bl3 = -point.y <= this.getOrientedHeight();
            if (bl3 || this.Jl()) {
                this.aKU.add(aef_22);
            } else {
                aef_22.release();
            }
            if (!this.aKY || !this.Jl() && !bl3) {
                this.Jz();
                this.aLe = false;
                return null;
            }
        }
        aef_22 = aef_2.aQF();
        aef_22.a(bP);
        return aef_22;
    }

    private void Jz() {
        if (this.aKU.isEmpty()) {
            return;
        }
        aef_2 aef_22 = (aef_2)this.aKU.get(this.aKU.size() - 1);
        if (aef_22 != null) {
            int n2 = aef_22.aQK();
            while (n2 > 0) {
                aFH aFH2;
                if ((aFH2 = aef_22.nz(--n2)).aRY() == nf_2.NN) {
                    wC wC2 = (wC)aFH2;
                    af_1 af_12 = wC2.Da();
                    if (af_12 == null) {
                        af_12 = this.mH();
                    }
                    if (af_12 != null) {
                        int n3 = af_12.g(aKM);
                        int n4 = this.getOrientedWidth() - wC2.getX();
                        if (n4 >= n3) {
                            int n5;
                            int n6;
                            int n7;
                            char[] cArray = wC2.getText().toCharArray();
                            int n8 = 0;
                            for (n7 = 0; n7 < cArray.length && n8 + (n6 = af_12.a(cArray[n7])) <= n4 - n3; ++n7) {
                                n8 += n6;
                            }
                            n6 = n7;
                            String string = wC2.getText().substring(0, n6);
                            int n9 = af_12.g(string);
                            if (n4 - n9 + (n5 = 5) >= n3) {
                                wC2.setText(string);
                                wC2.setWidth(n9);
                                aef_22.aQN();
                                aef_22.setX(aef_22.Fi().ag(aef_22.getWidth(), this.getOrientedWidth()));
                                wC wC3 = aef_22.a(aKM, null, 0, 0, wC2.getX() + n9, n3);
                                wC3.b(af_12);
                                wC3.setColor(wC2.getColor());
                                wC3.a(wC2.Fi());
                                wC3.setUnderline(wC2.isUnderline());
                                wC3.aR(wC2.Dd());
                                return;
                            }
                        }
                    }
                }
                aef_22.nx(n2);
            }
        }
    }

    public Iterator iterator() {
        return this.aKU.iterator();
    }

    public void clean() {
        this.aKQ = false;
        this.aKP = Color.BLACK;
        this.aKR = BP.aJA;
        this.aKS = BP.aJx;
        this.aKT = aiq_0.cxW;
        this.aKO = null;
    }

    public yt_1 JA() {
        return this.aKO;
    }

    public void bg(long l2) {
        this.aLk = l2;
    }

    public long JB() {
        return this.aLk;
    }
}

