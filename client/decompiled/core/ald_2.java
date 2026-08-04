/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from alD
 */
public class ald_2
extends so_0 {
    private ArrayList byq = new ArrayList();

    public byte[] encode() {
        int n2 = 0;
        int n3 = 0;
        for (int j = 0; j < this.byq.size(); ++j) {
            iz_0 iz_02 = (iz_0)this.byq.get(j);
            n2 += iz_02.nj();
            ++n3;
            if (!(iz_02 instanceof th_2)) continue;
            n2 += ((th_2)iz_02).agi().nj();
            ++n3;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + n2);
        byteBuffer.put((byte)n3);
        for (int j = 0; j < this.byq.size(); ++j) {
            iz_0 iz_03 = (iz_0)this.byq.get(j);
            byteBuffer.put(iz_03.cd());
            if (!(iz_03 instanceof th_2)) continue;
            iz_0 iz_04 = ((th_2)iz_03).agi();
            byteBuffer.put(iz_04.cd());
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public void e(iz_0 iz_02) {
        if (iz_02 instanceof th_2) {
            a.error((Object)"Attention ! Pour ajouter deux \u00e9v\u00e8nements li\u00e9s, il faut utiliser la m\u00e9thode addDurationCalendarEvent !");
        } else {
            this.byq.add(iz_02);
        }
    }

    public void a(iz_0 iz_02, th_2 th_22) {
        th_22.d(iz_02);
        this.byq.add(th_22);
    }

    public int getId() {
        return 17008;
    }
}

