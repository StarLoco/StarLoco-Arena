/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class AddIgnoreCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = ((String)arrayList.get(2)).replaceAll("\"", "");
        MP mP = new MP();
        mP.fo(string);
        apN.aDK().vJ().b(mP);
    }

    public boolean J() {
        return false;
    }
}

