/*
 * Decompiled with CFR 0.152.
 */
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * Renamed from aan
 */
public final class aan_2
implements aqi_0 {
    CopyOnWriteArrayList ceX = new CopyOnWriteArrayList();

    public void a(ajs_1 ajs_12) {
        this.ceX.add(ajs_12);
    }

    public ajs_1 Ud() {
        if (this.ceX.size() > 0) {
            return (ajs_1)this.ceX.get(0);
        }
        return null;
    }

    public void Ue() {
        this.ceX.clear();
    }

    public vq_0 U(Object object) {
        for (ajs_1 ajs_12 : this.ceX) {
            vq_0 vq_02 = ajs_12.aC(object);
            if (vq_02 != vq_0.bTn && vq_02 != vq_0.bTp) continue;
            return vq_02;
        }
        return vq_0.bTo;
    }
}

