/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.admin;

import java.util.ArrayList;

public abstract class ConsoleAdminCommand
implements MC {
    private byte bLb;

    protected ConsoleAdminCommand(byte by) {
        this.bLb = by;
    }

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        aFC aFC2 = new aFC();
        aFC2.setCommand((String)arrayList.get(0));
        aFC2.bn(this.bLb);
        axp_0 axp_02 = apN.aDK().vJ();
        if (axp_02 != null) {
            axp_02.b(aFC2);
        } else {
            apk_02.err("Pour acc\u00e9der \u00e0 ces commandes il faut \u00eatre connect\u00e9 !");
        }
    }

    public boolean J() {
        return false;
    }
}

