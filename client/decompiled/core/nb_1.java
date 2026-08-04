/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Nb
 */
public class nb_1
extends aww {
    public nb_1(float f, float f2, float f3, float f4) {
        super(f, f2, f3, f4);
        assert (this.a(-1, 1, db_0.lN) == this.b(-1, 1, db_0.lM));
        assert (this.a(-1, 1, db_0.lO) == this.b(-1, 1, db_0.lN));
    }

    public float a(int n2, int n3, float f, db_0 db_02) {
        int n4 = n3 - n2;
        return (float)n2 + (float)n4 * f;
    }

    protected float b(int n2, int n3, db_0 db_02) {
        assert (db_02 != db_0.lL && db_02 != db_0.lP);
        switch (db_02) {
            case lM: {
                return (float)n3 + 1.0f;
            }
            case lN: {
                return (float)n3 + 1.0f;
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
                return (float)n3 + 1.0f;
            }
            case lO: {
                return (float)n3 + 1.0f;
            }
        }
        throw new IllegalArgumentException("phase de suat incorrect " + (Object)((Object)db_02));
    }
}

