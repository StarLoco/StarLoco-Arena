/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.Constructor;

/*
 * Renamed from asd
 */
class asd_0
extends adi_0 {
    final Constructor cRa;
    private final we_1 azp;

    public asd_0(we_1 we_12, Constructor constructor) {
        super(we_12);
        this.azp = we_12;
        this.cRa = constructor;
    }

    public amf ib() {
        int n2 = this.cRa.getModifiers();
        return we_1.ee(n2);
    }

    public asn[] iy() {
        asn[] asnArray = we_1.a(this.azp, this.cRa.getParameterTypes());
        asn asn2 = this.azp.aFp();
        if (asn2 != null) {
            if (asnArray.length < 1) {
                throw new ajy_2("Constructor \"" + this.cRa + "\" lacks synthetic enclosing instance parameter", null);
            }
            if (asnArray[0] != asn2) {
                throw new ajy_2("Enclosing instance parameter of constructor \"" + this.cRa + "\" has wrong type -- \"" + asnArray[0] + "\" vs. \"" + asn2 + "\"", null);
            }
            asn[] asnArray2 = new asn[asnArray.length - 1];
            System.arraycopy(asnArray, 1, asnArray2, 0, asnArray2.length);
            asnArray = asnArray2;
        }
        return asnArray;
    }

    public String getDescriptor() {
        Class<?>[] classArray = this.cRa.getParameterTypes();
        String[] stringArray = new String[classArray.length];
        for (int j = 0; j < stringArray.length; ++j) {
            stringArray[j] = sA.cb(classArray[j].getName());
        }
        return new cc_2(stringArray, "V").toString();
    }

    public asn[] iz() {
        return we_1.a(this.azp, this.cRa.getExceptionTypes());
    }
}

