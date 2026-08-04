/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.isometric.highlight.HighLightEntity;

/*
 * Renamed from afA
 */
class afa_2
extends age_2 {
    static final /* synthetic */ boolean bb;

    afa_2() {
        super(null);
    }

    public boolean a(HighLightEntity highLightEntity) {
        if (!bb && this.cuD == null) {
            throw new AssertionError();
        }
        this.cuD.c(highLightEntity, true);
        return true;
    }

    static {
        bb = !wn_2.class.desiredAssertionStatus();
    }
}

