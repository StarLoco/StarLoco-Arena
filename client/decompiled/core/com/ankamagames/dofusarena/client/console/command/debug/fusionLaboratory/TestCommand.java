/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.fusionLaboratory;

import java.util.ArrayList;

public class TestCommand
implements MC {
    private static final String BL = " ";

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("test")) {
            if (arrayList.size() == 3) {
                cp_0.a(apk_02);
                ahg_0 ahg_02 = new ahg_0();
                String[] stringArray = ((String)arrayList.get(2)).split(BL);
                int[] nArray = new int[stringArray.length];
                for (int j = 0; j < stringArray.length; ++j) {
                    nArray[j] = Integer.parseInt(stringArray[j]);
                }
                ahg_02.e(nArray);
                apN.aDK().vJ().b(ahg_02);
            } else {
                apk_02.err("Mauvais nombre de param\u00e8tres.");
            }
        }
    }

    public boolean J() {
        return false;
    }
}

