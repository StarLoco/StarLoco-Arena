/*
 * Decompiled with CFR 0.152.
 */
public class adb
implements hY {
    private String rE;
    private boolean clI = false;
    private long PZ;
    private int wg;
    private boolean clJ = true;

    public adb(String string, int n2, long l2) {
        this.rE = string;
        this.wg = n2;
        this.PZ = l2;
        this.clJ = this.wg == Integer.MAX_VALUE;
    }

    public adb(String string) {
        this(string, Integer.MAX_VALUE, 0L);
    }

    public adb(boolean bl2) {
        this.clI = bl2;
    }

    public String getId() {
        return this.rE;
    }

    public int getDuration() {
        return this.wg;
    }

    public long getStartTime() {
        return this.PZ;
    }

    public void dx(long l2) {
        this.PZ = l2;
    }

    public boolean asc() {
        return this.clI;
    }

    public boolean is() {
        return this.clJ;
    }

    public void asd() {
        this.clJ = true;
    }
}

