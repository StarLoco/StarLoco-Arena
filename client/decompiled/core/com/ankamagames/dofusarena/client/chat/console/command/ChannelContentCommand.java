/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class ChannelContentCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        Object object;
        sj_1 sj_12 = apN.aDK().Ln();
        if (sj_12 != null) {
            object = new zc_0(sj_12.Ld(), (String)arrayList.get(3));
            ((zc_0)object).eD(3);
            ql_1.acX().a((zc_0)object, (String)arrayList.get(2));
        }
        object = new acS();
        ((acS)object).hv((String)arrayList.get(2));
        ((acS)object).k((String)arrayList.get(3));
        apN.aDK().vJ().b((pr_0)object);
    }

    public boolean J() {
        return false;
    }
}

