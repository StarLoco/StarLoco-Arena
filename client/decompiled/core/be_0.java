/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from BE
 */
public class be_0
extends auu_0
implements mx_2 {
    public be_0() {
    }

    protected be_0(be_0 be_02) {
        super(be_02);
    }

    public Object clone() {
        if (this.aId()) {
            return ((be_0)this.G(this.TP())).clone();
        }
        return super.clone();
    }

    public Iterator iterator() {
        if (this.aId()) {
            return ((be_0)this.G(this.TP())).iterator();
        }
        return new qf_0(this.o(this.TP()), this.F(this.TP()).kx());
    }

    public int size() {
        if (this.aId()) {
            return ((be_0)this.G(this.TP())).size();
        }
        return this.F(this.TP()).aOf();
    }

    public boolean dE() {
        return true;
    }
}

