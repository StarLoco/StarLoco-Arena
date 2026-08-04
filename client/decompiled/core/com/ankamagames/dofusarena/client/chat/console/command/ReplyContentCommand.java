/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.util.ArrayList;

public class ReplyContentCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        Xk xk = new Xk();
        String string = abl_2.aNI();
        String string2 = avQ.jT((String)arrayList.get(2));
        sj_1 sj_12 = apN.aDK().Ln();
        if (string == null || sj_12 == null || string.equals(sj_12.Ld()) || string2.equals("")) {
            zc_0 zc_02 = new zc_0(aon_0.aYc().getString("error.chat.operationNotPermited"));
            zc_02.eD(4);
            ql_1.acX().a(zc_02);
        } else {
            zc_0 zc_03 = new zc_0(string, sj_12.getId(), string2);
            zc_03.eD(2);
            ql_1.acX().a(zc_03, string);
        }
        xk.setUserName(string);
        xk.k(string2);
        apN.aDK().vJ().b(xk);
    }

    public boolean J() {
        return false;
    }
}

