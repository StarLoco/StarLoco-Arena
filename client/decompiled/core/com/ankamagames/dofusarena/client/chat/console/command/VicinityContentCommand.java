/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class VicinityContentCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        Object object;
        String string = (String)arrayList.get(0);
        sj_1 sj_12 = apN.aDK().Ln();
        if (sj_12 != null) {
            if ((string = avQ.jT(string)).equals("")) {
                object = aon_0.aYc().getString("error.chat.operationNotPermited");
                zc_0 zc_02 = new zc_0((String)object);
                zc_02.eD(4);
                ql_1.acX().a(zc_02);
            } else {
                object = new zc_0(sj_12.Ld(), sj_12.getId(), string);
                ((zc_0)object).eD(1);
                ql_1.acX().a((zc_0)object);
            }
        }
        object = new bb_0();
        ((bb_0)object).k(string);
        apN.aDK().vJ().b((pr_0)object);
    }

    public boolean J() {
        return false;
    }
}

