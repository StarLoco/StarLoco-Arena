/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from LP
 */
public abstract class lp_1 {
    protected boolean bsk;
    protected final ahz_0 bsl;
    protected boolean bsm = false;
    protected final ahz_0 bsn;
    protected final ahz_0 bso;
    protected final su_1 bsp = new ev_2();
    protected ee_2 bN;
    protected mv_1 bsq;
    protected final List bsr = new ArrayList();

    public lp_1(String string, float[] fArray, String string2, float[] fArray2, String string3, float[] fArray3) {
        this.bsn = new ahz_0(string, fArray);
        this.bso = new ahz_0(string3, fArray3);
        this.bsl = new ahz_0(string2, fArray2);
        this.bsn.oK(0);
        this.bso.oK(1);
        this.bsl.oK(2);
    }

    public void Yb() {
        this.Yc();
        this.bsn.clear();
        this.bso.clear();
    }

    public void Yc() {
        this.Yd();
        this.bsl.clear();
    }

    public void a(Pi pi, ry ry2) {
        if (!this.bsm) {
            this.Yd();
            this.bsl.clear();
        }
        if (pi != null) {
            qc_0 qc_02 = aby_2.E(ry2.getX() - this.bN.gg().getX(), ry2.getY() - this.bN.gg().getY());
            for (XV xV : pi) {
                Object object;
                agf_2 agf_22 = xV.alM();
                if (agf_22.fj() == zg_1.cdF) {
                    this.bsl.y(ry2.getX(), ry2.getY(), ry2.wk());
                    continue;
                }
                List list = agf_22.fg();
                ArrayList arrayList = Uj.a(ry2.getX(), ry2.getY(), qc_02, this.bsp, list);
                int n2 = arrayList.size();
                aoq_0 aoq_02 = this.bsq.gV();
                for (int j = 0; j < n2; ++j) {
                    DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)arrayList.get(j);
                    Object object2 = displayedScreenElement.atV();
                    object = ((ScreenElement)object2).avX();
                    if (!aoq_02.F(((ry)object).getX(), ((ry)object).getY()) || aoq_02.bL(((ry)object).getX(), ((ry)object).getY()) != ((ry)object).wk()) continue;
                    this.bsl.y(((ry)object).getX(), ((ry)object).getY(), ((ry)object).wk());
                }
                List list2 = ((ZT)mh_2.YJ().cr(xV.M())).a((xj_0)xV, (aOf)this.bN, this.bsq.Np(), ry2.getX(), ry2.getY(), ry2.wk());
                for (Object object2 : list2) {
                    object = object2.iterator();
                    while (object.hasNext()) {
                        kc_2 kc_22 = (kc_2)object.next();
                        if (!(kc_22 instanceof ee_2)) continue;
                        this.bsr.add((ee_2)kc_22);
                        ((ee_2)kc_22).NW().BX();
                    }
                }
            }
        } else {
            this.bsl.y(ry2.getX(), ry2.getY(), ry2.wk());
        }
        this.bsm = false;
    }

    public final boolean p(ry ry2) {
        return this.bsn.i(ry2) || this.bso.i(ry2);
    }

    protected void d(ee_2 ee_22) {
        this.bN = ee_22;
        this.bsq = ee_22.Oc();
        aoq_0 aoq_02 = this.bsq.gV();
        this.Yb();
        int n2 = aoq_02.getMinX();
        int n3 = aoq_02.getMinY();
        int n4 = aoq_02.getWidth();
        int n5 = n2 + n4;
        int n6 = n3 + aoq_02.getHeight();
        ry ry2 = new ry();
        for (int j = n2; j < n5; ++j) {
            block5: for (int i2 = n3; i2 <= n6; ++i2) {
                short s;
                if (!aoq_02.bG(j, i2) || (s = aoq_02.bL(j, i2)) == Short.MIN_VALUE) continue;
                ry2.l(j, i2, s);
                switch (this.l(ry2)) {
                    case cbK: {
                        if (this.bsn == null) continue block5;
                        this.bsn.y(j, i2, s);
                        continue block5;
                    }
                    case cbL: {
                        if (this.bso == null) continue block5;
                        this.bso.y(j, i2, s);
                    }
                }
            }
        }
        this.bN = null;
        this.bsq = null;
    }

    protected abstract YT l(ry var1);

    public void b(ee_2 ee_22) {
        this.bN = ee_22;
    }

    public void b(mv_1 mv_12) {
        this.bsq = mv_12;
    }

    public final void Yd() {
        for (ee_2 ee_22 : this.bsr) {
            ee_22.NW().BY();
        }
        this.bsr.clear();
    }
}

