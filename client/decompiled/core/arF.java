/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

public class arF
extends avg {
    public void addText(String string) {
        es_2 es_22 = es_2.OE();
        if (!(es_22 instanceof zv_0)) {
            return;
        }
        String string2 = this.TP().getDescription();
        if (string2 == null) {
            this.TP().setDescription(string);
        } else {
            this.TP().setDescription(string2 + string);
        }
    }

    public static String E(UI uI) {
        Vector vector = (Vector)uI.gi("ant.targets");
        if (vector == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < vector.size(); ++j) {
            id_2 id_22 = (id_2)vector.elementAt(j);
            arF.a(uI, id_22, stringBuffer);
        }
        return stringBuffer.toString();
    }

    private static void a(UI uI, id_2 id_22, StringBuffer stringBuffer) {
        if (id_22 == null) {
            return;
        }
        Vector vector = arF.a(uI, id_22, "description");
        if (vector == null) {
            return;
        }
        for (int j = 0; j < vector.size(); ++j) {
            rs_0 rs_02;
            String string;
            dm_1 dm_12 = (dm_1)vector.elementAt(j);
            if (!(dm_12 instanceof rs_0) || (string = (rs_02 = (rs_0)dm_12).LN().Pa().toString()) == null) continue;
            stringBuffer.append(uI.fZ(string));
        }
    }

    private static Vector a(UI uI, id_2 id_22, String string) {
        dm_1[] dm_1Array = id_22.TQ();
        Vector<dm_1> vector = new Vector<dm_1>();
        for (int j = 0; j < dm_1Array.length; ++j) {
            if (!string.equals(dm_1Array[j].LF())) continue;
            vector.addElement(dm_1Array[j]);
        }
        return vector;
    }
}

