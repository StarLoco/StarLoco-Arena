/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.fusionLaboratory;

import java.util.ArrayList;

public class HelpCommand
implements MC {
    private static final String sI = "Usage : test specialReferenceCoachCardId referenceCardIds\n     Teste la fusion de la liste de cartes en tenant compte de la carte sp\u00e9ciale.\n\n";

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

