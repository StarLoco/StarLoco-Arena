/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from LB
 */
public class lb_2 {
    private static Logger a = Logger.getLogger(lb_2.class);
    private static lb_2 brH = new lb_2();
    private adg_2 brI;
    private final HashMap brJ = new HashMap();

    private lb_2() {
    }

    public void e(adg_2 adg_22) {
        aht_1 aht_12 = adg_22.getRootFocusParent();
        if (aht_12 != null) {
            ArrayList<adg_2> arrayList = (ArrayList<adg_2>)this.brJ.get(aht_12);
            if (arrayList == null) {
                arrayList = new ArrayList<adg_2>();
                this.brJ.put(aht_12, arrayList);
            }
            if (!arrayList.contains(adg_22)) {
                arrayList.add(adg_22);
            }
        }
    }

    public void f(adg_2 adg_22) {
        aht_1 aht_12;
        if (this.brI == adg_22) {
            this.XP();
        }
        if (this.brI == adg_22) {
            this.brI = null;
        }
        if ((aht_12 = adg_22.getRootFocusParent()) != null) {
            ArrayList arrayList = (ArrayList)this.brJ.get(aht_12);
            this.a(adg_22, aht_12, arrayList);
        } else {
            for (ArrayList arrayList : this.brJ.values()) {
                this.a(adg_22, aht_12, arrayList);
            }
        }
    }

    private void a(adg_2 adg_22, aht_1 aht_12, ArrayList arrayList) {
        if (arrayList != null) {
            arrayList.remove(adg_22);
            if (arrayList.isEmpty()) {
                this.brJ.remove(aht_12);
            }
        }
    }

    public void c(aht_1 aht_12) {
        this.brJ.remove(aht_12);
    }

    public static final lb_2 XL() {
        return brH;
    }

    public void g(adg_2 adg_22) {
        Vz vz;
        if (adg_22 == this.brI) {
            return;
        }
        adg_2 adg_23 = this.brI;
        this.brI = adg_22;
        if (adg_23 != null && adg_23.getFocusable()) {
            vz = new Vz(adg_23, false);
            adg_23.f(vz);
        }
        if (adg_22 != null && adg_22.getFocusable()) {
            vz = new Vz(adg_22, true);
            adg_22.f(vz);
        }
    }

    public adg_2 XM() {
        return this.brI;
    }

    public void XN() {
        if (this.brI != null) {
            adg_2 adg_22;
            for (adg_22 = this.brI; adg_22 != null && adg_22 != ago_2.getInstance() && adg_22.getModalLevel() == -1; adg_22 = adg_22.getContainer()) {
            }
            if (adg_22 != null && adg_22.getModalLevel() < amY.aBW().aBX()) {
                this.g(null);
            }
        }
    }

    public void XO() {
        this.bO(false);
    }

    public void XP() {
        this.bO(true);
    }

    private void bO(boolean bl2) {
        adg_2 adg_22 = null;
        if (this.brI == null) {
            for (ArrayList arrayList : this.brJ.values()) {
                if (arrayList.isEmpty()) continue;
                adg_22 = (adg_2)arrayList.get(0);
                break;
            }
        } else {
            ArrayList arrayList;
            aht_1 aht_12 = this.brI.getRootFocusParent();
            if (aht_12 != null && (arrayList = (ArrayList)this.brJ.get(aht_12)) != null && !arrayList.isEmpty()) {
                int n2 = arrayList.indexOf(this.brI);
                int n3 = arrayList.size() - 1;
                if (bl2 && n2 == n3) {
                    adg_22 = (adg_2)arrayList.get(0);
                } else if (!bl2 && n2 == 0) {
                    adg_22 = (adg_2)arrayList.get(n3);
                } else if (n2 >= 0 && n2 <= n3) {
                    adg_22 = (adg_2)arrayList.get(n2 + (bl2 ? 1 : -1));
                } else {
                    a.error((Object)("m_focused (" + this.brI + ") n'est pas enregistr\u00e9 dans les listes de widgets Focusables"));
                }
            }
        }
        this.g(adg_22);
    }
}

