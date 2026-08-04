/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import java.util.ArrayList;

public class WeaponSelectionCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (arrayList.size() < 3 || arrayList.get(2) == null) {
            return;
        }
        short s = Short.valueOf((String)arrayList.get(2));
        adu_0 adu_02 = apN.aDK().aDL();
        ee_2 ee_22 = (ee_2)adu_02.ass().nP();
        if (ee_22 != null && ee_22.Oi() != null) {
            ve_0 ve_02 = (ve_0)ee_22.Oi().p(s);
            if (ve_02 != null) {
                lf_0 lf_02 = new lf_0();
                lf_02.b(ee_22);
                lf_02.a(ve_02);
                lf_02.f(18007);
                acu_1.ara().c(lf_02);
            } else {
                ayd_0 ayd_02 = new ayd_0();
                ayd_02.b(ee_22);
                ayd_02.f(18008);
                acu_1.ara().c(ayd_02);
            }
        }
    }

    public boolean J() {
        return false;
    }
}

