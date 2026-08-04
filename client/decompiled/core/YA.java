/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

public class YA
extends ain_2 {
    final /* synthetic */ ano_0 bPX;

    protected YA(ano_0 ano_02) {
        this.bPX = ano_02;
        super(ano_02, null);
    }

    public Iterator iterator() {
        return new ami_1(this.bPX);
    }

    public boolean removeElement(Object object) {
        return null != this.bPX.remove(object);
    }

    public boolean aq(Object object) {
        return this.bPX.contains(object);
    }
}

