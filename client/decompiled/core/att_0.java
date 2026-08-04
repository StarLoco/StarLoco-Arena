/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/*
 * Renamed from atT
 */
public class att_0
extends a_0 {
    public static final String TAG = "SpringLayout";
    public static final String aTJ = "SPL";
    private HashMap cVq = new HashMap();

    public String getTag() {
        return TAG;
    }

    public ant_0 getConstraint(adg_2 adg_22) {
        ArrayList arrayList = (ArrayList)this.cVq.get(adg_22);
        return arrayList != null && arrayList.size() != 0 ? (ant_0)arrayList.get(0) : null;
    }

    private adg_2 getWidgetByConstraint(aht_1 aht_12, ant_0 ant_02) {
        for (adg_2 adg_22 : aht_12.getWidgetChildren()) {
            ant_0 ant_03;
            if (!(adg_22.getLayoutData() instanceof ant_0) || !(ant_03 = (ant_0)adg_22.getLayoutData()).equals(ant_02)) continue;
            return adg_22;
        }
        return null;
    }

    private void B(ArrayList arrayList) {
        Set set = this.cVq.keySet();
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            adg_2 adg_22 = (adg_2)arrayList.get(j);
            if (set.contains(adg_22)) continue;
            this.cVq.remove(adg_22);
        }
    }

    private void m(adg_2 adg_22) {
        if (adg_22.getLayoutData() instanceof ant_0) {
            return;
        }
        ArrayList<ant_0> arrayList = (ArrayList<ant_0>)this.cVq.get(adg_22);
        if (arrayList == null) {
            arrayList = new ArrayList<ant_0>();
            this.cVq.put(adg_22, arrayList);
        }
        if (arrayList.size() == 0) {
            ant_0 ant_02 = ant_0.a(this, adg_22);
            arrayList.add(ant_02);
        }
    }

    public boolean a() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        this.B(aht_12.getWidgetChildren());
        Rectangle rectangle = new Rectangle();
        for (adg_2 adg_22 : aht_12.getWidgetChildren()) {
            ant_0 ant_02 = null;
            this.m(adg_22);
            ant_02 = (ant_0)((ArrayList)this.cVq.get(adg_22)).get(0);
            rectangle.union(new Rectangle(ant_02.getX().getValue(), ant_02.getY().getValue(), ant_02.getWidth().getValue(), ant_02.getHeight().getValue()));
        }
        return new agj_1((int)rectangle.getWidth(), (int)rectangle.getHeight());
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        ArrayList arrayList = aht_12.getWidgetChildren();
        this.B(arrayList);
        Rectangle rectangle = new Rectangle();
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            adg_2 adg_22 = (adg_2)arrayList.get(j);
            ant_0 ant_02 = null;
            this.m(adg_22);
            ant_02 = (ant_0)((ArrayList)this.cVq.get(adg_22)).get(0);
            rectangle.union(new Rectangle(ant_02.getX().getValue(), ant_02.getY().getValue(), ant_02.getWidth().getValue(), ant_02.getHeight().getValue()));
        }
        return new agj_1((int)rectangle.getWidth(), (int)rectangle.getHeight());
    }

    public static void a(aht_1 aht_12, List list) {
    }

    public void a(aht_1 aht_12) {
        adg_2 adg_22;
        int n2;
        ArrayList arrayList = aht_12.getWidgetChildren();
        if (aht_12 == null || arrayList == null) {
            return;
        }
        for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
            adg_22 = (adg_2)arrayList.get(n2);
            azC.b(aht_12, adg_22);
            this.m(adg_22);
        }
        for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
            ant_0 ant_02;
            adg_22 = (adg_2)arrayList.get(n2);
            if (!(adg_22.getLayoutData() instanceof ant_0) || (ant_02 = (ant_0)((ArrayList)this.cVq.get(adg_22)).get(0)) == null) continue;
            int n3 = ant_02.getX().getValue();
            int n4 = ant_02.getY().getValue();
            int n5 = ant_02.getWidth().getValue();
            int n6 = ant_02.getHeight().getValue();
            adg_22.setX(n3);
            adg_22.setY(n4);
            adg_22.setSize(new agj_1(n5, n6));
        }
    }

    public void j() {
        super.j();
        this.cVq.clear();
    }

    public att_0 aGX() {
        att_0 att_02 = new att_0();
        att_02.b();
        this.a((air_1)att_02);
        return att_02;
    }

    private boolean isFullyDisplayed(aht_1 aht_12, ant_0 ant_02) {
        return ant_02.getX().getValue() >= 0 && ant_02.getY().getValue() >= 0 && ant_02.getX().getValue() + ant_02.getWidth().getValue() <= aht_12.getWidth() && ant_02.getY().getValue() + ant_02.getHeight().getValue() <= aht_12.getHeight();
    }

    public void a(adg_2 adg_22, ant_0 ant_02) {
        if (ant_02 == null) {
            return;
        }
        ArrayList<ant_0> arrayList = (ArrayList<ant_0>)this.cVq.get(adg_22);
        if (arrayList == null) {
            arrayList = new ArrayList<ant_0>();
            this.cVq.put(adg_22, arrayList);
        } else {
            arrayList.clear();
        }
        arrayList.add(ant_02);
    }

    public void n(adg_2 adg_22) {
        this.cVq.remove(adg_22);
    }
}

