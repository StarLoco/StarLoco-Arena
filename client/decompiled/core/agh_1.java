/*
 * Decompiled with CFR 0.152.
 */
import java.util.ConcurrentModificationException;

/*
 * Renamed from agh
 */
public class agh_1
extends aiz_1 {
    private final sd ctK;

    public agh_1(sd sd2) {
        super(sd2);
        this.ctK = sd2;
    }

    protected final int nextIndex() {
        if (this.cyP != this.cyO.size()) {
            throw new ConcurrentModificationException();
        }
        Object[] objectArray = this.ctK.dxM;
        int n2 = this._index;
        while (n2-- > 0 && (objectArray[n2] == null || objectArray[n2] == adf_2.dxO || objectArray[n2] == adf_2.dxP)) {
        }
        return n2;
    }

    public void fK() {
        this.ays();
    }

    public Object awg() {
        return this.ctK.dxM[this._index];
    }

    public float awh() {
        return this.ctK.aiQ[this._index];
    }

    public float aT(float f) {
        float f2 = this.awh();
        this.ctK.aiQ[this._index] = f;
        return f2;
    }
}

