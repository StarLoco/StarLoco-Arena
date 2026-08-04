/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.script;

import java.util.ArrayList;

public class RunCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string;
        if (arrayList.size() == 3 && (string = (String)arrayList.get(2)) != null) {
            try {
                String string2 = mu_1.rM().getString("scriptPath");
                int n2 = Ky.WG().eY(string2 + string + ".lua").getId();
                apk_02.trace("ID : " + n2);
            }
            catch (aih_2 aih_22) {
                apk_02.err(aih_22.toString());
            }
        }
    }

    public boolean J() {
        return false;
    }
}

