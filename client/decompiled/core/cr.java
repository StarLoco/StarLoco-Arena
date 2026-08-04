/*
 * Decompiled with CFR 0.152.
 */
public final class cr
extends akE {
    public final jy_2 ij;

    public cr(jy_2 jy_22) {
        super(jy_22.aP());
        if (!(jy_22 instanceof ayN || jy_22 instanceof afa_1 || jy_22 instanceof La || jy_22 instanceof ajs_2 || jy_22 instanceof Nl || jy_22 instanceof afi_2)) {
            String string = jy_22.getClass().getName();
            string = string.substring(string.lastIndexOf(46) + 1);
            this.j(string + " is not allowed as an expression statement. " + "Expressions statements must be one of assignments, method invocations, or object allocations.");
        }
        this.ij = jy_22;
        this.ij.a(this);
    }

    public String toString() {
        return this.ij.toString() + ';';
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

