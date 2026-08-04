/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from JS
 */
public abstract class js_2
extends aat_0
implements aDa,
gx_2 {
    private boolean bmL = true;

    public void bL(boolean bl2) {
        this.bmL = bl2;
    }

    public Reader b(Reader reader) {
        tD tD2 = new tD(reader);
        if (!this.bmL) {
            tD2.a((abe_0)new anl_0());
        }
        tD2.a(this);
        return tD2;
    }
}

