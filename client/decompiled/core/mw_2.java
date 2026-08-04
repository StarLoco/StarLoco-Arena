/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from mW
 */
public class mw_2
extends ZT {
    private static final acl_0 aU = new ym_0(new ahx_2());
    private short Ny = (short)18;
    private short Nz = (short)5;

    public mw_2() {
        this.aG();
    }

    public mw_2 rT() {
        mw_2 mw_22;
        try {
            mw_22 = (mw_2)aU.adr();
            mw_22.uG = aU;
        }
        catch (Exception exception) {
            mw_22 = new mw_2();
            mw_22.uG = null;
            a.error((Object)("Erreur lors d'un newInstance sur un MapDestruction : " + exception.getMessage()));
        }
        return mw_22;
    }

    public static mw_2 a(ea_0 ea_02, ry ry2, short s, short s2) {
        mw_2 mw_22;
        try {
            mw_22 = (mw_2)aU.adr();
            mw_22.uG = aU;
        }
        catch (Exception exception) {
            mw_22 = new mw_2();
            mw_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un MapDestruction : " + exception.getMessage()));
        }
        mw_22.aW = mh_2.bwn.getId();
        mw_22.bWr = ((ZT)mh_2.bwn.getObject()).Oz();
        mw_22.aG();
        mw_22.bWn.g(ry2);
        mw_22.ahI = -1;
        mw_22.r = 0;
        mw_22.bdv = ea_02;
        mw_22.Ny = s;
        mw_22.Nz = s2;
        return mw_22;
    }

    public void aG() {
        super.aG();
    }

    public void a(xb_2 xb_22, boolean bl2) {
        int n2;
        Object object;
        Object object2;
        Object object3;
        ArrayList<int[]> arrayList = new ArrayList<int[]>();
        cp_2 cp_22 = new cp_2();
        if (this.akf()) {
            object3 = this.bdv.gT().agn();
            while (object3.hasNext()) {
                object2 = (kc_2)object3.next();
                if (!(object2 instanceof gn_0)) continue;
                long l2 = ((long)object2.gn() << 32) + (long)object2.go();
                object = new ArrayList();
                if (((gn_0)object2).rD()) {
                    ((ArrayList)object).add(((gn_0)object2).PZ());
                }
                ((ArrayList)object).add(object2);
                if (((gn_0)object2).Qa()) {
                    ((ArrayList)object).add(((gn_0)object2).PY());
                }
                cp_22.a(l2, object);
            }
        }
        object3 = new in_0();
        object2 = new int[]{this.bWn.getX(), this.bWn.getY()};
        int n3 = 0;
        for (n2 = 0; n2 < this.Ny * this.Ny; ++n2) {
            object = ((in_0)object3).Ug();
            Object object4 = object2;
            object4[0] = object4[0] + object[0];
            Object object5 = object2;
            object5[1] = object5[1] + object[1];
            arrayList.add(0, new int[]{(int)object2[0], (int)object2[1]});
            if (!this.akf() || n2 >= this.Nz * this.Nz) continue;
            ++n3;
        }
        n2 = !this.akf() ? this.r : arrayList.size() - n3;
        if (this.bdv instanceof adt_2) {
            object = (adt_2)this.bdv;
            for (int j = 0; j < n2; ++j) {
                long l3;
                ArrayList arrayList2;
                if (this.akf()) {
                    ++this.r;
                }
                boolean bl3 = false;
                afj_0 afj_02 = ((adt_2)object).aPN();
                if (afj_02 != null) {
                    gk_1 gk_12 = afj_02.aRJ();
                    while (gk_12.hasNext()) {
                        gk_12.fK();
                        if (((ry)gk_12.value()).getX() != ((int[])arrayList.get(j))[0] || ((ry)gk_12.value()).getY() != ((int[])arrayList.get(j))[1]) continue;
                        bl3 = true;
                    }
                }
                if (!bl3 && this.bdv != null) {
                    ((adt_2)object).bV(((int[])arrayList.get(j))[0], ((int[])arrayList.get(j))[1]);
                }
                if ((arrayList2 = (ArrayList)cp_22.t(l3 = ((long)((int[])arrayList.get(j))[0] << 32) + (long)((int[])arrayList.get(j))[1])) == null) continue;
                this.b(xb_22, bl2);
                for (kc_2 kc_22 : arrayList2) {
                    if (kc_22.PR() || !kc_22.Qg()) continue;
                    kc_22.bt(true);
                    kc_22.b(this.bWl);
                    kc_22.bt(false);
                }
            }
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.r = 0;
        switch (((xj_0)this.bWj).Tb().length) {
            case 2: {
                this.Ny = (short)((xj_0)this.bWj).Tb()[0];
                this.Nz = (short)((xj_0)this.bWj).Tb()[1];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect pour une destruction de map : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return false;
    }
}

