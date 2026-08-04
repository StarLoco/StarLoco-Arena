/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from cw
 */
public class cw_1 {
    private static final cw_1 iY = new cw_1();
    private static final Logger a = Logger.getLogger(cw_1.class);
    private final cp_2 iZ = new cp_2();
    private final lb_0 ja = new lb_0();
    private final lb_0 jb = new lb_0();
    private long[] jc = ug_2.bQg;

    public static cw_1 eO() {
        return iY;
    }

    public void a(wq_2 wq_22) {
        if (!this.iZ.v(wq_22.getId())) {
            this.iZ.a(wq_22.getId(), wq_22);
        }
    }

    public void a(long[] lArray, int n2) {
        if (!this.ja.contains(n2)) {
            this.ja.c(n2, lArray);
        }
        if (lArray.length > this.jc.length) {
            this.jc = new long[lArray.length];
        }
    }

    public void a(of_2 of_22) {
        if (!this.jb.contains(of_22.getId())) {
            this.jb.c(of_22.getId(), of_22);
        }
    }

    public wq_2 w(long l2) {
        if (this.iZ.v(l2)) {
            return (wq_2)this.iZ.t(l2);
        }
        return null;
    }

    public ArrayList P(int n2) {
        ArrayList arrayList = new ArrayList();
        of_2 of_22 = (of_2)this.jb.get(n2);
        if (of_22 == null) {
            a.error((Object)("Impossble d'obtenir la liste d'\u00e9v\u00e8nements d'id " + n2 + " : FightEventsList \u00e9gal \u00e0 null."));
        } else {
            for (int j = 0; j < of_22.abz() - 1; ++j) {
                zy_2 zy_22 = of_22.hd(j);
                int n3 = j < of_22.abz() - 2 ? of_22.hd(j + 1).anV() - zy_22.anV() : -1;
                this.a(arrayList, (long[])this.ja.get(zy_22.anW()), n3, zy_22.anX());
            }
            while (arrayList.size() <= 4 && this.a(n2, arrayList)) {
            }
        }
        return arrayList;
    }

    public boolean a(int n2, ArrayList arrayList) {
        boolean bl2 = false;
        of_2 of_22 = (of_2)this.jb.get(n2);
        if (of_22 == null) {
            a.error((Object)("Impossble d'ajouter la liste d'\u00e9v\u00e8nements d'id " + n2 + " \u00e0 une liste d'\u00e9v\u00e8nements : FightEventsList \u00e9gal \u00e0 null."));
        } else {
            long[] lArray = (long[])this.ja.get(of_22.hd(of_22.abz() - 1).anW());
            bl2 = lArray != null && this.a(arrayList, lArray, -1, false);
        }
        return bl2;
    }

    private boolean a(ArrayList arrayList, long[] lArray, int n2, boolean bl2) {
        boolean bl3 = false;
        if (arrayList == null) {
            a.error((Object)"Impossible d'ajouter une s\u00e9rie d'\u00e9v\u00e8nements \u00e0 une liste d'\u00e9v\u00e8nements : ArrayList \u00e9gal \u00e0 null.");
        } else if (lArray == null) {
            a.error((Object)"Impossible d'ajouter une s\u00e9rie d'\u00e9v\u00e8nements \u00e0 une liste d'\u00e9v\u00e8nements : CurrentEventsList \u00e9gal \u00e0 null.");
        } else {
            System.arraycopy(lArray, 0, this.jc, 0, lArray.length);
            int n3 = lArray.length;
            int n4 = 0;
            if (!bl2 && n2 > n3) {
                a.error((Object)"Houla, faites gaffe, y a pas assez de cartes disponibles pour remplir la p\u00e9riode sans r\u00e9p\u00e9tition");
            }
            if (n2 == -1 || !bl2 && n2 > n3) {
                n2 = n3;
                bl2 = false;
            }
            while (n4 < n2) {
                ++n4;
                int n5 = ou_1.he(n3) - 1;
                arrayList.add(this.iZ.t(this.jc[n5]));
                if (bl2) continue;
                this.jc[n5] = this.jc[n3 - 1];
                --n3;
            }
            bl3 = true;
        }
        return bl3;
    }
}

