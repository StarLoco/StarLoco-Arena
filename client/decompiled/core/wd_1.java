/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from wD
 */
public class wd_1
extends on_1 {
    private static Logger a = Logger.getLogger(wd_1.class);
    private BT cG = null;

    public void setup(and_0 and_02) {
        if (and_02 instanceof aac) {
            ((aac)and_02).setAlign(this.cG);
        }
    }

    public void setAlignment(BT bT) {
        this.cG = bT;
    }

    public BT getAlignment() {
        return this.cG;
    }

    public void a(air_1 air_12) {
        wd_1 wd_12 = (wd_1)air_12;
        super.a((air_1)wd_12);
        wd_12.cG = this.cG;
    }
}

