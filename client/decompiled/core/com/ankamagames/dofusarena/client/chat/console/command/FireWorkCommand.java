/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.dofusarena.client.chat.console.command;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class FireWorkCommand
implements MC {
    protected static Logger a = Logger.getLogger(FireWorkCommand.class);
    public static final int diZ = iu_0.bhc.byteValue();
    public static final int dja = 1;
    public static final int djb = diZ * 1 * 1000;
    public static final la_0 djc = la_0.XJ();
    public static final iu_0 djd = iu_0.Ut();
    private static long dje = System.currentTimeMillis();

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        sj_1 sj_12 = apN.aDK().Ln();
        if (sj_12 != null && aet_0.nF(sj_12.aQc()) && dje < System.currentTimeMillis()) {
            try {
                aho_0 aho_02;
                int n2;
                djd.clean();
                jg_0 jg_02 = new jg_0();
                while (jg_02.size() < diZ) {
                    n2 = cF.eV();
                    if (n2 == 0) {
                        return;
                    }
                    aho_02 = (xj)djc.pj(n2);
                    if (aho_02 == null || ((oj_0)((Object)aho_02)).tj() != aMK.dYz || cF.size() > diZ && (jg_02.contains(n2) || jg_02.size() >= 2 && (((xj)djc.pj(jg_02.get(jg_02.size() - 1))).tl() == ((oj_0)((Object)aho_02)).tl() || ((xj)djc.pj(jg_02.get(jg_02.size() - 2))).tl() == ((oj_0)((Object)aho_02)).tl()))) continue;
                    jg_02.add(n2);
                }
                int n3 = 0;
                for (n2 = jg_02.size() - 1; 0 <= n2; --n2) {
                    aho_02 = (akl_2)djd.Uu().an((short)n2);
                    ((akl_2)aho_02).i(aoi_0.aXY().ac(ByteBuffer.wrap(((xj)djc.pj(jg_02.get(n2))).cd())));
                    ((akl_2)aho_02).setDelay(++n3);
                }
                djd.bO(0L);
                djd.start();
                dje = System.currentTimeMillis() + (long)djb;
            }
            catch (Exception exception) {
                a.error((Object)"Imposible d'executer la commande : ", (Throwable)exception);
            }
        }
    }

    public boolean J() {
        return false;
    }
}

