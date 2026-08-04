/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import java.util.ArrayList;

/*
 * Renamed from lc
 */
public class lc_1 {
    public static final lc_1 Gf = new ahe_0();
    private final ArrayList Gg = new ArrayList();
    private final ArrayList Gh = new ArrayList();
    private final lb_0 Gi = new lb_0();

    public static lc_1 b(lc_1 lc_12) {
        if (lc_12 == Gf) {
            return Gf;
        }
        return lc_12.pQ();
    }

    public boolean pM() {
        return !this.Gi.isEmpty();
    }

    public boolean pN() {
        return !this.Gh.isEmpty();
    }

    public afd_2 bZ(int n2) {
        return (afd_2)this.Gi.get(n2);
    }

    public ll_0 pO() {
        return this.Gi.pK();
    }

    public void clear() {
        for (int j = this.Gg.size() - 1; j >= 0; --j) {
            this.c((Anm)this.Gg.get(j));
        }
        this.Gg.clear();
        this.Gh.clear();
        this.Gi.clear();
    }

    boolean a(Anm anm, aBp aBp2) {
        if (anm == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/PartsHelper.setPartsFrom must not be null");
        }
        if (aBp2 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/PartsHelper.setPartsFrom must not be null");
        }
        this.b(anm);
        boolean bl2 = false;
        if (anm.is()) {
            for (int j = 0; j < anm.qA.length; ++j) {
                ju_2 ju_22 = anm.qA[j];
                int n2 = ju_22.CX;
                if (n2 == 0 || !aBp2.contains(ju_22.CY)) continue;
                this.Gi.c(n2, new afd_2(n2, anm, ju_22));
                bl2 = true;
                this.ca(ju_22.CY);
            }
        } else {
            qk qk2 = aBp2.aNm();
            while (qk2.hasNext()) {
                this.Gh.add(new afd_2(qk2.next(), anm, null));
            }
        }
        return bl2;
    }

    private void ca(int n2) {
        for (int j = this.Gh.size() - 1; j >= 0; --j) {
            if (((afd_2)this.Gh.get((int)j)).asw != n2) continue;
            this.Gh.remove(j);
        }
    }

    boolean b(Anm anm, aBp aBp2) {
        boolean bl2 = false;
        ll_0 ll_02 = this.Gi.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            afd_2 afd_22 = (afd_2)ll_02.value();
            if (!aBp2.contains(afd_22.dFa.CY) || afd_22.dEZ != anm) continue;
            ll_02.remove();
            bl2 = true;
        }
        int n2 = 0;
        while (n2 < this.Gh.size()) {
            afd_2 afd_23 = (afd_2)this.Gh.get(n2);
            if (aBp2.contains(afd_23.asw) && afd_23.dEZ == anm) {
                this.Gh.remove(n2);
                bl2 = true;
                continue;
            }
            ++n2;
        }
        if (bl2) {
            return this.pP();
        }
        return false;
    }

    boolean fZ() {
        boolean bl2 = false;
        int n2 = 0;
        while (n2 < this.Gh.size()) {
            afd_2 afd_22 = (afd_2)this.Gh.get(n2);
            int n3 = afd_22.asw;
            Anm anm = afd_22.dEZ;
            if (anm.is()) {
                bl2 = true;
                if (n3 == 0) {
                    this.d(anm);
                } else {
                    for (int j = 0; j < anm.qA.length; ++j) {
                        ju_2 ju_22 = anm.qA[j];
                        if (ju_22 == null || ju_22.CX == 0 || ju_22.CY != n3) continue;
                        this.Gi.c(ju_22.CX, new afd_2(n3, anm, ju_22));
                    }
                }
                this.Gh.remove(n2);
                continue;
            }
            if (anm.ip()) {
                this.Gh.remove(n2);
                continue;
            }
            ++n2;
        }
        return bl2;
    }

    private void b(Anm anm) {
        if (anm == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/PartsHelper.addEquipment must not be null");
        }
        int n2 = this.Gg.size();
        for (int j = 0; j < n2; ++j) {
            if (this.Gg.get(j) != anm) continue;
            return;
        }
        anm.HE();
        this.Gg.add(anm);
    }

    private void c(Anm anm) {
        if (anm == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/framework/graphics/engine/Anm2/PartsHelper.removeReference must not be null");
        }
        assert (anm.avb() > 0);
        anm.HF();
    }

    private boolean pP() {
        boolean bl2 = false;
        int n2 = 0;
        while (n2 < this.Gg.size()) {
            Anm anm = (Anm)this.Gg.get(n2);
            boolean bl3 = false;
            ll_0 ll_02 = this.Gi.pK();
            while (ll_02.hasNext()) {
                ll_02.fK();
                if (((afd_2)ll_02.value()).dEZ != anm) continue;
                bl3 = true;
                break;
            }
            int n3 = this.Gh.size();
            for (int j = 0; j < n3; ++j) {
                if (((afd_2)this.Gh.get((int)j)).dEZ != anm) continue;
                bl3 = true;
                break;
            }
            if (!bl3) {
                this.Gg.remove(n2);
                this.c(anm);
                bl2 = true;
                continue;
            }
            ++n2;
        }
        return bl2;
    }

    boolean d(Anm anm) {
        boolean bl2 = false;
        if (anm.is()) {
            for (int j = 0; j < anm.qA.length; ++j) {
                ju_2 ju_22 = anm.qA[j];
                int n2 = ju_22.CX;
                if (ju_22.m_name != null) {
                    this.Gi.c(n2, new afd_2(n2, anm, ju_22));
                }
                bl2 = true;
            }
        } else {
            this.Gh.add(new afd_2(0, anm, null));
        }
        return bl2;
    }

    lc_1 pQ() {
        int n2;
        lc_1 lc_12 = new lc_1();
        int n3 = this.Gg.size();
        lc_12.Gg.ensureCapacity(n3);
        for (n2 = 0; n2 < n3; ++n2) {
            Anm anm = (Anm)this.Gg.get(n2);
            anm.HE();
            lc_12.Gg.add(anm);
        }
        n2 = this.Gh.size();
        lc_12.Gh.ensureCapacity(n2);
        for (int j = 0; j < n2; ++j) {
            lc_12.Gh.add(new afd_2((afd_2)this.Gh.get(j)));
        }
        ll_0 ll_02 = this.Gi.pK();
        lc_12.Gi.ensureCapacity(this.Gi.size());
        while (ll_02.hasNext()) {
            ll_02.fK();
            lc_12.Gi.c(ll_02.kR(), ll_02.value());
        }
        return lc_12;
    }
}

