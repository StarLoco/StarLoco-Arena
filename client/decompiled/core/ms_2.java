/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;

/*
 * Renamed from ms
 */
public abstract class ms_2
implements ff_0 {
    private final asn zn;

    public ms_2(asn asn2) {
        this.zn = asn2;
    }

    public abstract amf ib();

    public asn ic() {
        return this.zn;
    }

    public abstract asn[] iy();

    public abstract String getDescriptor();

    public abstract asn[] iz();

    public boolean a(ms_2 ms_22) {
        Object[] objectArray = this.iy();
        Object[] objectArray2 = ms_22.iy();
        for (int j = 0; j < objectArray.length; ++j) {
            if (objectArray2[j].g((asn)objectArray[j])) continue;
            return false;
        }
        return !Arrays.equals(objectArray, objectArray2);
    }

    public boolean b(ms_2 ms_22) {
        return ms_22.a(this);
    }

    public abstract String toString();
}

