/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.common;

import java.util.ArrayList;

public class MaximizeChatCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (add_1.aOG().kR("chatDialog")) {
            boolean bl2 = azs_0.aLV().getProperty("chat.isMaximize").getBoolean();
            azs_0.aLV().g("chat.isMaximize", !bl2);
        }
    }

    public boolean J() {
        return false;
    }
}

