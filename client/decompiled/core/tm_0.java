/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tm
 */
public abstract class tm_0
extends ii_2
implements mt_2 {
    private String name;
    boolean amF = false;

    public abstract vq_0 a(axe var1, arN var2, rl_2 var3, String var4, Object[] var5, Throwable var6);

    public void start() {
        this.amF = true;
    }

    public boolean isStarted() {
        return this.amF;
    }

    public void stop() {
        this.amF = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }
}

