/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

/*
 * Renamed from aNt
 */
public class ant_2
extends avg {
    private Vector dZm = new Vector();

    public ant_2() {
    }

    public ant_2(wb_2[] wb_2Array) {
        for (int j = 0; j < wb_2Array.length; ++j) {
            this.a(wb_2Array[j]);
        }
    }

    public void a(wb_2 wb_22) {
        if (this.aId()) {
            throw this.aIi();
        }
        if (wb_22 == null) {
            return;
        }
        this.dZm.add(wb_22);
        this.setChecked(false);
    }

    public boolean lp() {
        if (this.aId()) {
            return ((ant_2)this.aIg()).lp();
        }
        this.aIf();
        return !this.dZm.isEmpty();
    }

    public int lq() {
        if (this.aId()) {
            return ((ant_2)this.aIg()).lq();
        }
        this.aIf();
        return this.dZm.size();
    }

    public Iterator aXv() {
        if (this.aId()) {
            return ((ant_2)this.aIg()).aXv();
        }
        this.aIf();
        return Collections.unmodifiableList(this.dZm).iterator();
    }

    protected void a(Stack stack, UI uI) {
        if (this.isChecked()) {
            return;
        }
        if (this.aId()) {
            super.a(stack, uI);
        } else {
            Iterator iterator = this.dZm.iterator();
            while (iterator.hasNext()) {
                Object e = iterator.next();
                if (!(e instanceof avg)) continue;
                stack.push(e);
                ant_2.a((avg)e, stack, uI);
            }
            this.setChecked(true);
        }
    }
}

