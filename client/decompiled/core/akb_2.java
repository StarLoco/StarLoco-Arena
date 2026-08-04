/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/*
 * Renamed from aKB
 */
public final class akb_2
implements acg_2 {
    public static final boolean cR = false;
    private static final Logger a = Logger.getLogger(akb_2.class);
    private final ArrayList G = new ArrayList();
    private final LinkedList dTH = new LinkedList();

    public LinkedList aVH() {
        return this.dTH;
    }

    public void c(Eq eq) {
        this.dTH.add(eq);
    }

    public Eq pc(int n2) {
        Eq eq = this.pd(n2);
        if (eq != null) {
            this.dTH.remove(eq);
        }
        return eq;
    }

    public Eq pd(int n2) {
        for (Eq eq : this.dTH) {
            if (eq.Ao() != n2) continue;
            return eq;
        }
        return null;
    }

    public Eq ci(int n2, int n3) {
        for (Eq eq : this.dTH) {
            if (eq.M() != n3 || eq.Nk() != n2) continue;
            return eq;
        }
        return null;
    }

    public Eq pe(int n2) {
        for (Eq eq : this.dTH) {
            if (eq.Nk() != n2) continue;
            return eq;
        }
        return null;
    }

    public Eq pf(int n2) {
        for (Eq eq : this.dTH) {
            if (eq.mS() != (long)n2) continue;
            return eq;
        }
        return null;
    }

    public Iterable aVI() {
        ArrayList<Long> arrayList = new ArrayList<Long>();
        for (Eq eq : this.dTH) {
            long l2 = eq.mS();
            if (l2 == Long.MIN_VALUE || arrayList.contains(l2)) continue;
            arrayList.add(l2);
        }
        return arrayList;
    }

    public Eq O(int n2, int n3, int n4) {
        for (Eq eq : this.dTH) {
            if (eq.mS() != (long)n2 || eq.Nk() != n3 || eq.M() != n4) continue;
            return eq;
        }
        return null;
    }

    public void a(dA dA2) {
        this.G.add(dA2);
    }

    public void b(dA dA2) {
        this.G.remove(dA2);
    }

    public void aVJ() {
        if (this.dTH == null || this.dTH.size() <= 0) {
            this.aVK();
            return;
        }
        Eq eq = (Eq)this.dTH.getFirst();
        this.a(eq, true);
    }

    public void a(Eq eq, boolean bl2) {
        if (this.dTH != null && this.dTH.contains(eq)) {
            Eq eq2;
            Iterator iterator = this.dTH.iterator();
            while (iterator.hasNext() && (eq2 = (Eq)iterator.next()) != eq) {
                if (eq2.No()) continue;
                if (bl2) {
                    eq2.a(this);
                } else {
                    iterator.remove();
                }
                this.a(eq2, "Forced execution");
            }
        }
        if (bl2) {
            eq.a(this);
        } else {
            this.pc(eq.Ao());
        }
        this.a(eq, "In Group order");
    }

    private void a(Eq eq, String string) {
        try {
            eq.bl(true);
            eq.run();
        }
        catch (Exception exception) {
            a.error((Object)("[_FL_] ACTION FAILURE (" + string + ") " + this.d(eq) + " - " + bl_0.b(exception)));
            this.a(eq);
        }
    }

    private String d(Eq eq) {
        return eq.getClass().getSimpleName() + " : " + eq.Ao() + " #" + eq.hashCode();
    }

    public void a(Eq eq) {
        eq.b(this);
        this.pc(eq.Ao());
        this.aVJ();
    }

    public void kill() {
        a.info((Object)("Kill des actions de la pile (" + this.aVH().size() + ")"));
        ArrayList<Eq> arrayList = new ArrayList<Eq>();
        for (Eq eq : this.dTH) {
            eq.b(this);
            arrayList.add(eq);
        }
        this.dTH.clear();
        for (Eq eq : arrayList) {
            if (eq instanceof kr_0) {
                int n2 = ((kr_0)eq).oT();
                if (n2 == -1) continue;
                Ky.WG().gC(n2);
                continue;
            }
            if (!(eq instanceof qs_0)) continue;
            qs_0 qs_02 = (qs_0)eq;
            aam_1.aMF().b(qs_02);
        }
        this.aVK();
    }

    private void aVK() {
        for (dA dA2 : this.G.toArray(new dA[0])) {
            dA2.a(this);
        }
    }

    public void aiT() {
        while (!this.dTH.isEmpty()) {
            Eq eq = (Eq)this.dTH.remove();
            eq.b(this);
            this.a(eq, "Executing all actions");
        }
    }
}

