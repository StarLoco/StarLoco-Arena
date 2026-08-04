/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.util.ArrayList;

/*
 * Renamed from acV
 */
public class acv_0 {
    private static final acv_0 clf = new acv_0();
    private static final short clg = 20;
    private static final int clh = Integer.MIN_VALUE;
    private static int bZN = -2147483647;
    private asz cli = new asz();
    private ArrayList clj = new ArrayList();
    private static final ArrayList clk = new ArrayList();
    private static final String cll = "stickData";

    private acv_0() {
    }

    public static acv_0 arH() {
        return clf;
    }

    public void a(aab_2 aab_22, boolean bl2) {
        this.a(aab_22, bl2, true);
    }

    public void a(aab_2 aab_22, boolean bl2, boolean bl3) {
        aab_22.setStickData(new afo(bl2));
        this.clj.add(aab_22);
        aab_22.getStickData().km(Integer.MIN_VALUE);
        if (bl3) {
            aab_22.a(new gl_2(this, aab_22));
        }
    }

    public void b(aab_2 aab_22) {
        this.c(aab_22);
        this.clj.remove(aab_22);
    }

    public void a(aab_2 aab_22, int n2) {
        if (aab_22.getStickData().auY() == n2) {
            return;
        }
        this.c(aab_22);
        aab_22.getStickData().km(n2);
        ArrayList<aab_2> arrayList = (ArrayList<aab_2>)this.cli.get(n2);
        if (arrayList == null) {
            arrayList = new ArrayList<aab_2>();
            this.cli.put(n2, arrayList);
        }
        arrayList.add(aab_22);
    }

    public void c(aab_2 aab_22) {
        int n2 = aab_22.getStickData().auY();
        ArrayList arrayList = (ArrayList)this.cli.get(n2);
        if (arrayList != null) {
            arrayList.remove(aab_22);
        }
        aab_22.getStickData().km(Integer.MIN_VALUE);
    }

    private static int arI() {
        return ++bZN;
    }

    public void a(aab_2 aab_22, int n2, int n3, int n4, int n5, Point point, boolean bl2) {
        Object object;
        int n6;
        afo afo2 = aab_22.getStickData();
        int n7 = afo2 != null ? afo2.auY() : Integer.MIN_VALUE;
        ArrayList arrayList = (ArrayList)this.cli.get(n7);
        if (arrayList != null && !afo2.auX() && !bl2) {
            aab_2 aab_23;
            for (n6 = arrayList.size() - 1; n6 >= 0; --n6) {
                aab_23 = (aab_2)arrayList.get(n6);
                if (aab_23 == aab_22 || (object = dm_2.a(n4, n5, aab_22, aab_23)) == dm_2.my) continue;
                n4 = ((dm_2)((Object)object)).a(n4, aab_23, aab_22);
                n5 = ((dm_2)((Object)object)).b(n5, aab_23, aab_22);
            }
            if (n4 == n2 && n5 == n3) {
                point.setLocation(n4, n5);
                return;
            }
            this.a(aab_22, n4, n5);
            for (n6 = arrayList.size() - 1; n6 >= 0; --n6) {
                clk.add(arrayList.get(n6));
            }
            for (n6 = clk.size() - 1; n6 >= 0; --n6) {
                aab_23 = (aab_2)clk.get(n6);
                this.a(aab_23, aab_23.getX(), aab_23.getY());
            }
            clk.clear();
        }
        int n8 = this.clj.size();
        for (n6 = 0; n6 < n8; ++n6) {
            dm_2 dm_22;
            object = (aab_2)this.clj.get(n6);
            if (object == aab_22 || ((aab_2)object).getStickData().auY() == aab_22.getStickData().auY() && ((aab_2)object).getStickData().auY() != Integer.MIN_VALUE || (dm_22 = dm_2.a(n4, n5, aab_22, (aab_2)object)) == dm_2.my) continue;
            n4 = dm_22.a(n4, (aab_2)object, aab_22);
            n5 = dm_22.b(n5, (aab_2)object, aab_22);
        }
        dm_2 dm_23 = dm_2.a(n4, n5, aab_22, aab_22.getContainer());
        n4 = dm_23.a(n4, aab_22);
        n5 = dm_23.b(n5, aab_22);
        if ((bl2 || aab_22.getStickData().auX()) && aab_22.getStickData().auY() != Integer.MIN_VALUE) {
            ArrayList arrayList2 = (ArrayList)this.cli.get(aab_22.getStickData().auY());
            int n9 = n4 - n2;
            int n10 = n5 - n3;
            int n11 = arrayList2.size();
            for (int j = 0; j < n11; ++j) {
                aab_2 aab_24 = (aab_2)arrayList2.get(j);
                if (aab_24 == aab_22) continue;
                aab_24.setPosition(aab_24.getX() + n9, aab_24.getY() + n10);
            }
        }
        point.x = n4;
        point.y = n5;
    }

    private void a(aab_2 aab_22, int n2, int n3) {
        afo afo2 = aab_22.getStickData();
        if (afo2.auY() == Integer.MIN_VALUE) {
            return;
        }
        ArrayList arrayList = (ArrayList)this.cli.get(afo2.auY());
        boolean bl2 = false;
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            dm_2 dm_22;
            aab_2 aab_23 = (aab_2)arrayList.get(j);
            if (aab_23 == aab_22 || (dm_22 = dm_2.a(n2, n3, aab_22, aab_23)) == dm_2.my) continue;
            bl2 = true;
            break;
        }
        if (!bl2) {
            arrayList.remove(aab_22);
            afo2.km(Integer.MIN_VALUE);
        }
    }

    public void b(aab_2 aab_22, int n2, int n3) {
        int n4 = this.clj.size();
        for (int j = 0; j < n4; ++j) {
            int n5;
            dm_2 dm_22;
            aab_2 aab_23 = (aab_2)this.clj.get(j);
            if (aab_23 == aab_22 || aab_23.getStickData().auY() == aab_22.getStickData().auY() && aab_23.getStickData().auY() != Integer.MIN_VALUE || (dm_22 = dm_2.a(n2, n3, aab_22, aab_23)) == dm_2.my) continue;
            int n6 = aab_22.getStickData().auY();
            int n7 = aab_23.getStickData().auY();
            int n8 = n5 = n6 != Integer.MIN_VALUE ? n6 : n7;
            if (n5 == Integer.MIN_VALUE) {
                n5 = acv_0.arI();
            }
            if (n6 != n5) {
                this.a(aab_22, n5);
            }
            if (n7 == n5) continue;
            if (n7 == Integer.MIN_VALUE) {
                this.a(aab_23, n5);
                continue;
            }
            ArrayList arrayList = (ArrayList)this.cli.get(n7);
            for (int i2 = arrayList.size() - 1; i2 >= 0; --i2) {
                this.a((aab_2)arrayList.get(i2), n5);
            }
        }
    }

    public void d(aab_2 aab_22) {
        this.b(aab_22, aab_22.getX(), aab_22.getY());
    }

    public boolean hw(String string) {
        abk_0 abk_02 = add_1.aOG().kD(string);
        return abk_02 != null && abk_02.getBoolean(cll + string);
    }

    public void e(aab_2 aab_22) {
        String string = aab_22.getElementMap().getId();
        add_1.aOG().kD(string).t(cll + aab_22.getHorizontalDialog(), string.startsWith(aab_22.getVerticalDialog()));
    }
}

