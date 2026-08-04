/*
 * Decompiled with CFR 0.152.
 */
public abstract class aib
extends ii_2
implements ayx {
    String name;
    boolean bgs;

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        if (this.name != null) {
            throw new IllegalStateException("name has been already set");
        }
        this.name = string;
    }

    public boolean isStarted() {
        return this.bgs;
    }

    public void start() {
        this.bgs = true;
    }

    public void stop() {
        this.bgs = false;
    }
}

