/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

public class adY
implements aho_0 {
    public static final String cnR = "teamManagement.fighterList";
    public static final String cnS = "teamManagement.filtredFighterList";
    public static final String[] ce = new String[]{"teamManagement.fighterList", "teamManagement.filtredFighterList"};
    private static adY cnT = new adY();
    private cp_2 caq = new cp_2();
    private abv_1 cnU = null;

    public adY() {
        azs_0.aLV().g("teamManagement.fighterManager", this);
    }

    public static adY atu() {
        return cnT;
    }

    public void clear() {
        this.caq.a(new T(this));
        this.caq.clear();
        if (this.cnU != null) {
            this.cnU.release();
            this.cnU = null;
        }
    }

    public boolean isEmpty() {
        return this.caq.isEmpty();
    }

    public void j(ee_2 ee_22) {
        this.caq.a(ee_22.getId(), ee_22);
        azs_0.aLV().a((aho_0)this, cnR);
        azs_0.aLV().a((aho_0)adY.atu(), cnS);
    }

    public void Y(long l2) {
        this.caq.u(l2);
    }

    public void k(ee_2 ee_22) {
        this.Y(ee_22.getId());
    }

    public ee_2 dz(long l2) {
        return (ee_2)this.caq.t(l2);
    }

    public cp_2 amq() {
        return this.caq;
    }

    public abv_1 Ol() {
        return this.cnU;
    }

    public void a(abv_1 abv_12) {
        this.cnU = abv_12;
        this.atw();
    }

    public abv_1 atv() {
        return new abv_1();
    }

    private void atw() {
        azs_0.aLV().g("teamManagement.editableFighter", this.cnU);
    }

    public void atx() {
        if (this.cnU != null) {
            this.cnU.release();
            this.cnU = null;
        }
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(cnR)) {
            ArrayList arrayList = new ArrayList(this.caq.size());
            this.caq.a(new U(this, arrayList));
            Collections.sort(arrayList, new V(this));
            return arrayList.toArray();
        }
        if (string.equals(cnS)) {
            ArrayList arrayList = new ArrayList(this.caq.size());
            this.caq.a(new Z(this, arrayList));
            Collections.sort(arrayList, new aa(this));
            return arrayList.toArray();
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

