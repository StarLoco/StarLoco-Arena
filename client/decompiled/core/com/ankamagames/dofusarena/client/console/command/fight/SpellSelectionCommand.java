/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import java.util.ArrayList;
import org.apache.log4j.Logger;

public class SpellSelectionCommand
implements MC {
    protected static final Logger a = Logger.getLogger(SpellSelectionCommand.class);

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        int n2;
        if (arrayList.size() < 3 || arrayList.get(2) == null) {
            return;
        }
        try {
            n2 = Integer.valueOf((String)arrayList.get(2));
        }
        catch (NumberFormatException numberFormatException) {
            return;
        }
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null && adu_02.ass() != null) {
            ee_2 ee_22 = (ee_2)adu_02.ass().nP();
            if (ee_22 != null) {
                ajv_2 ajv_22 = ee_22.Oh();
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
            } else {
                a.error((Object)"Pas de fighter");
            }
        } else if (adu_02 == null) {
            a.error((Object)"Pas de fight");
        } else {
            a.error((Object)"Pas de timeLine");
        }
    }

    public boolean J() {
        return false;
    }
}

