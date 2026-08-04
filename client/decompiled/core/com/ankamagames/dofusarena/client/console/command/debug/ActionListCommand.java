/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActionListCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        LinkedList linkedList = vr_0.aiM().aiP();
        stringBuilder.append(linkedList.size()).append(" groupes d'action dans la pile.\n");
        if (linkedList.size() > 0) {
            akb_2 akb_22 = (akb_2)linkedList.peek();
            stringBuilder.append("Actions dans le groupe en haut de la pile : \n");
            for (Eq eq : akb_22.aVH()) {
                stringBuilder.append(eq.getClass().getSimpleName()).append(" ").append(eq.M()).append(" (").append(eq.Ao()).append(")\n");
            }
        }
        apk_02.trace(stringBuilder.toString());
    }

    public boolean J() {
        return false;
    }
}

