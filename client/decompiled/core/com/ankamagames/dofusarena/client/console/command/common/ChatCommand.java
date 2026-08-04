/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.common;

import java.util.ArrayList;

public class ChatCommand
implements MC {
    public static final String SPACE = "space";
    public static final String NULL = "none";

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        Object object;
        boolean bl2 = add_1.aOG().kR("chatDialog");
        String string = null;
        if (arrayList.size() >= 3 && arrayList.get(2) != null) {
            string = (String)arrayList.get(2);
        }
        UV uV = null;
        if (bl2 && (object = add_1.aOG().azj().lh("chatDialog")) != null) {
            uV = (UV)((aji_1)object).R("chatInput");
        }
        if (!(uV != null && lb_2.XL().XM() == uV || string.equalsIgnoreCase(NULL))) {
            object = ((String)arrayList.get(2)).equalsIgnoreCase(SPACE) ? " " : (String)arrayList.get(2);
            azs_0.aLV().getProperty("chat.dialogView").avs();
            azs_0.aLV().c("chat.dialogView", "input", object);
        }
        if (uV != null) {
            lb_2.XL().g(uV);
        }
    }

    public boolean J() {
        return false;
    }
}

