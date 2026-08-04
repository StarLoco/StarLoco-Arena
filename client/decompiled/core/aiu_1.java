/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIU
 */
class aiu_1
implements ja_1 {
    final /* synthetic */ dy_2 dQN;
    final /* synthetic */ ug_1 dQO;

    aiu_1(ug_1 ug_12, dy_2 dy_22) {
        this.dQO = ug_12;
        this.dQN = dy_22;
    }

    public void b(int n2) {
        abB abB2 = new abB();
        abB2.U(this.dQN.MF());
        abB2.T(this.dQN.ME());
        abB2.setName(this.dQN.getName());
        abB2.M((short)2);
        if (n2 == 8) {
            abB2.cV(true);
            apN.aDK().a(hu_2.li());
        } else {
            abB2.cV(false);
        }
        apN.aDK().vJ().b(abB2);
    }
}

