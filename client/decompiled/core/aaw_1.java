/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Renamed from aaw
 */
public class aaw_1
extends alp {
    private volatile long hf;

    private void noop() {
        di.a(null, 1);
    }

    protected aaw_1(long l2, boolean bl2) {
        super(XugglerJNI.SWIGIMetaDataUpcast(l2), bl2);
        this.hf = l2;
    }

    protected aaw_1(long l2, boolean bl2, AtomicLong atomicLong) {
        super(XugglerJNI.SWIGIMetaDataUpcast(l2), bl2, atomicLong);
        this.hf = l2;
    }

    public static long b(aaw_1 aaw_12) {
        if (aaw_12 == null) {
            return 0L;
        }
        return aaw_12.dv();
    }

    public long dv() {
        if (this.hf == 0L) {
            throw new IllegalStateException("underlying native object already deleted");
        }
        return this.hf;
    }

    public aaw_1 aoY() {
        if (this.hf == 0L) {
            return null;
        }
        return new aaw_1(this.hf, this.hg, this.aAJ());
    }

    public boolean equals(Object object) {
        boolean bl2 = false;
        if (object instanceof aaw_1) {
            bl2 = ((aaw_1)object).hf == this.hf;
        }
        return bl2;
    }

    public int hashCode() {
        return (int)this.hf;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName()).append("@").append(this.hashCode());
        stringBuilder.append("[");
        Collection collection = this.aoZ();
        for (String string : collection) {
            String string2 = this.a(string, sd_2.akN);
            stringBuilder.append(string).append("=").append(string2).append(";");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Collection aoZ() {
        int n2 = this.apa();
        ArrayList<String> arrayList = new ArrayList<String>(n2);
        for (int j = 0; j < this.apa(); ++j) {
            String string = this.getKey(j);
            if (string == null || string.length() <= 0) continue;
            arrayList.add(string);
        }
        return arrayList;
    }

    public String getValue(String string) {
        return this.a(string, sd_2.akN);
    }

    private int apa() {
        return XugglerJNI.IMetaData_getNumKeys(this.hf, this);
    }

    private String getKey(int n2) {
        return XugglerJNI.IMetaData_getKey(this.hf, this, n2);
    }

    public String a(String string, sd_2 sd_22) {
        return XugglerJNI.IMetaData_getValue(this.hf, this, string, sd_22.dZ());
    }

    public int R(String string, String string2) {
        return XugglerJNI.IMetaData_setValue(this.hf, this, string, string2);
    }

    public static aaw_1 apb() {
        long l2 = XugglerJNI.IMetaData_make();
        return l2 == 0L ? null : new aaw_1(l2, false);
    }
}

