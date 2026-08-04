/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.geometry.GeometrySprite;
import java.nio.ShortBuffer;

/*
 * Renamed from aMs
 */
public final class ams_1
extends afB {
    public qx dXN;
    public ShortBuffer dXO;
    private int bZB;
    private int dXP;

    public ams_1() {
        this.dXN = null;
        this.dXO = null;
        this.bZB = 0;
        this.clear();
    }

    public ams_1(int n2) {
        this.setSize(n2);
        this.clear();
    }

    public ams_1(ams_1 ams_12) {
        this.setSize(ams_12.bZB);
        this.dXP = ams_12.dXP;
        this.dXO.put(ams_12.aWZ());
    }

    public final void a(aij_1 aij_12) {
        aij_12.writeInt(this.bZB);
        aij_12.writeInt(this.dXP);
        this.dXO.rewind();
        short[] sArray = new short[this.dXO.limit()];
        this.dXO.get(sArray);
        aij_12.writeInt(sArray.length);
        for (short s : sArray) {
            aij_12.writeShort(s);
        }
    }

    public final void b(acf acf2) {
        this.bZB = acf2.readInt();
        this.dXP = acf2.readInt();
        this.setSize(this.bZB);
        int n2 = acf2.readInt();
        for (int j = 0; j < n2; ++j) {
            this.dXO.put(acf2.readShort());
        }
    }

    public final void setSize(int n2) {
        this.bZB = n2;
        if (this.dXN != null) {
            this.dXN.release();
        }
        this.dXN = aoj_1.aXZ().pH(this.bZB * 2);
        this.dXO = (ShortBuffer)this.dXN.getBuffer();
    }

    public final int aWX() {
        return this.bZB;
    }

    public final int aWY() {
        return this.dXP;
    }

    public final void pn(int n2) {
        assert (this.dXP < this.bZB);
        this.dXP = n2;
    }

    public final ShortBuffer aWZ() {
        return (ShortBuffer)this.dXO.position(0);
    }

    public final void clear() {
        this.dXP = 0;
    }

    public final void c(short[] sArray, int n2, int n3) {
        assert (n3 <= this.bZB);
        this.dXO.clear();
        this.dXO.put(sArray, n2, n3);
        this.dXP = n3;
    }

    public final void b(ams_1 ams_12) {
        assert (this.dXP + ams_12.dXP < this.bZB);
        this.dXO.position(this.dXP);
        this.dXO.put(ams_12.aWZ());
        this.dXP += ams_12.dXP;
    }

    public final void g(short[] sArray) {
        assert (this.dXP + sArray.length <= this.bZB);
        this.dXO.position(this.dXP);
        this.dXO.put(sArray);
        this.dXP += sArray.length;
    }

    public final void a(short[] sArray, int n2, int n3) {
        assert (this.dXP + n3 <= this.bZB);
        this.dXO.position(this.dXP);
        this.dXO.put(sArray, n2, n3);
        this.dXP += n3;
    }

    public final void add(int n2) {
        this.dXO.position(this.dXP);
        this.dXO.put((short)n2);
        ++this.dXP;
    }

    public final void cn(int n2, int n3) {
        this.dXO.put(n2, (short)n3);
        if (this.dXP < n2) {
            this.dXP = n2 + 1;
        }
    }

    protected void delete() {
        assert (this != GeometrySprite.bUJ);
        if (this.dXN != null) {
            this.dXN.release();
            this.dXN = null;
        }
        this.dXO = null;
    }
}

