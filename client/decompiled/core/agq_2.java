/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agQ
 */
public class agq_2
extends adg_2 {
    public static final String TAG = "spacer";

    public String getTag() {
        return TAG;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public void b() {
        super.b();
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
    }
}

