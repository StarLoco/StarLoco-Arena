/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class EmoteCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        sj_1 sj_12 = apN.aDK().Ln();
        up_0 up_02 = up_0.cz((String)arrayList.get(1));
        if (up_02 != null && sj_12.aQn().bW(up_02.tI()) != null) {
            if (sj_12.Ov().L().acM()) {
                sj_12.Ov().b(qc_0.hf((sj_12.Ov().L().getIndex() + 1) % 8));
            }
            JY jY = new JY();
            jY.eW(up_02.AU());
            jY.gy(up_02.tI());
            apN.aDK().vJ().b(jY);
        }
    }

    public boolean J() {
        return false;
    }
}

