/*
 * Decompiled with CFR 0.152.
 */
public class iT
extends tj_0 {
    private int yV;
    private int yW;
    private int yX;
    private int yY;
    private boolean yZ;
    final /* synthetic */ ahr_2 za;

    public iT(ahr_2 ahr_22, int n2, int n3, int n4, int n5, adg_2 adg_22, int n6, int n7, ys ys2, boolean bl2) {
        this.za = ahr_22;
        super(null, null, adg_22, n6, n7, ys2);
        this.yV = n2;
        this.yW = n3;
        this.yX = n4;
        this.yY = n5;
        this.yZ = bl2;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            int n3 = (int)this.amA.b(this.yV, this.yX, this.IP, this.wg);
            int n4 = (int)this.amA.b(this.yW, this.yY, this.IP, this.wg);
            this.za.setDeltaPositionNoCheck(n3, n4);
        }
        return true;
    }

    public void ly() {
        this.za.setDeltaPositionNoCheck(this.yX, this.yY);
        if (this.yZ) {
            ahr_2.c(this.za);
        } else {
            this.za.axw();
        }
        super.ly();
    }
}

