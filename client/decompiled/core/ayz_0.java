/*
 * Decompiled with CFR 0.152.
 */
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Renamed from ayz
 */
public final class ayz_0
extends CopyOnWriteArrayList {
    private static final long serialVersionUID = 1L;

    public final vq_0 b(axe axe2, arN arN2, rl_2 rl_22, String string, Object[] objectArray, Throwable throwable) {
        int n2 = this.size();
        if (n2 == 1) {
            try {
                tm_0 tm_02 = (tm_0)this.get(0);
                return tm_02.a(axe2, arN2, rl_22, string, objectArray, throwable);
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                return vq_0.bTo;
            }
        }
        Object[] objectArray2 = this.toArray();
        int n3 = objectArray2.length;
        for (int j = 0; j < n3; ++j) {
            tm_0 tm_03 = (tm_0)objectArray2[j];
            vq_0 vq_02 = tm_03.a(axe2, arN2, rl_22, string, objectArray, throwable);
            if (vq_02 != vq_0.bTn && vq_02 != vq_0.bTp) continue;
            return vq_02;
        }
        return vq_0.bTo;
    }
}

