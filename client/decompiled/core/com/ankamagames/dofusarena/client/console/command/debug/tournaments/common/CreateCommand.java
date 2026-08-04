/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class CreateCommand
implements MC {
    private static final String dfU = " ";

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("create")) {
            if (arrayList.size() == 3) {
                ajp_0.a(apk_02);
                ayQ ayQ2 = new ayQ();
                String[] stringArray = ((String)arrayList.get(2)).split(dfU);
                if (stringArray.length == 2) {
                    short s = Short.parseShort(stringArray[0]);
                    String string2 = stringArray[1];
                    ayQ2.ca(s);
                    ayQ2.setName(string2);
                    apN.aDK().vJ().b(ayQ2);
                } else {
                    apk_02.err("Mauvais nombre de param\u00e8tres.");
                }
            } else {
                apk_02.err("Mauvais nombre de param\u00e8tres.");
            }
        }
    }

    public boolean J() {
        return false;
    }
}

