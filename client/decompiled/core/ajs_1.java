/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajS
 */
public abstract class ajs_1
extends ii_2
implements mt_2 {
    private String name;
    boolean amF = false;

    public void start() {
        this.amF = true;
    }

    public boolean isStarted() {
        return this.amF;
    }

    public void stop() {
        this.amF = false;
    }

    public abstract vq_0 aC(Object var1);

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }
}

