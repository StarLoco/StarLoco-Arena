/*
 * Decompiled with CFR 0.152.
 */
public abstract class Ik
extends ii_2
implements adr_0 {
    protected boolean bgs = false;
    private ThreadLocal bgt = new yZ(this);
    protected String name;
    private aan_2 bgu = new aan_2();
    private int bgv = 0;
    private int bgw = 0;
    static final int bgx = 5;

    public String getName() {
        return this.name;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void T(Object object) {
        if (((Boolean)this.bgt.get()).booleanValue()) {
            return;
        }
        try {
            this.bgt.set(true);
            if (!this.bgs) {
                if (this.bgv++ < 5) {
                    this.b(new apQ("Attempted to append to non started appender [" + this.name + "].", this));
                }
                return;
            }
            if (this.U(object) == vq_0.bTn) {
                return;
            }
            this.z(object);
        }
        catch (Exception exception) {
            if (this.bgw++ < 5) {
                this.e("Appender [" + this.name + "] failed to append.", exception);
            }
        }
        finally {
            this.bgt.set(false);
        }
    }

    protected abstract void z(Object var1);

    public void setName(String string) {
        this.name = string;
    }

    public void start() {
        this.bgs = true;
    }

    public void stop() {
        this.bgs = false;
    }

    public boolean isStarted() {
        return this.bgs;
    }

    public String toString() {
        return this.getClass().getName() + "[" + this.name + "]";
    }

    public void a(ajs_1 ajs_12) {
        this.bgu.a(ajs_12);
    }

    public ajs_1 Ud() {
        return this.bgu.Ud();
    }

    public void Ue() {
        this.bgu.Ue();
    }

    public vq_0 U(Object object) {
        return this.bgu.U(object);
    }

    public ei_2 ti() {
        return null;
    }

    public void a(ei_2 ei_22) {
    }
}

