/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.client.proxyclient.base.console.command;

import java.util.ArrayList;

public class HelpCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        Object object22;
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        ArrayList<Object> arrayList3 = new ArrayList<Object>();
        ArrayList arrayList4 = apk_02.aDF().getChildren();
        for (Object object22 : arrayList4) {
            if (((adb_2)object22).BK() > apk_02.aDC()) continue;
            if (object22 instanceof aiw_2) {
                arrayList2.add(object22);
                continue;
            }
            arrayList3.add(object22);
        }
        ArrayList arrayList5 = apk_02.aDD().getChildren();
        object22 = arrayList5.iterator();
        while (object22.hasNext()) {
            adb_2 adb_23 = (adb_2)object22.next();
            if (adb_23.BK() > apk_02.aDC()) continue;
            if (adb_23 instanceof aiw_2) {
                arrayList2.add(adb_23);
                continue;
            }
            arrayList3.add(adb_23);
        }
        object22 = new StringBuilder("# Liste des commandes #\n");
        for (adb_2 adb_24 : arrayList2) {
            ((StringBuilder)object22).append("[").append(adb_24.getName()).append("] ");
        }
        for (adb_2 adb_23 : arrayList3) {
            ((StringBuilder)object22).append(adb_23.getName()).append(" ");
        }
        apk_02.trace(((StringBuilder)object22).toString());
    }

    public boolean J() {
        return false;
    }
}

