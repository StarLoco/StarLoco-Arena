/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.saves;

import java.util.ArrayList;
import java.util.Arrays;

public class saveCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("save")) {
            lp_0 lp_02 = new lp_0("serializableFightParameterCoachCards.loadCommand.test");
            int[] nArray = new int[]{554, 557};
            lp_02.e(nArray);
            if (br.b(lp_02)) {
                apk_02.log("Donn\u00e9es sauv\u00e9es : " + Arrays.toString(nArray));
            } else {
                apk_02.err("Donn\u00e9es non sauv\u00e9es.");
            }
        }
    }

    public boolean J() {
        return false;
    }
}

