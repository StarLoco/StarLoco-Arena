/*
 * Decompiled with CFR 0.152.
 */
import java.util.Map;

class aNi
extends ii_2 {
    akn_1 bEB;
    akn_1 dYY;
    final ki_1 dYZ;
    final Map dZa;

    aNi(ki_1 ki_12, Map map) {
        this.dYZ = ki_12;
        this.dZa = map;
    }

    akn_1 aXq() {
        this.dYY = null;
        this.bEB = null;
        ki_1 ki_12 = this.dYZ;
        while (ki_12 != null) {
            switch (ki_12.type) {
                case 0: {
                    this.h(new aFx((String)ki_12.getValue()));
                    break;
                }
                case 2: {
                    qS qS2 = (qS)ki_12;
                    xz_2 xz_22 = new xz_2();
                    xz_22.b(qS2.aCu());
                    aNi aNi2 = new aNi(qS2.we(), this.dZa);
                    aNi2.a(this.Pb);
                    akn_1 akn_12 = aNi2.aXq();
                    xz_22.c(akn_12);
                    this.h(xz_22);
                    break;
                }
                case 1: {
                    awg_0 awg_02 = (awg_0)ki_12;
                    aci_2 aci_22 = this.a(awg_02);
                    if (aci_22 != null) {
                        aci_22.b(awg_02.aCu());
                        aci_22.n(awg_02.aJo());
                        this.h(aci_22);
                        break;
                    }
                    aFx aFx2 = new aFx("%PARSER_ERROR_" + awg_02.getValue());
                    this.b(new aIX("[" + awg_02.getValue() + "] is not a valid conversion word", this));
                    this.h(aFx2);
                }
            }
            ki_12 = ki_12.Fm;
        }
        return this.bEB;
    }

    private void h(akn_1 akn_12) {
        if (this.bEB == null) {
            this.bEB = this.dYY = akn_12;
        } else {
            this.dYY.g(akn_12);
            this.dYY = akn_12;
        }
    }

    aci_2 a(awg_0 awg_02) {
        String string = (String)awg_02.getValue();
        String string2 = (String)this.dZa.get(string);
        if (string2 != null) {
            try {
                return (aci_2)dh_2.a(string2, aci_2.class, this.Pb);
            }
            catch (Exception exception) {
                this.e("Failed to instantiate converter class [" + string2 + "]", exception);
                return null;
            }
        }
        this.eg("There is no conversion class registered for conversion word [" + string + "]");
        return null;
    }
}

