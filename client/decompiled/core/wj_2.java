/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/*
 * Renamed from wJ
 */
public class wj_2
implements aHq {
    private static final wj_2 avm = new wj_2();
    private int avn = 0;
    private final Map avo;
    private final lb_0 avp;
    private final ArrayList avq = new ArrayList(5);

    private wj_2() {
        this.avo = new HashMap();
        this.avp = new lb_0();
    }

    public static wj_2 Df() {
        return avm;
    }

    public int Dg() {
        return ++this.avn;
    }

    public void a(ajY ajY2) {
        if (ajY2 == null) {
            return;
        }
        this.avq.add(ajY2);
    }

    public void b(ajY ajY2) {
        if (ajY2 == null) {
            return;
        }
        this.avq.remove(ajY2);
    }

    private void a(xu_1 xu_12, iw_2 iw_22) {
        if (xu_12 == null || iw_22 == null) {
            return;
        }
        int n2 = this.avq.size();
        if (n2 == 0) {
            return;
        }
        Tw tw = new Tw(xu_12, iw_22);
        for (int j = 0; j < n2; ++j) {
            ((ajY)this.avq.get(j)).a(tw);
        }
    }

    public int a(xu_1 xu_12) {
        aln_1 aln_12 = xu_12.Ek();
        HashSet<xu_1> hashSet = (HashSet<xu_1>)this.avo.get(aln_12);
        if (hashSet == null) {
            hashSet = new HashSet<xu_1>();
            this.avo.put(aln_12, hashSet);
        }
        if (!hashSet.contains(xu_12)) {
            xu_12.f(this.Dg());
            hashSet.add(xu_12);
            this.avp.c(xu_12.getId(), xu_12);
            this.a(xu_12, iw_2.biI);
        }
        return xu_12.getId();
    }

    public final HashSet a(aln_1 aln_12) {
        return (HashSet)this.avo.get(aln_12);
    }

    public boolean a(aln_1 aln_12, int n2) {
        HashSet hashSet = (HashSet)this.avo.get(aln_12);
        if (hashSet == null) {
            return false;
        }
        Iterator iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            xu_1 xu_12 = (xu_1)iterator.next();
            if (xu_12.ao() != n2) continue;
            iterator.remove();
            return true;
        }
        return false;
    }

    public final void clear() {
        ll_0 ll_02 = this.avp.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((xu_1)ll_02.value()).cleanUp();
        }
        this.avo.clear();
        this.avp.clear();
    }

    public final xu_1 eg(int n2) {
        return (xu_1)this.avp.get(n2);
    }

    public void eh(int n2) {
        ll_0 ll_02 = this.avp.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            xu_1 xu_12 = (xu_1)ll_02.value();
            if (xu_12.ao() != n2) continue;
            HashSet hashSet = (HashSet)this.avo.get(xu_12.Ek());
            assert (hashSet != null);
            hashSet.remove(xu_12);
            ll_02.remove();
            xu_12.cleanUp();
            this.a(xu_12, iw_2.biJ);
        }
    }

    public void b(aln_1 aln_12, int n2) {
        HashSet hashSet = (HashSet)this.avo.get(aln_12);
        if (hashSet != null) {
            Iterator iterator = hashSet.iterator();
            while (iterator.hasNext()) {
                xu_1 xu_12 = (xu_1)iterator.next();
                if (xu_12.ao() != n2) continue;
                this.avp.remove(xu_12.getId());
                iterator.remove();
                xu_12.cleanUp();
                this.a(xu_12, iw_2.biJ);
            }
        }
    }

    public void c(aln_1 aln_12, int n2) {
        HashSet hashSet = (HashSet)this.avo.get(aln_12);
        if (hashSet != null) {
            Iterator iterator = hashSet.iterator();
            while (iterator.hasNext()) {
                xu_1 xu_12 = (xu_1)iterator.next();
                if (xu_12.ao() != n2) continue;
                this.avp.remove(xu_12.getId());
                iterator.remove();
                xu_12.cleanUp();
                this.a(xu_12, iw_2.biJ);
                return;
            }
        }
    }

    public final void b(aln_1 aln_12) {
        HashSet hashSet = (HashSet)this.avo.remove(aln_12);
        if (hashSet != null) {
            for (xu_1 xu_12 : hashSet) {
                this.avp.remove(xu_12.getId());
                xu_12.cleanUp();
                this.a(xu_12, iw_2.biJ);
            }
        }
    }

    public final void b(xu_1 xu_12) {
        assert (xu_12 != null);
        this.ei(xu_12.getId());
    }

    public void ei(int n2) {
        xu_1 xu_12 = (xu_1)this.avp.remove(n2);
        if (xu_12 != null) {
            HashSet hashSet = (HashSet)this.avo.get(xu_12.Ek());
            assert (hashSet != null);
            hashSet.remove(xu_12);
            xu_12.cleanUp();
            this.a(xu_12, iw_2.biJ);
        }
    }

    public void a(qs_2 qs_22, float f, float f2) {
        int n2 = (int)Math.floor(qs_22.aNA());
        ll_0 ll_02 = this.avp.pK();
        float f3 = qs_22.vn().aEK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            xu_1 xu_12 = (xu_1)ll_02.value();
            int n3 = (int)xu_12.getAltitude() * n2;
            double d = xu_12.getWorldX();
            double d2 = xu_12.getWorldY();
            double d3 = qs_22.i(d, d2);
            double d4 = qs_22.j(d, d2);
            double d5 = d3 * (double)(xu_12.El() ? f3 : 1.0f);
            double d6 = (d4 + (double)n3) * (double)(xu_12.El() ? f3 : 1.0f);
            int n4 = (int)(d5 + (double)((float)xu_12.getXOffset() * (xu_12.El() ? f3 : 1.0f)));
            int n5 = (int)(d6 + (double)((float)xu_12.getYOffset() * (xu_12.El() ? f3 : 1.0f)));
            xu_12.c(n4, n5, 0.0f, 0.0f);
            Entity entity = xu_12.getEntity();
            if (entity == null) continue;
            qs_22.b(entity, false);
        }
    }

    public void a(qs_2 qs_22, int n2) {
        ll_0 ll_02 = this.avp.pK();
        jg_0 jg_02 = new jg_0();
        while (ll_02.hasNext()) {
            ll_02.fK();
            xu_1 xu_12 = (xu_1)ll_02.value();
            if (!xu_12.isAlive()) {
                jg_02.add(xu_12.getId());
                continue;
            }
            xu_12.a(qs_22, n2);
        }
        for (int j = jg_02.size() - 1; j >= 0; --j) {
            this.ei(jg_02.bu(j));
        }
    }
}

