/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Vh
 */
public class vh_2
extends aww {
    public vh_2(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
        assert (this.a(-1, 1, db_0.lN) == this.b(-1, 1, db_0.lM));
        assert (this.a(-1, 1, db_0.lO) == this.b(-1, 1, db_0.lN));
    }

    public float a(int n2, int n3, float f, db_0 db_02) {
        float f2 = (f - 0.5f) * 2.0f;
        int n4 = n2 - n3;
        float f3 = (float)n4 / 4.0f;
        return (float)n3 + (float)(Math.pow(f3 + 1.0f, 2.0) - Math.pow(f3 * f2 + 1.0f, 2.0));
    }

    protected float b(int n2, int n3, db_0 db_02) {
        assert (db_02 != db_0.lL && db_02 != db_0.lP);
        switch (db_02) {
            case lM: {
                return (float)n2 + 0.4f;
            }
            case lN: {
                return (float)n2 + 0.4f;
            }
            case lO: {
                return n3;
            }
        }
        throw new IllegalArgumentException("phase de suat incorrect " + (Object)((Object)db_02));
    }

    protected float a(int n2, int n3, db_0 db_02) {
        assert (db_02 != db_0.lL && db_02 != db_0.lP);
        switch (db_02) {
            case lM: {
                return n2;
            }
            case lN: {
                return (float)n2 + 0.4f;
            }
            case lO: {
                return (float)n2 + 0.4f;
            }
        }
        throw new IllegalArgumentException("phase de suat incorrect " + (Object)((Object)db_02));
    }
}

