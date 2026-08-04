/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.xulor;

import java.util.ArrayList;

public class UnloadCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        if (!arrayList.isEmpty()) {
            String string = (String)arrayList.get(2);
            if (add_1.aOG().kR(string)) {
                add_1.aOG().kO((String)arrayList.get(2));
            } else {
                apk_02.err(string + " n'est pas charg\u00e9 !");
            }
        }
    }

    public boolean J() {
        return false;
    }
}

