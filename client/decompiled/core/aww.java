/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public abstract class aww {
    protected static final float dhT = 1.0f;
    protected static Logger a = Logger.getLogger(aww.class);
    final float[] dhU;

    public aww(float f, float f2, float f3, float f4) {
        this.dhU = new float[]{f, f2, f3, f4};
    }

    public db_0 bo(float f) {
        assert (f >= 0.0f && f <= 1.0f);
        if (f < this.dhU[0]) {
            return db_0.lL;
        }
        if (f < this.dhU[1]) {
            return db_0.lM;
        }
        if (f < this.dhU[2]) {
            return db_0.lN;
        }
        if (f < this.dhU[3]) {
            return db_0.lO;
        }
        return db_0.lP;
    }

    public float a(int n2, int n3, float f, db_0 db_02) {
        assert (this.bo(f) == db_02);
        assert (db_02 != db_0.lL && db_02 != db_0.lP);
        float f2 = this.a(n2, n3, db_02);
        float f3 = this.b(n2, n3, db_02);
        int n4 = db_02.ordinal();
        float f4 = (f - this.dhU[n4 - 1]) / (this.dhU[n4] - this.dhU[n4 - 1]);
        return ej_0.a(f2, f3, f4);
    }

    protected abstract float a(int var1, int var2, db_0 var3);

    protected abstract float b(int var1, int var2, db_0 var3);
}

