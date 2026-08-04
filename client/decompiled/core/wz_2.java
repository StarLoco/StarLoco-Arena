/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;

/*
 * Renamed from wZ
 */
public class wz_2
extends aht_1 {
    public static final String TAG = "DisplayContainer";
    private int wg = 30;
    private int avQ = 10;
    private final HashMap avR = new HashMap();
    public static final int avS = "contentSize".hashCode();
    public static final int avT = "duration".hashCode();

    public boolean b(adg_2 adg_22, int n2) {
        boolean bl2 = super.b(adg_22, n2);
        if (bl2) {
            if (this.dMc.size() > this.avQ) {
                ((adg_2)this.dMc.get(0)).aab();
            }
            this.avR.put(adg_22, new ru_0(adg_22, this.wg * 1000));
            aqo aqo2 = new aqo(this, this.dMc.size() == this.avQ);
            aqo2.b();
            this.f(aqo2);
        }
        return bl2;
    }

    public void b(adg_2 adg_22) {
        ru_0 ru_02 = (ru_0)this.avR.remove(adg_22);
        if (ru_02 != null) {
            ru_02.stop();
        }
        super.b(adg_22);
        aqo aqo2 = new aqo(this, this.dMc.size() == this.avQ);
        aqo2.b();
        this.f(aqo2);
    }

    public String getTag() {
        return TAG;
    }

    public int getDuration() {
        return this.wg;
    }

    public void setDuration(int n2) {
        this.wg = n2;
    }

    public int getContentSize() {
        return this.avQ;
    }

    public void setContentSize(int n2) {
        this.avQ = n2;
    }

    public boolean isFull() {
        return this.avQ == this.dMc.size();
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        wz_2 wz_22 = (wz_2)air_12;
        wz_22.setDuration(this.wg);
        wz_22.setContentSize(this.avQ);
    }

    public void j() {
        super.j();
        for (ru_0 ru_02 : this.avR.values()) {
            ru_02.stop();
        }
        this.avR.clear();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == avS) {
            this.setContentSize(Gr.R(string));
        } else if (n2 == avT) {
            this.setDuration(Gr.R(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == avS) {
            this.setContentSize(Gr.R(object));
        } else if (n2 == avT) {
            this.setDuration(Gr.R(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

