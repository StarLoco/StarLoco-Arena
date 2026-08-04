/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class PrivateContentCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        Object object;
        String string = ((String)arrayList.get(2)).trim().replaceAll("\"", "");
        String string2 = (String)arrayList.get(3);
        sj_1 sj_12 = apN.aDK().Ln();
        if (sj_12 != null) {
            if ((string2 = avQ.jT(string2)).equals("")) {
                object = aon_0.aYc().getString("error.chat.operationNotPermited");
                zc_0 zc_02 = new zc_0((String)object);
                zc_02.eD(4);
                ql_1.acX().a(zc_02);
            } else if (!string.equals(sj_12.Ld())) {
                object = new zc_0(string, sj_12.getId(), string2);
                ((zc_0)object).eD(2);
                ql_1.acX().a((zc_0)object, string);
            }
        }
        object = new Xk();
        ((Xk)object).setUserName(string);
        ((Xk)object).k(string2);
        apN.aDK().vJ().b((pr_0)object);
    }

    public boolean J() {
        return false;
    }
}

