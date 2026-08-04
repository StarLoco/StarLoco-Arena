/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public class aeb
extends ain_2 {
    final /* synthetic */ ano_0 bPX;

    protected aeb(ano_0 ano_02) {
        this.bPX = ano_02;
        super(ano_02, null);
    }

    public Iterator iterator() {
        return new kj_0(this, this.bPX);
    }

    public boolean aq(Object object) {
        return this.bPX.containsValue(object);
    }

    public boolean removeElement(Object object) {
        Object[] objectArray = this.bPX.iN;
        Object[] objectArray2 = this.bPX.dxM;
        int n2 = objectArray.length;
        while (n2-- > 0) {
            if ((objectArray2[n2] == adf_2.dxP || objectArray2[n2] == adf_2.dxO || object != objectArray[n2]) && (null == objectArray[n2] || !objectArray[n2].equals(object))) continue;
            this.bPX.O(n2);
            return true;
        }
        return false;
    }
}

