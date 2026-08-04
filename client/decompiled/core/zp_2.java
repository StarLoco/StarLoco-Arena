/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZP
 */
public class zp_2
extends aks {
    private int[] cZ = new int[]{-1};
    private int[] da = new int[]{-1};

    public boolean GO() {
        try {
            this.cY.alGenAuxiliaryEffectSlots(1, this.da, 0);
            this.bAT.check();
            this.cY.alGenEffects(1, this.cZ, 0);
            this.bAT.check();
        }
        catch (Exception exception) {
            return false;
        }
        return true;
    }

    public void cleanUp() {
        if (this.da[0] != -1) {
            this.cY.alDeleteAuxiliaryEffectSlots(1, this.da, 0);
            this.bAT.check();
            this.da[0] = -1;
        }
        if (this.cZ[0] != -1) {
            this.cY.alDeleteEffects(1, this.cZ, 0);
            this.bAT.check();
            this.cZ[0] = -1;
        }
        this.cZ = null;
        this.da = null;
        super.cleanUp();
    }

    public amA GP() {
        return amA.cHG;
    }
}

