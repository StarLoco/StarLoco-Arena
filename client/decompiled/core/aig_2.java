/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from aiG
 */
public class aig_2
extends ov_2 {
    private final List entries;

    aig_2(short s) {
        super(s);
        this.entries = new ArrayList();
    }

    aig_2(short s, aqw_0[] aqw_0Array) {
        super(s);
        this.entries = new ArrayList<aqw_0>(Arrays.asList(aqw_0Array));
    }

    public List ayu() {
        return this.entries;
    }

    private static ov_2 a(short s, DataInputStream dataInputStream) {
        aqw_0[] aqw_0Array = new aqw_0[dataInputStream.readShort()];
        for (int n2 = 0; n2 < aqw_0Array.length; n2 = (int)((short)(n2 + 1))) {
            aqw_0Array[n2] = new aqw_0(dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort());
        }
        return new aig_2(s, aqw_0Array);
    }

    protected void b(DataOutputStream dataOutputStream) {
        dataOutputStream.writeShort(this.entries.size());
        Iterator iterator = this.entries.iterator();
        while (iterator.hasNext()) {
            aqw_0 aqw_02 = (aqw_0)iterator.next();
            dataOutputStream.writeShort(aqw_02.cOX);
            dataOutputStream.writeShort(aqw_02.cOY);
            dataOutputStream.writeShort(aqw_02.cOZ);
            dataOutputStream.writeShort(aqw_02.cPa);
        }
    }

    static ov_2 h(short s, DataInputStream dataInputStream) {
        return aig_2.a(s, dataInputStream);
    }
}

