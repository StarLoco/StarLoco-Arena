/*
 * Decompiled with CFR 0.152.
 */
public final class Ze {
    public final String ccF;
    public final int ccG;

    public Ze(String string, int n2) {
        this.ccF = string;
        this.ccG = n2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Ze)) {
            return false;
        }
        Ze ze = (Ze)object;
        return this.ccG == ze.ccG && this.ccF.equals(ze.ccF);
    }
}

