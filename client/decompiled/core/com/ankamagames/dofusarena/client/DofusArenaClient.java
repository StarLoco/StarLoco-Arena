/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 *  org.apache.log4j.PropertyConfigurator
 */
package com.ankamagames.dofusarena.client;

import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.awt.GraphicsEnvironment;
import java.net.URL;
import java.util.Locale;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DofusArenaClient {
    private static Logger a = Logger.getLogger(DofusArenaClient.class);
    private static final boolean dmt = true;

    public static void main(String[] stringArray) {
        PropertyConfigurator.configure((URL)DofusArenaClient.class.getResource("log4j.properties"));
        cx_0.JY().ae((float)GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getAvailableAcceleratedMemory() / 1024.0f);
        ait_1.ayz().a("TGA", new Fd());
        ait_1.ayz().a("DDSM", new qn_2());
        ait_1.ayz().a("DDS", new asL());
        ait_1.ayz().a("TGAM", new aj_2());
        qz_0.adf().start();
        kS.display();
        a.trace((Object)("Locale: " + Locale.getDefault().getDisplayName()));
        Locale.setDefault(Locale.ENGLISH);
        boolean bl2 = false;
        block6: for (String string : stringArray) {
            if (string.length() <= 1 || string.charAt(0) != '-') continue;
            char c = string.charAt(1);
            switch (c) {
                case 'c': {
                    String string2 = string.substring(2);
                    bl2 = mu_1.rM().load(string2);
                    if (bl2) continue block6;
                    DofusArenaClientInstance.getLogger().fatal((Object)("Echec du chargement avec le fichier de config " + string2 + ", reprise du fichier par d\u017dfaut"));
                    continue block6;
                }
                case 's': {
                    mu_1.rM().aa(false);
                    continue block6;
                }
                default: {
                    DofusArenaClientInstance.getLogger().error((Object)("argument inconnu : '-" + c + "'"));
                }
            }
        }
        if (bl2 || mu_1.rM().rP()) {
            DofusArenaClientInstance dofusArenaClientInstance = DofusArenaClientInstance.yl();
            try {
                dofusArenaClientInstance.initialize();
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception, "Error", 0);
                a.error((Object)"Erreur au lancement", (Throwable)exception);
                System.exit(0);
            }
        } else {
            DofusArenaClientInstance.getLogger().fatal((Object)"Echec du chargement de la configuration, DofusArenaConfiguration introuvable");
        }
    }
}

