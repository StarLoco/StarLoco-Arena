/*
 * Decompiled with CFR 0.152.
 */
public class aCk
implements apG {
    private Class ach = awl_0.class;

    public awl_0 kw(String string) {
        return this.l(this.ach, string);
    }

    public awl_0 l(Class clazz, String string) {
        if (string == null) {
            return null;
        }
        if (clazz.equals(awl_0.class)) {
            awl_0 awl_02 = new awl_0();
            aji_1 aji_12 = add_1.aOG().azj().aRR();
            awl_02.d(string, aji_12);
            return awl_02;
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
            return "null";
        }
        if (clazz.equals(awl_0.class)) {
            zp_12.j(awl_0.class);
            zp_12.j(afq_1.class);
            String string2 = zp_12.GQ();
            zp_12.a(new aKI(awl_0.class, string2, "new DropValidateCallBack()"));
            zp_12.a(new aza(null, "setFunc", string2, "\"" + string + "\"", "env.getCurrentElementMap()"));
            return string2;
        }
        return "null";
    }
}

