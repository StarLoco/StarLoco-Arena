/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class RemoveFriendCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = ((String)arrayList.get(2)).replaceAll("\"", "");
        aym_0 aym_02 = new aym_0();
        aym_02.fK(string);
        apN.aDK().vJ().b(aym_02);
    }

    public boolean J() {
        return false;
    }
}

