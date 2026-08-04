/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.Map;

public class UD
extends ain_2 {
    final /* synthetic */ ano_0 bPX;

    protected UD(ano_0 ano_02) {
        this.bPX = ano_02;
        super(ano_02, null);
    }

    public Iterator iterator() {
        return new aqd_0(this, this.bPX);
    }

    public boolean a(Map.Entry entry) {
        Object object;
        Object object2 = this.d(entry);
        int n2 = this.bPX.index(object2);
        if (n2 >= 0 && ((object = this.c(entry)) == this.bPX.iN[n2] || null != object && object.equals(this.bPX.iN[n2]))) {
            this.bPX.O(n2);
            return true;
        }
        return false;
    }

    public boolean b(Map.Entry entry) {
        Object object = this.bPX.get(this.d(entry));
        Object v = entry.getValue();
        return v == object || null != object && object.equals(v);
    }

    protected Object c(Map.Entry entry) {
        return entry.getValue();
    }

    protected Object d(Map.Entry entry) {
        return entry.getKey();
    }
}

