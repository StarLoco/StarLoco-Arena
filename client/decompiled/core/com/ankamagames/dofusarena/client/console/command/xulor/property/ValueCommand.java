/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.dofusarena.client.console.command.xulor.property;

import java.util.ArrayList;

public class ValueCommand
implements MC {
    public void a(apk_0 apk_02, adb_2 adb_22, ArrayList arrayList) {
        String string = (String)arrayList.get(2);
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.append(" = ");
        afl_0 afl_02 = azs_0.aLV().getProperty(string);
        if (afl_02 != null) {
            Object object = afl_02.getValue();
            if (object instanceof aho_0) {
                String[] stringArray;
                aho_0 aho_02 = (aho_0)object;
                for (String string2 : stringArray = aho_02.getFields()) {
                    stringBuilder.append('\n').append(string2).append(" = ");
                    this.a(aho_02.getFieldValue(string2), stringBuilder);
                }
            } else {
                this.a(object, stringBuilder);
            }
        }
        apk_02.trace(stringBuilder.toString());
    }

    public boolean J() {
        return false;
    }

    private void a(Object object, StringBuilder stringBuilder) {
        if (object instanceof Object[]) {
            for (Object object2 : (Object[])object) {
                stringBuilder.append(object2).append(',');
            }
        } else {
            stringBuilder.append(object);
        }
    }
}

