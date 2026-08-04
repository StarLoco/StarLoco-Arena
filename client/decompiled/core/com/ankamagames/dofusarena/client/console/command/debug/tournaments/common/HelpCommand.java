/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.debug.tournaments.common;

import java.util.ArrayList;

public class HelpCommand
implements MC {
    private static final String sI = "Usage : list\n     Affiche la liste des tournois.\n\nUsage : create tournamentDefinitionId name\n     Ajoute un tournoi d'un id de d\u00e9finition de tournoi et d'un nom, et affiche l'id du tournoi.\n\nUsage : destroy tournamentId\n     Enl\u00e8ve un tournoi d'id donn\u00e9.\n\nUsage : add tournamentId creatorCoachId teamPresetId referenceCardId\n     Ajoute le client dans un tournoi, en utilisant une carte, d'ids donn\u00e9s.\n\nUsage : setIsInRegistrationTimePeriod tournamentId (0|1)\n     Ferme/Ouvre la p\u00e9riode des inscriptions dans un tournoi d'id donn\u00e9.\n\nUsage : setIsInOpponentSearchTimePeriod tournamentId level (0|1) nextOpponentSearchTimePeriodDuration\n     Ferme/Ouvre la p\u00e9riode de recherche des opposants dans un tournoi d'id, un niveau de duel et une dur\u00e9e de temps donn\u00e9s.\n\nUsage : setNotReady tournamentId creatorCoachId teamPresetId\n     Change le statut du client \u00e0 non pr\u00eat dans un tournoi d'id donn\u00e9 pour un id de coach cr\u00e9ateur et un id d'\u00e9quipe s\u00e9lectionn\u00e9e.\n\nUsage : setReady tournamentId creatorCoachId teamPresetId\n     Change le statut du client \u00e0 pr\u00eat dans un tournoi d'id donn\u00e9 pour un id de coach cr\u00e9ateur et un id d'\u00e9quipe s\u00e9lectionn\u00e9e.\n";

    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(1);
        if (string.equalsIgnoreCase("help") && arrayList.size() == 2) {
            if (apk_02 == null) {
                add_1.aOG().f(sI, 102, 1);
            } else {
                apk_02.log(sI);
            }
        }
    }

    public boolean J() {
        return false;
    }
}

