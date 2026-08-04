/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.saves;

import java.util.ArrayList;
import java.util.Arrays;

public class loadCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("load")) {
            lp_0 lp_02 = new lp_0("serializableFightParameterCoachCards.loadCommand.test");
            if (br.a(lp_02)) {
                apk_02.log("Donn\u00e9es charg\u00e9es : " + Arrays.toString(lp_02.qi()));
            } else {
                apk_02.err("Donn\u00e9es non charg\u00e9es.");
            }
        }
    }

    public boolean J() {
        return false;
    }
}

