/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aui
 */
class aui_0
extends aNc
implements alg {
    public aui_0(avo_0 avo_02) {
        avo_0 avo_03 = avo_02;
        avo_03.getClass();
        super(avo_03);
    }

    public void aAv() {
        int n2 = this.offset % 4;
        if (n2 != 0) {
            avo_0 avo_02 = this.Gb();
            avo_02.a(this);
            avo_02.d((short)-1, 4 - n2);
            avo_02.aIy();
        }
    }
}

