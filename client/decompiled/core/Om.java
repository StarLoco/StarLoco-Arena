/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.Iterator;
import org.apache.log4j.Logger;

public class Om {
    private static final Logger a = Logger.getLogger(Om.class);
    private final int[] bBL = new int[6];
    private final jg_0[] bBM = new jg_0[2];
    private final ano_2 bBN = new ano_2();

    public ry ae(byte by) {
        return Om.hb(this.bBL[by]);
    }

    public Iterator af(byte by) {
        return new aag_0(this, by);
    }

    public Iterator abs() {
        return new aah_1(this);
    }

    public static ry hb(int n2) {
        return new ry((n2 >>> 20 & 0xFFF) - 2047, (n2 >>> 8 & 0xFFF) - 2047, (short)((n2 & 0xFF) - 127));
    }

    private static int s(ry ry2) {
        assert (Math.abs(ry2.getX()) < 2048);
        assert (Math.abs(ry2.getY()) < 2048);
        return ry2.getX() + 2047 << 20 | ry2.getY() + 2047 << 8 | ry2.wk() + 127;
    }

    public void clear() {
        for (int j = 0; j < 6; ++j) {
            this.bBL[j] = 0;
        }
        this.bBM[0].clear();
        this.bBM[1].clear();
        this.bBN.clear();
    }

    public void b(acf acf2) {
        int n2;
        int n3;
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/dofusarena/common/game/fight/ArenaFightMapDefinition.load must not be null");
        }
        for (n3 = 0; n3 < 6; ++n3) {
            this.bBL[n3] = acf2.readInt();
        }
        n3 = acf2.readShort() & 0xFFFF;
        int n4 = n3 >>> 8;
        int n5 = n3 & 0xFF;
        this.bBM[0] = new jg_0(n4);
        this.bBM[1] = new jg_0(n5);
        for (n2 = 0; n2 < n4; ++n2) {
            this.bBM[0].add(acf2.readInt());
        }
        this.bBM[0].sort();
        for (n2 = 0; n2 < n5; ++n2) {
            this.bBM[1].add(acf2.readInt());
        }
        this.bBM[1].sort();
        n2 = acf2.readByte() & 0xFF;
        for (int j = 0; j < n2; ++j) {
            int n6 = acf2.readInt();
            int n7 = acf2.readInt();
            this.bBN.bz(n6, n7);
        }
    }

    public void a(ry ry2, int n2) {
        int n3 = Om.s(ry2);
        assert (!this.bBN.bY(n3));
        this.bBN.bz(n3, n2);
    }

    public void a(aij_1 aij_12) {
        int n2;
        int n3;
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/dofusarena/common/game/fight/ArenaFightMapDefinition.save must not be null");
        }
        if (this.bBM[0] == null || this.bBM[1] == null) {
            throw new co_2("pas de point de d\u00e9part");
        }
        for (n3 = 0; n3 < 6; ++n3) {
            aij_12.writeInt(this.bBL[n3]);
        }
        n3 = this.bBM[0].size() << 8 | this.bBM[1].size();
        if (n3 >= 65535) {
            throw new co_2("trop de point de d\u00e9part");
        }
        aij_12.writeShort((short)n3);
        for (n2 = 0; n2 < this.bBM[0].size(); ++n2) {
            aij_12.writeInt(this.bBM[0].get(n2));
        }
        for (n2 = 0; n2 < this.bBM[1].size(); ++n2) {
            aij_12.writeInt(this.bBM[1].get(n2));
        }
        n2 = this.bBN.size();
        if (n2 >= 255) {
            throw new co_2("trop de cellules sp\u00e9ciales");
        }
        aij_12.writeByte((byte)(n2 & 0xFF));
        hp_0 hp_02 = this.bBN.aCq();
        while (hp_02.hasNext()) {
            hp_02.fK();
            aij_12.writeInt(hp_02.kR());
            aij_12.writeInt(hp_02.value());
        }
    }

    public void a(int n2, ry ry2) {
        if (ry2 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/dofusarena/common/game/fight/ArenaFightMapDefinition.addCoach must not be null");
        }
        this.bBL[n2] = Om.s(ry2);
    }

    public void b(int n2, ry ry2) {
        if (ry2 == null) {
            throw new IllegalArgumentException("Argument 1 for @NotNull parameter of com/ankamagames/dofusarena/common/game/fight/ArenaFightMapDefinition.addStartPoint must not be null");
        }
        assert (n2 == 0 || n2 == 1);
        if (this.bBM[n2] == null) {
            this.bBM[n2] = new jg_0();
        }
        this.bBM[n2].add(Om.s(ry2));
    }

    static /* synthetic */ jg_0[] a(Om om) {
        return om.bBM;
    }

    static /* synthetic */ ano_2 b(Om om) {
        return om.bBN;
    }
}

