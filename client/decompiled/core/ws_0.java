/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from WS
 */
public class ws_0
implements amj_1 {
    private int aW;
    private long bVU;
    private float bAO;
    private boolean bVV;
    private boolean bVW;
    private short bVX;
    private short bVY;
    private float bVZ;
    private int bWa;
    private int bWb;

    public ws_0() {
    }

    public ws_0(int n2, long l2, float f, boolean bl2, boolean bl3, short s, short s2, float f2, int n3, int n4) {
        this.aW = n2;
        this.bVU = l2;
        this.bAO = f;
        this.bVV = bl2;
        this.bVW = bl3;
        this.bVX = s;
        this.bVY = s2;
        this.bVZ = f2;
        this.bWa = n3;
        this.bWb = n4;
    }

    final void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/sound/SoundData.load must not be null");
        }
        this.aW = acf2.readInt();
        this.bVU = acf2.readLong();
        this.bAO = acf2.readFloat();
        this.bVV = acf2.aqE();
        this.bVW = acf2.aqE();
        this.bVX = acf2.readShort();
        this.bVY = acf2.readShort();
        this.bVZ = acf2.readFloat();
        this.bWa = acf2.readInt();
        this.bWb = acf2.readInt();
    }

    final void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/sound/SoundData.save must not be null");
        }
        aij_12.writeInt(this.aW);
        aij_12.writeLong(this.bVU);
        aij_12.writeFloat(this.bAO);
        aij_12.fe(this.bVV);
        aij_12.fe(this.bVW);
        aij_12.writeShort(this.bVX);
        aij_12.writeShort(this.bVY);
        aij_12.writeFloat(this.bVZ);
        aij_12.writeInt(this.bWa);
        aij_12.writeInt(this.bWb);
    }

    public int getId() {
        return this.aW;
    }

    public long ajF() {
        return this.bVU;
    }

    public boolean getStereo() {
        return this.bVV;
    }

    public float getMaxGain() {
        return this.bAO;
    }

    public float getMaxDistance() {
        return this.bVY;
    }

    public int ajG() {
        return this.bWb;
    }

    public float ajH() {
        return this.bVZ;
    }

    public float ajI() {
        return this.bVX;
    }

    public int ajJ() {
        return this.bWa;
    }
}

