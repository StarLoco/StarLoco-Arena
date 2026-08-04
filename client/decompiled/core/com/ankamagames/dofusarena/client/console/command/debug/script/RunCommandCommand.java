/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.script;

import java.util.ArrayList;

public class RunCommandCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string;
        if (arrayList.size() == 3 && (string = (String)arrayList.get(2)) != null) {
            int n2 = Ky.WG().eZ(string).getId();
            apk_02.trace("ID : " + n2);
        }
    }

    public boolean J() {
        return false;
    }
}

