/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import java.util.ArrayList;

public class TurnFighterCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        int n2;
        if (arrayList.size() < 3 || arrayList.get(2) == null) {
            return;
        }
        qc_0 qc_02 = qc_0.hf(Integer.valueOf((String)arrayList.get(2)));
        switch (qc_02) {
            case bEQ: {
                n2 = 18005;
                break;
            }
            case bEO: {
                n2 = 18004;
                break;
            }
            case bEK: {
                n2 = 18002;
                break;
            }
            case bEM: {
                n2 = 18003;
                break;
            }
            default: {
                return;
            }
        }
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null) {
            ee_2 ee_22 = null;
            if (adu_02.Zy() == ko_2.bpv) {
                ee_22 = (ee_2)adu_02.ass().nP();
            }
            if (adu_02.Zy() == ko_2.bpt) {
                ee_22 = azL.aMm().aMn();
            }
            if (ee_22 != null) {
                ayd_0 ayd_02 = new ayd_0();
                ayd_02.b(ee_22);
                ayd_02.f(n2);
                acu_1.ara().c(ayd_02);
            }
        }
    }

    public boolean J() {
        return false;
    }
}

