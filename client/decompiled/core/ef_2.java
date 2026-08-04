/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ef
 */
public class ef_2 {
    private final lb_0 nX = new lb_0();

    public boolean a(JX jX) {
        this.nX.remove(jX.getId());
        return this.nX.size() == 0;
    }

    public boolean a(afs_1 afs_12) {
        Object[] objectArray = new aca_1[this.nX.size()];
        for (aca_1 aca_12 : (aca_1[])this.nX.a(objectArray)) {
            aca_12.a(afs_12);
            if (!aca_12.aOl()) continue;
            this.nX.remove(aca_12.eA());
        }
        return this.nX.isEmpty();
    }

    public void a(JX jX, String string, jJ[] jJArray, boolean bl2) {
        this.nX.c(jX.getId(), new aca_1(this, jX, string, jJArray, bl2));
    }
}

