/*
 * Decompiled with CFR 0.152.
 */
public final class QI
extends ahg_2 {
    public mr_0 bHx;
    public float[] bHy;

    public QI(String string, mr_0 mr_02) {
        super(string);
        this.bHx = mr_02;
        this.bHy = new float[16];
    }

    public QI(QI qI) {
        super(qI.m_name);
        this.bHy = new float[16];
        this.c(qI);
    }

    public final boolean b(QI qI) {
        if (this == qI) {
            return true;
        }
        if (this.bHx != qI.bHx) {
            return false;
        }
        switch (this.bHx) {
            case JN: {
                return this.bHy[0] == qI.bHy[0];
            }
            case JO: {
                return this.bHy[0] == qI.bHy[0] && this.bHy[1] == qI.bHy[1] && this.bHy[2] == qI.bHy[2] && this.bHy[3] == qI.bHy[3];
            }
            case JP: {
                return this.bHy[0] == qI.bHy[0] && this.bHy[1] == qI.bHy[1] && this.bHy[2] == qI.bHy[2] && this.bHy[3] == qI.bHy[3] && this.bHy[4] == qI.bHy[4] && this.bHy[5] == qI.bHy[5] && this.bHy[6] == qI.bHy[6] && this.bHy[7] == qI.bHy[7] && this.bHy[8] == qI.bHy[8] && this.bHy[9] == qI.bHy[9] && this.bHy[10] == qI.bHy[10] && this.bHy[11] == qI.bHy[11] && this.bHy[12] == qI.bHy[12] && this.bHy[13] == qI.bHy[13] && this.bHy[14] == qI.bHy[14] && this.bHy[15] == qI.bHy[15];
            }
        }
        return false;
    }

    public final void c(QI qI) {
        this.bHx = qI.bHx;
        switch (this.bHx) {
            case JN: {
                this.bHy[0] = qI.bHy[0];
                break;
            }
            case JO: {
                this.bHy[0] = qI.bHy[0];
                this.bHy[1] = qI.bHy[1];
                this.bHy[2] = qI.bHy[2];
                this.bHy[3] = qI.bHy[3];
                break;
            }
            case JP: {
                this.bHy[0] = qI.bHy[0];
                this.bHy[1] = qI.bHy[1];
                this.bHy[2] = qI.bHy[2];
                this.bHy[3] = qI.bHy[3];
                this.bHy[4] = qI.bHy[4];
                this.bHy[5] = qI.bHy[5];
                this.bHy[6] = qI.bHy[6];
                this.bHy[7] = qI.bHy[7];
                this.bHy[8] = qI.bHy[8];
                this.bHy[9] = qI.bHy[9];
                this.bHy[10] = qI.bHy[10];
                this.bHy[11] = qI.bHy[11];
                this.bHy[12] = qI.bHy[12];
                this.bHy[13] = qI.bHy[13];
                this.bHy[14] = qI.bHy[14];
                this.bHy[15] = qI.bHy[15];
            }
        }
    }
}

