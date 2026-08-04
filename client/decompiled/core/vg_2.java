/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Font;

/*
 * Renamed from vG
 */
public class vg_2
extends afg_0 {
    private boolean atw;

    public vg_2(Font font, boolean bl2, boolean bl3) {
        super(font, bl2, bl3);
        this.atw = false;
    }

    public vg_2(Font font, boolean bl2, boolean bl3, bE bE2) {
        super(font, bl2, bl3, bE2);
        this.atw = bE2 instanceof uu;
    }

    public boolean aD() {
        return this.atw;
    }
}

