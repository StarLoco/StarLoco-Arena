/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from a
 */
public abstract class a_0
extends amx_1
implements aiD {
    private static Logger a = Logger.getLogger(a_0.class);
    protected boolean b = false;

    public boolean a() {
        return true;
    }

    public boolean isStandAlone() {
        return this.b;
    }

    public agj_1 getContentGreedySize(aht_1 aht_12, adg_2 adg_22, agj_1 agj_12) {
        return this.getContentPreferedSize(aht_12);
    }

    public void a(aht_1 aht_12, adg_2 adg_22) {
    }

    public void b() {
        super.b();
        this.b = false;
    }

    public a_0 c() {
        return null;
    }
}

