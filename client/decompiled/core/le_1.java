/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from lE
 */
public abstract class le_1
extends aht_1 {
    private static final Logger a = Logger.getLogger(le_1.class);
    protected ArrayList Hy;
    protected boolean Hz = false;
    public static final int HA = "colors".hashCode();

    public void setColors(ArrayList arrayList) {
        if (this.Hy == arrayList) {
            return;
        }
        this.Hy = arrayList;
        this.Hz = true;
        this.setNeedsToPreProcess();
    }

    protected abstract void qt();

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.Hz) {
            this.qt();
            this.Hz = false;
        }
        return bl2;
    }

    public void j() {
        super.j();
        this.setColors(null);
    }

    public void b() {
        super.b();
        this.Hz = false;
        this.Hy = new ArrayList();
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != HA) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setColors((ArrayList)object);
        return true;
    }
}

