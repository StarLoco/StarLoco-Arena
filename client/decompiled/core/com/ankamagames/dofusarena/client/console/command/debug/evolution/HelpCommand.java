/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.evolution;

import java.util.ArrayList;

public class HelpCommand
implements MC {
    private static final String sI = "Usage : setNotReady creatorCoachId teamPresetId\n     Change le statut \u00e0 non pr\u00eat dans le mode \u00e9volution d'une \u00e9quipe donn\u00e9e.\n\nUsage : setReady creatorCoachId teamPresetId\n     Change le statut \u00e0 pr\u00eat dans le mode \u00e9volution d'une \u00e9quipe donn\u00e9e.\n";

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("help") && arrayList.size() == 2) {
            if (apk_02 == null) {
                add_1.aOG().f(sI, 102, 1);
            } else {
                apk_02.log(sI);
            }
        }
    }

    public boolean J() {
        return false;
    }
}

