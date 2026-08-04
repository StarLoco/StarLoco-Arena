/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class RemoveIgnoreCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = ((String)arrayList.get(2)).replaceAll("\"", "");
        aer_0 aer_02 = new aer_0();
        aer_02.fo(string);
        apN.aDK().vJ().b(aer_02);
    }

    public boolean J() {
        return false;
    }
}

