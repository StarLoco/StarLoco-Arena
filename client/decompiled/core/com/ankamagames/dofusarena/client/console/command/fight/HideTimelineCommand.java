/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.fight;

import java.util.ArrayList;

public class HideTimelineCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        boolean bl2 = azs_0.aLV().getBooleanProperty("fight.timeline.display");
        azs_0.aLV().g("fight.timeline.display", !bl2);
    }

    public boolean J() {
        return false;
    }
}

