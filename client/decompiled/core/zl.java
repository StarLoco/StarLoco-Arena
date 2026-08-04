/*
 * Decompiled with CFR 0.152.
 */
public class zl
extends oi_2 {
    public static final String SYSTEM_OUT = "System.out";
    public static final String SYSTEM_ERR = "System.err";
    protected String target = "System.out";

    public void setTarget(String string) {
        String string2 = string.trim();
        if (SYSTEM_OUT.equalsIgnoreCase(string2)) {
            this.target = SYSTEM_OUT;
        } else if (SYSTEM_ERR.equalsIgnoreCase(string2)) {
            this.target = SYSTEM_ERR;
        } else {
            this.dl(string);
        }
    }

    public String getTarget() {
        return this.target;
    }

    void dl(String string) {
        apQ apQ2 = new apQ("[" + string + " should be System.out or System.err.", this);
        apQ2.c(new apQ("Using previously set target, System.out by default.", this));
        this.b(apQ2);
    }

    public void start() {
        if (this.target.equals(SYSTEM_OUT)) {
            this.setWriter(this.createWriter(System.out));
        } else {
            this.setWriter(this.createWriter(System.err));
        }
        super.start();
    }

    protected final void closeWriter() {
        this.writeFooter();
    }
}

