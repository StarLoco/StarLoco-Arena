/*
 * Decompiled with CFR 0.152.
 */
import java.util.ConcurrentModificationException;
import java.util.Map;

/*
 * Renamed from acc
 */
final class acc_1
implements Map.Entry {
    private Object key;
    private Object cjg;
    private final int index;
    final /* synthetic */ ano_0 bPX;

    acc_1(ano_0 ano_02, Object object, Object object2, int n2) {
        this.bPX = ano_02;
        this.key = object;
        this.cjg = object2;
        this.index = n2;
    }

    void av(Object object) {
        this.key = object;
    }

    void aw(Object object) {
        this.cjg = object;
    }

    public Object getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.cjg;
    }

    public Object setValue(Object object) {
        if (this.bPX.iN[this.index] != this.cjg) {
            throw new ConcurrentModificationException();
        }
        this.bPX.iN[this.index] = object;
        this.cjg = object = this.cjg;
        return object;
    }

    public boolean equals(Object object) {
        if (object instanceof Map.Entry) {
            acc_1 acc_12 = this;
            Map.Entry entry = (Map.Entry)object;
            return (acc_12.getKey() == null ? entry.getKey() == null : acc_12.getKey().equals(entry.getKey())) && (acc_12.getValue() == null ? entry.getValue() == null : acc_12.getValue().equals(entry.getValue()));
        }
        return false;
    }

    public int hashCode() {
        return (this.getKey() == null ? 0 : this.getKey().hashCode()) ^ (this.getValue() == null ? 0 : this.getValue().hashCode());
    }
}

