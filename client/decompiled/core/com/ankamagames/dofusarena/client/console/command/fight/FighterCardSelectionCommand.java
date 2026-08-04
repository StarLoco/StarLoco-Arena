/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import java.util.ArrayList;

public class FighterCardSelectionCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        int n2;
        if (arrayList.size() < 3 || arrayList.get(2) == null) {
            return;
        }
        try {
            n2 = Short.valueOf((String)arrayList.get(2)).shortValue();
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
        adu_0 adu_02 = apN.aDK().aDL();
        ee_2 ee_22 = (ee_2)adu_02.ass().nP();
        ajv_2 ajv_22 = ee_22.Oj();
        yp_2 yp_22 = null;
        int n3 = 0;
        for (yp_2 yp_23 : ajv_22) {
            if (n3 == n2) {
                yp_22 = yp_23;
                break;
            }
            ++n3;
        }
        if (yp_22 != null) {
            da_1 da_12 = new da_1();
            da_12.b(ee_22);
            da_12.a(yp_22);
            da_12.f(18006);
            acu_1.ara().c(da_12);
        }
    }

    public boolean J() {
        return false;
    }
}

