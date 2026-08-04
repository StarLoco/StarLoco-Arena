/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class azV
extends DM {
    public final List doP = new ArrayList();
    public final List doQ = new ArrayList();
    final SortedMap doR = new TreeMap();

    public azV(lc_0 lc_02, short s) {
        super(lc_02, s);
    }

    public void f(acc_0 acc_02) {
        this.doP.add(acc_02);
        acc_02.a(this);
    }

    public void a(aR aR2) {
        this.doQ.add(aR2);
        aR2.a(this);
        if (this.aOl != null) {
            this.aOl.aFn();
        }
    }

    public void a(jz_0 jz_02) {
        if (!(this instanceof eb_0)) {
            throw new aHY();
        }
        jz_0 jz_03 = (jz_0)this.doR.get(jz_02.getName());
        if (jz_03 != null) {
            if (jz_02.tF() != jz_03.tF()) {
                throw new aHY();
            }
            return;
        }
        this.doR.put(jz_02.getName(), jz_02);
    }

    acc_0[] aMx() {
        if (this.doP.isEmpty()) {
            acc_0 acc_02 = new acc_0(this.aP(), null, 1, new anb_1[0], new atu_0[0], null, Collections.EMPTY_LIST);
            acc_02.a(this);
            return new acc_0[]{acc_02};
        }
        return this.doP.toArray(new acc_0[this.doP.size()]);
    }
}

