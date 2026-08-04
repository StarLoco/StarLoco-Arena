/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class GY
extends yu_2 {
    public static final Logger a = Logger.getLogger(GY.class);
    private static final int bdi = 18;
    private static final GY bdj = new GY();
    protected final cp_2 bdk;
    ArrayList bdl = new ArrayList(8);
    protected final cp_2 bdm;
    private final ArrayList aJc;
    private final ArrayList aJd;
    private final ArrayList aJe;
    private final ArrayList aJf;
    private boolean bdn = true;
    private final aoU bdo = new ajw_2(this);

    public static GY Ss() {
        return bdj;
    }

    protected GY() {
        this.bdk = new cp_2();
        this.bdm = new cp_2();
        this.aJc = new ArrayList();
        this.aJd = new ArrayList();
        this.aJe = new ArrayList();
        this.aJf = new ArrayList();
    }

    public boolean St() {
        return this.bdn;
    }

    public void bv(boolean bl2) {
        this.bdn = bl2;
    }

    public void b(tp_1 tp_12) {
        if (!this.bdk.v(tp_12.getId())) {
            tp_12.eV(tp_12.zr());
            this.bdk.a(tp_12.getId(), tp_12);
            ry ry2 = tp_12.aTI();
            long l2 = ej_0.o(ry2.getX(), ry2.getY());
            qa_2 qa_22 = (qa_2)this.bdm.t(l2);
            if (qa_22 == null) {
                qa_22 = new qa_2();
                qa_22.ct(tp_12.getId());
                this.bdm.a(l2, qa_22);
            } else {
                qa_22.ct(tp_12.getId());
            }
            this.a(tp_12, ry2.getX(), ry2.getY(), ry2.wk());
            this.d(tp_12);
        } else {
            a.warn((Object)("Impossible d'ajouter l'\u00e9l\u00e9ment id=" + tp_12.getId() + " en " + tp_12.gn() + ":" + tp_12.go() + " car il en existe d\u00e9j\u00e0 avec cet ID."));
        }
    }

    public void c(tp_1 tp_12) {
        if (tp_12 != null) {
            this.bdk.u(tp_12.getId());
            long l2 = ej_0.o(tp_12.aTI().getX(), tp_12.aTI().getY());
            qa_2 qa_22 = (qa_2)this.bdm.t(l2);
            for (int j = qa_22.size() - 1; j >= 0; --j) {
                if (qa_22.hn(j) != tp_12.getId()) continue;
                qa_22.remove(j);
                break;
            }
            if (qa_22.size() == 0) {
                this.bdm.u(l2);
            }
            this.e(tp_12);
            tp_12.release();
        } else {
            a.error((Object)"Impossible de retirer un element null");
        }
    }

    public void bE(long l2) {
        tp_1 tp_12 = (tp_1)this.bdk.t(l2);
        if (tp_12 != null) {
            this.c(tp_12);
        } else {
            a.warn((Object)("Impossible de supprimer un element d'ID " + l2 + " qui n'existe pas"));
        }
    }

    public void removeAllElements() {
        a.info((Object)"Supression de tout les Element du AnimatedElementSceneViewManager.");
        Object[] objectArray = new tp_1[this.bdk.size()];
        for (Object object : objectArray = (tp_1[])this.bdk.a(objectArray)) {
            this.c((tp_1)object);
        }
        if (this.bdk.size() > 0) {
            a.error((Object)("Il reste encore " + this.bdk.size() + " apr\u00e8s la supression !!!"));
        }
    }

    public boolean ao(int n2, int n3) {
        return this.bdm.m(ej_0.o(n2, n3));
    }

    public tp_1 bF(long l2) {
        return (tp_1)this.bdk.t(l2);
    }

    public ArrayList ap(int n2, int n3) {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        qa_2 qa_22 = (qa_2)this.bdm.t(ej_0.o(n2, n3));
        if (qa_22 != null) {
            for (int j = qa_22.size() - 1; j >= 0; --j) {
                arrayList.add(this.bdk.t(qa_22.get(j)));
            }
            return arrayList;
        }
        return arrayList;
    }

    public int Su() {
        return this.bdk.size();
    }

    public void a(qs_2 qs_22, int n2) {
        cp_2 cp_22 = this.bdk.eH();
        akz_0 akz_02 = cp_22.eI();
        this.atf.clear();
        while (akz_02.hasNext()) {
            akz_02.fK();
            tp_1 tp_12 = (tp_1)akz_02.value();
            if (!tp_12.b(qs_22, n2)) continue;
            if (tp_12.zq()) {
                this.bdl.add(tp_12);
                continue;
            }
            this.atf.add(tp_12);
        }
        int n3 = this.bdl.size();
        for (int j = 0; j < n3; ++j) {
            this.c((tp_1)this.bdl.get(j));
        }
        this.bdl.clear();
    }

    public void a(qs_2 qs_22, float f, float f2) {
        this.atf.clear();
        if (!this.bdn) {
            return;
        }
        int n2 = (int)Math.floor(qs_22.aNA());
        YR yR = qs_22.vn();
        akz_0 akz_02 = this.bdk.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            tp_1 tp_12 = (tp_1)akz_02.value();
            if (tp_12.a(qs_22)) {
                this.atf.add(tp_12);
            }
            this.a(tp_12, null, qs_22, f, f2);
        }
    }

    public void clear() {
        akz_0 akz_02 = this.bdk.eI();
        while (akz_02.hasNext()) {
            akz_02.fK();
            tp_1 tp_12 = (tp_1)akz_02.value();
            tp_12.dispose();
            tp_12.release();
        }
        this.bdk.clear();
        this.atf.clear();
        this.aJe.clear();
        this.aJe.addAll(this.aJc);
        this.aJf.clear();
        this.aJf.addAll(this.aJd);
    }

    public void a(long l2, int n2, int n3) {
        tp_1 tp_12 = (tp_1)this.bdk.t(l2);
        if (tp_12 != null) {
            long l3;
            long l4 = ej_0.o(tp_12.aTI().getX(), tp_12.aTI().getY());
            qa_2 qa_22 = (qa_2)this.bdm.t(l4);
            for (int j = qa_22.size() - 1; j >= 0; --j) {
                if (qa_22.hn(j) != tp_12.getId()) continue;
                qa_22.remove(j);
                break;
            }
            if ((qa_22 = (qa_2)this.bdm.t(l3 = ej_0.o(n2, n3))) == null) {
                qa_22 = new qa_2();
                qa_22.ct(tp_12.getId());
                this.bdm.a(l3, qa_22);
            } else {
                qa_22.ct(tp_12.getId());
            }
        }
    }

    private void d(tp_1 tp_12) {
        int n2 = this.aJe.size();
        for (int j = 0; j < n2; ++j) {
            ((akz)this.aJe.get(j)).f(tp_12);
        }
    }

    private void e(tp_1 tp_12) {
        int n2 = this.aJf.size();
        for (int j = 0; j < n2; ++j) {
            ((ks_0)this.aJf.get(j)).a(tp_12);
        }
    }

    public void a(akz akz2) {
        if (this.aJc.contains(akz2)) {
            return;
        }
        this.aJc.add(akz2);
        this.b(akz2);
    }

    public void a(ks_0 ks_02) {
        if (this.aJd.contains(ks_02)) {
            return;
        }
        this.aJd.add(ks_02);
        this.b(ks_02);
    }

    public void b(akz akz2) {
        if (!this.aJe.contains(akz2)) {
            this.aJe.add(akz2);
        }
    }

    public void b(ks_0 ks_02) {
        if (!this.aJf.contains(ks_02)) {
            this.aJf.add(ks_02);
        }
    }

    public Iterator Sv() {
        return this.atf.iterator();
    }

    public akz_0 Sw() {
        return this.bdk.eI();
    }
}

