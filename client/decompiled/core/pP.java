/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class pP
extends ph_0 {
    private static Logger a = Logger.getLogger(pP.class);
    public static final String TAG = "GradientBackground";
    private fr_1 acw;

    public String getTag() {
        return TAG;
    }

    public void setColor(aab_0 aab_02) {
        this.setColor(aab_02.getColor(), aab_02.getPosition());
    }

    public void setColor(vP vP2, acX acX2) {
        this.getMesh().setColor(vP2, acX2);
    }

    public fr_1 getMesh() {
        return this.acw;
    }

    public boolean isValidAdd(air_1 air_12) {
        if (air_12 instanceof aab_0 && ((aab_0)air_12).getPosition() == null) {
            a.error((Object)"Tentative d'ajout d'un ColorElement sans position");
            return false;
        }
        return super.isValidAdd(air_12);
    }

    protected void a(aab_0 aab_02) {
        if (this.uA == null) {
            return;
        }
        for (int j = this.uA.size() - 1; j >= 0; --j) {
            na_1 na_12 = (na_1)this.uA.get(j);
            if (!(na_12 instanceof aab_0) || !((aab_0)na_12).getPosition().equals((Object)aab_02.getPosition())) continue;
            this.k(na_12);
        }
    }

    public void b() {
        super.b();
        this.acw = new fr_1();
        this.acw.b();
    }

    public void j() {
        super.j();
        this.acw.j();
    }
}

