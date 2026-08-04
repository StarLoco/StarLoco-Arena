/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.isometric.highlight.HighLightEntity;

class aDc
implements apx {
    static final /* synthetic */ boolean bb;

    aDc() {
    }

    public boolean a(HighLightEntity highLightEntity) {
        highLightEntity.HF();
        highLightEntity.aRc = false;
        if (!bb && highLightEntity.avb() != 0) {
            throw new AssertionError();
        }
        return true;
    }

    static {
        bb = !aaR.class.desiredAssertionStatus();
    }
}

