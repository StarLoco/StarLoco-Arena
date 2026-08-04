/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from aci
 */
public abstract class aci_2
extends acz_0
implements mt_2 {
    private List cjt;
    boolean bgs = false;

    public void start() {
        this.bgs = true;
    }

    public void stop() {
        this.bgs = false;
    }

    public boolean isStarted() {
        return this.bgs;
    }

    public void n(List list) {
        this.cjt = list;
    }

    protected String aqI() {
        if (this.cjt == null || this.cjt.size() == 0) {
            return null;
        }
        return (String)this.cjt.get(0);
    }

    protected List aqJ() {
        return this.cjt;
    }
}

