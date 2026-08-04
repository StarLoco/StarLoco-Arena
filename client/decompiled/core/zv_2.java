/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from Zv
 */
public class zv_2
extends auu_0
implements mx_2 {
    public zv_2() {
    }

    protected zv_2(zv_2 zv_22) {
        super(zv_22);
    }

    public Object clone() {
        if (this.aId()) {
            return ((zv_2)this.G(this.TP())).clone();
        }
        return super.clone();
    }

    public Iterator iterator() {
        if (this.aId()) {
            return ((zv_2)this.G(this.TP())).iterator();
        }
        return new qf_0(this.o(this.TP()), this.F(this.TP()).kw());
    }

    public int size() {
        if (this.aId()) {
            return ((zv_2)this.G(this.TP())).size();
        }
        return this.F(this.TP()).aOh();
    }

    public boolean dE() {
        return true;
    }

    public String toString() {
        abs_0 abs_02 = this.F(this.TP());
        String[] stringArray = abs_02.kw();
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < stringArray.length; ++j) {
            if (j > 0) {
                stringBuffer.append(';');
            }
            stringBuffer.append(stringArray[j]);
        }
        return stringBuffer.toString();
    }
}

