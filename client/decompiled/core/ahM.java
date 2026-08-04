/*
 * Decompiled with CFR 0.152.
 */
public class ahM
implements apG {
    private Class ach = ag_0.class;

    public ag_0 ik(String string) {
        return this.i(this.ach, string);
    }

    public ag_0 i(Class clazz, String string) {
        if (string == null) {
            return null;
        }
        if (clazz.equals(ag_0.class)) {
            ag_0 ag_02 = new ag_0();
            afq_1 afq_12 = add_1.aOG().azj();
            aji_1 aji_12 = afq_12.aRR();
            Ur ur = afq_12.aRT();
            ag_02.a(string, aji_12, ur);
            return ag_02;
        }
        return null;
    }

    public Class uk() {
        return this.ach;
    }

    public boolean ul() {
        return true;
    }

    public boolean um() {
        return true;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        if (string == null) {
            return null;
        }
        if (clazz.equals(ag_0.class)) {
            String string2 = zp_12.GQ();
            zp_12.j(clazz);
            zp_12.j(afq_1.class);
            zp_12.a(new aKI(clazz, string2, "new " + clazz.getSimpleName() + "()"));
            zp_12.a(new aza(null, "setFunc", string2, "\"" + string + "\"", "env.getCurrentElementMap()", "env.getCurrentForm()"));
            return string2;
        }
        return null;
    }
}

