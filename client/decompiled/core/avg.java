/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;

public abstract class avg
extends aat_0
implements Cloneable {
    protected awq_0 cYz;
    protected boolean cYA = true;

    public boolean aId() {
        return this.cYz != null;
    }

    public void a(awq_0 awq_02) {
        this.cYz = awq_02;
        this.cYA = false;
    }

    protected String aIe() {
        return abm_1.a(this.TP(), this, true);
    }

    protected void aIf() {
        this.N(this.TP());
    }

    protected void N(UI uI) {
        if (this.cYA || !this.aId()) {
            return;
        }
        this.a(new ans_1(this), uI);
    }

    protected void a(Stack stack, UI uI) {
        if (this.cYA || !this.aId()) {
            return;
        }
        Object object = this.cYz.P(uI);
        if (object instanceof avg) {
            ans_1 ans_12 = ans_1.a(stack);
            if (ans_12.contains(object)) {
                throw this.aIj();
            }
            ans_12.push(object);
            ((avg)object).a(ans_12, uI);
            ans_12.pop();
        }
        this.cYA = true;
    }

    public static void a(avg avg2, Stack stack, UI uI) {
        avg2.a(stack, uI);
    }

    protected Object aIg() {
        return this.O(this.TP());
    }

    protected Object O(UI uI) {
        return this.a(this.getClass(), this.aIe(), uI);
    }

    protected Object k(Class clazz, String string) {
        return this.a(clazz, string, this.TP());
    }

    protected Object a(Class clazz, String string, UI uI) {
        if (uI == null) {
            throw new eq_2("No Project specified");
        }
        this.N(uI);
        Object object = this.cYz.P(uI);
        if (!clazz.isAssignableFrom(object.getClass())) {
            this.l("Class " + object.getClass() + " is not a subclass of " + clazz, 3);
            String string2 = this.cYz.aJC() + " doesn't denote a " + string;
            throw new eq_2(string2);
        }
        return object;
    }

    protected eq_2 aIh() {
        return new eq_2("You must not specify more than one attribute when using refid");
    }

    protected eq_2 aIi() {
        return new eq_2("You must not specify nested elements when using refid");
    }

    protected eq_2 aIj() {
        return new eq_2("This data type contains a circular reference.");
    }

    protected boolean isChecked() {
        return this.cYA;
    }

    protected void setChecked(boolean bl2) {
        this.cYA = bl2;
    }

    public awq_0 aIk() {
        return this.cYz;
    }

    protected void aIl() {
        if (this.aId()) {
            throw this.aIh();
        }
    }

    protected void aIm() {
        if (this.aId()) {
            throw this.aIi();
        }
    }

    public String toString() {
        String string = this.getDescription();
        return string == null ? this.aIe() : this.aIe() + " " + string;
    }

    public Object clone() {
        avg avg2 = (avg)super.clone();
        avg2.setDescription(this.getDescription());
        if (this.aIk() != null) {
            avg2.a(this.aIk());
        }
        avg2.setChecked(this.isChecked());
        return avg2;
    }
}

