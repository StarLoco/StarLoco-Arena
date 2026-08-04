/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Bu
 */
class bu_2
implements aji_0 {
    acv_2 aci;

    bu_2() {
    }

    public void a(mT mT2, boolean bl2) {
        if (bl2) {
            if (mT2 instanceof aez_0) {
                aez_0 aez_02 = (aez_0)mT2;
                ca_0 ca_02 = aez_02.aPY();
                String string = "";
                if (ca_02 != null && ca_02.Kf() >= 5L) {
                    string = ca_02.hd();
                }
                short s = aez_02.tw();
                String string2 = "";
                if (s != 0) {
                    string2 = aez_02.lZ() == 0 ? aon_0.aYc().a(56, s, new Object[0]) : aon_0.aYc().a(57, s, new Object[0]);
                }
                String string3 = aez_02.Ld() + "\n" + add_1.aOG().kE("elite") + " : " + Math.max(1, aez_02.aQh()) + "\n" + add_1.aOG().kE("evolution") + " : " + Math.max(1, aez_02.aQi());
                if (!string2.equals("")) {
                    string3 = string3 + "\n<" + string2 + ">";
                }
                if (string != null && !string.equals("")) {
                    string3 = string3 + "\n[" + string + "]";
                }
                this.aci = new acv_2(string3);
                float[] fArray = xg_2.c(aez_02);
                if (fArray != xg_2.bYU) {
                    this.aci.setColor(fArray[0], fArray[1], fArray[2], fArray[3]);
                }
                this.aci.c(mT2);
                wj_2.Df().a(this.aci);
            }
        } else if (this.aci != null) {
            wj_2.Df().b(this.aci);
        }
    }
}

