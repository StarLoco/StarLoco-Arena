/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Renamed from av
 */
public class av_0
implements Iterator {
    private final Iterator ck;
    private boolean cl = false;
    private kc_2 cm;
    private boolean cn;
    final /* synthetic */ axw co;

    public av_0(axw axw2) {
        this.co = axw2;
        ArrayList arrayList = new ArrayList();
        amm_1 amm_12 = new amm_1(this, arrayList, axw2);
        axw2.djE.a(amm_12);
        axw2.djF.a(amm_12);
        Iterator iterator = axw2.aKy();
        if (iterator != null) {
            while (iterator.hasNext()) {
                arrayList.add(iterator.next());
            }
        }
        this.ck = arrayList.iterator();
    }

    private boolean a(kc_2 kc_22) {
        long l2 = kc_22.getId();
        if (this.co.djE.v(l2)) {
            return true;
        }
        if (this.co.djF.v(l2)) {
            return true;
        }
        if (this.co.djK != null && this.co.djK.bI(l2)) {
            return true;
        }
        return this.co.ei(l2);
    }

    private boolean aY() {
        this.cl = this.aZ();
        return this.cl;
    }

    private boolean aZ() {
        if (!this.ck.hasNext()) {
            return false;
        }
        kc_2 kc_22 = (kc_2)this.ck.next();
        if (this.a(kc_22)) {
            this.cm = kc_22;
            return true;
        }
        return this.aZ();
    }

    private kc_2 ba() {
        if (!this.cl) {
            throw new NoSuchElementException();
        }
        return this.cm;
    }

    public boolean hasNext() {
        if (this.cn) {
            return this.cl;
        }
        this.cn = true;
        return this.aY();
    }

    public kc_2 bb() {
        if (!this.cn) {
            this.aY();
        }
        this.cn = false;
        return this.ba();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

