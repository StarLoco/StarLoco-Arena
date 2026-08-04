/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Oq
 */
public class oq_0
extends ub_0 {
    private boolean bBy;
    private boolean bBR = true;

    public void b(db_2 db_22) {
        vo_1 vo_12 = vo_1.aik();
        if (this.bBR) {
            if (!this.bBy) {
                vo_12.cv(false);
            }
        } else if (!this.bBy) {
            vo_12.cv(true);
        }
    }

    public final void setUseParentScissor(boolean bl2) {
        this.bBy = bl2;
    }
}

