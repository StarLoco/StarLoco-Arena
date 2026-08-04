/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;
import java.util.HashMap;

public class ListFriendsCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        HashMap hashMap = mc_1.qM().qN();
        if (hashMap != null) {
            String string = aon_0.aYc().getString("chat.friendList");
            string = string + " :\n";
            StringBuilder stringBuilder = new StringBuilder("");
            for (axa_0 axa_02 : hashMap.values()) {
                stringBuilder.append(" +").append(axa_02.getName()).append(" (");
                if (axa_02.uq()) {
                    stringBuilder.append("onLine");
                } else {
                    stringBuilder.append("offLine");
                }
                stringBuilder.append(")\n");
            }
            string = string + stringBuilder.toString();
            zc_0 zc_02 = new zc_0(string);
            zc_02.eD(5);
            ql_1.acX().a(zc_02);
        }
    }

    public boolean J() {
        return false;
    }
}

