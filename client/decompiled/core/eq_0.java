/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from eq
 */
public class eq_0
extends aht_1 {
    private static Logger a = Logger.getLogger(eq_0.class);
    public static final String TAG = "LayeredContainer";
    public static final int op = -40000;
    public static final int oq = -39999;
    public static final int or = -30000;
    public static final int os = 25000;
    public static final int ot = 26000;
    public static final int ou = 27000;
    public static final int ov = 30000;
    public static final int ow = 40000;
    public static final int ox = Integer.MIN_VALUE;
    private jg_0 oy = new jg_0();

    public void a(adg_2 adg_22, int n2) {
        this.a(adg_22, n2, Integer.MAX_VALUE);
    }

    public void a(adg_2 adg_22, int n2, int n3) {
        if (!this.czc) {
            aht_1 aht_12 = this.getContainerFromLayer(n2);
            if (aht_12 == null) {
                aht_12 = this.ah(n2);
            }
            int n4 = Math.min(aht_12.getWidgetChildren().size(), n3);
            aht_12.c(adg_22, n4);
        }
    }

    private aht_1 ah(int n2) {
        int n3;
        aht_1 aht_12 = aht_1.checkOut();
        auW auW2 = new auW();
        auW2.b();
        auW2.setSize(new agj_1(100.0f, 100.0f));
        aht_12.a(auW2);
        azC azC2 = new azC();
        azC2.setAdaptToContentSize(true);
        azC2.b();
        aht_12.a(azC2);
        int n4 = this.dMc.size();
        for (n3 = 0; n3 < n4 && this.oy.get(n3) <= n2; ++n3) {
        }
        this.oy.v(n3, n2);
        this.c(aht_12, n3);
        return aht_12;
    }

    public void b(na_1 na_12) {
        int n2;
        if (na_12 instanceof aht_1 && (n2 = this.dMc.indexOf(na_12)) != -1) {
            this.oy.bv(n2);
        }
        super.b(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public int getLayer(adg_2 adg_22) {
        int n2 = this.dMc.size();
        for (int j = 0; j < n2; ++j) {
            aht_1 aht_12 = (aht_1)this.dMc.get(j);
            if (!aht_12.getWidgetChildren().contains(adg_22)) continue;
            return this.oy.get(j);
        }
        return Integer.MIN_VALUE;
    }

    public int getWidgetCountInLayer(int n2) {
        aht_1 aht_12 = this.getContainerFromLayer(n2);
        if (aht_12 != null) {
            return aht_12.getWidgetChildren().size();
        }
        return 0;
    }

    public aht_1 getContainerFromLayer(int n2) {
        int n3 = this.oy.indexOf(n2);
        if (n3 != -1) {
            return (aht_1)this.dMc.get(n3);
        }
        return null;
    }

    public aht_1 getContainerFromWidget(adg_2 adg_22) {
        int n2 = this.dMc.size();
        for (int j = 0; j < n2; ++j) {
            aht_1 aht_12 = (aht_1)this.dMc.get(j);
            if (!aht_12.getWidgetChildren().contains(adg_22)) continue;
            return aht_12;
        }
        return null;
    }

    public void a(int n2, apx apx2) {
        apx2.a(this.getContainerFromLayer(n2));
    }

    public void b(int n2, apx apx2) {
        if (!this.oy.isEmpty()) {
            this.oy.a(new Mg(this, n2, apx2));
        }
    }

    public void a(adg_2 adg_22) {
        aht_1 aht_12;
        int n2 = this.getLayer(adg_22);
        if (n2 != Integer.MIN_VALUE && (aht_12 = this.getContainerFromLayer(n2)) != null) {
            aht_12.getWidgetChildren().remove(adg_22);
            aht_12.getWidgetChildren().add(adg_22);
            aht_12.setNeedsToResetMeshes();
        }
    }

    public int getWidgetPositionInLayer(adg_2 adg_22) {
        aht_1 aht_12;
        int n2 = this.getLayer(adg_22);
        if (n2 != Integer.MIN_VALUE && (aht_12 = this.getContainerFromLayer(n2)) != null) {
            return aht_12.getWidgetChildren().indexOf(adg_22);
        }
        return -1;
    }

    public void setWidgetPositionInLayer(adg_2 adg_22, int n2) {
        aht_1 aht_12;
        if (n2 < 0) {
            a.warn((Object)"on essaye de set la position d'un widget dans un layer \u00e0 une position inf\u00e9rieure \u00e0 0");
            return;
        }
        int n3 = this.getLayer(adg_22);
        if (n3 != Integer.MIN_VALUE && (aht_12 = this.getContainerFromLayer(n3)) != null) {
            ArrayList arrayList = aht_12.getWidgetChildren();
            if (n2 >= arrayList.size()) {
                a.warn((Object)"on essaye de set la position d'un widget dans un layer \u00e0 une position trop grande");
                return;
            }
            arrayList.remove(adg_22);
            arrayList.add(n2, adg_22);
            aht_12.setNeedsToResetMeshes();
        }
    }

    public void b() {
        super.b();
        azC azC2 = new azC();
        azC2.b();
        azC2.setAdaptToContentSize(true);
        this.a(azC2);
    }
}

