/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from asm
 */
class asm_0
implements Runnable {
    final /* synthetic */ ce_2 cRv;

    asm_0(ce_2 ce_22) {
        this.cRv = ce_22;
    }

    public void run() {
        if (akK.a(this.cRv.aMf) != null) {
            akK.a(this.cRv.aMf).b(this.cRv.aMf.cDO, this.cRv.aMf.cDP);
        }
        StringBuilder stringBuilder = new StringBuilder("PSys Debug ").append(" Part.: ").append(this.cRv.aMf.cDO.size()).append(" (max ").append(this.cRv.aMf.aQl).append(") Lights: ").append(this.cRv.aMf.cDP.size()).append(" (max ").append(this.cRv.aMf.cDQ).append(")");
        akK.b(this.cRv.aMf).setTitle(stringBuilder.toString());
    }
}

