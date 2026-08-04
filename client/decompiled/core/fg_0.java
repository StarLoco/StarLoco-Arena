/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from FG
 */
public class fg_0
extends sr_0 {
    private static final aag_2[] aVk = new aag_2[]{new aag_2(1024, 768, 32, ""), new aag_2(1280, 720, 32, "16/9"), new aag_2(1280, 800, 32, "Wide"), new aag_2(1280, 1024, 32, ""), new aag_2(1360, 768, 32, "16/9"), new aag_2(1680, 1050, 32, "Wide")};

    public void zc() {
        super.zc();
        this.b((ro_2)adc_0.clT, true);
        this.b((ro_2)adc_0.clQ, false);
        this.b((ro_2)adc_0.clR, true);
        this.b((ro_2)adc_0.clS, false);
        this.b((ro_2)adc_0.clU, false);
        this.b((ro_2)adc_0.cma, 101);
        this.b((ro_2)adc_0.cmb, 0);
        this.b((ro_2)adc_0.clV, true);
        this.b((ro_2)adc_0.clW, false);
        this.b((ro_2)adc_0.clZ, true);
        this.b((ro_2)adc_0.clX, true);
        this.b((ro_2)adc_0.clY, 1);
    }

    public String[] getFields() {
        adc_0[] adc_0Array = adc_0.values();
        akz_1[] akz_1Array = akz_1.values();
        String[] stringArray = new String[adc_0Array.length + akz_1Array.length];
        int n2 = 0;
        for (adc_0 enum_ : adc_0Array) {
            stringArray[n2] = enum_.getKey();
            ++n2;
        }
        for (Enum enum_ : akz_1Array) {
            stringArray[n2] = ((akz_1)enum_).getKey();
            ++n2;
        }
        return stringArray;
    }

    public Object getFieldValue(String string) {
        if (string.equals(adc_0.clK.getKey())) {
            return this.a(adc_0.clK);
        }
        if (string.equals(adc_0.clL.getKey())) {
            return this.f(adc_0.clL);
        }
        if (string.equals(adc_0.clM.getKey())) {
            return this.f(adc_0.clM);
        }
        if (string.equals(adc_0.clN.getKey())) {
            return this.a(adc_0.clN);
        }
        if (string.equals(adc_0.clO.getKey())) {
            return this.a(adc_0.clO);
        }
        if (string.equals(adc_0.clP.getKey())) {
            return this.Pi();
        }
        if (string.equals(adc_0.clT.getKey())) {
            return this.a(adc_0.clT);
        }
        if (string.equals(adc_0.clQ.getKey())) {
            return this.a(adc_0.clQ);
        }
        if (string.equals(adc_0.clR.getKey())) {
            return this.a(adc_0.clR);
        }
        if (string.equals(adc_0.clS.getKey())) {
            return this.a(adc_0.clS);
        }
        if (string.equals(adc_0.clU.getKey())) {
            return this.a(adc_0.clU);
        }
        if (string.equals(adc_0.clV.getKey())) {
            return this.a(adc_0.clV);
        }
        if (string.equals(adc_0.clW.getKey())) {
            return this.a(adc_0.clW);
        }
        if (string.equals(adc_0.clX.getKey())) {
            return this.a(adc_0.clX);
        }
        if (string.equals(adc_0.clY.getKey())) {
            return this.d(adc_0.clY);
        }
        if (string.equals(adc_0.cmb.getKey())) {
            return this.d(adc_0.cmb);
        }
        if (string.equals(adc_0.clZ.getKey())) {
            return this.a(adc_0.clZ);
        }
        return super.getFieldValue(string);
    }

    public boolean Ph() {
        try {
            return mu_1.rM().getBoolean("activateMapParticles");
        }
        catch (aih_2 aih_22) {
            return false;
        }
    }

    public void bp(boolean bl2) {
        mu_1.rM().setBoolean("activateMapParticles", bl2);
        mu_1.rM().rQ();
    }

    public int[] Pi() {
        try {
            String string = this.f(adc_0.clP);
            String[] stringArray = string.split(",");
            int[] nArray = new int[stringArray.length];
            for (int j = 0; j < stringArray.length; ++j) {
                nArray[j] = Integer.parseInt(stringArray[j]);
            }
            return nArray;
        }
        catch (Exception exception) {
            return null;
        }
    }
}

