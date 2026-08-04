/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

public abstract class lt
extends aib {
    static Class He = Boolean.TYPE;
    static Class[] Hf = new Class[1];
    public static final int Hg = 4;
    private String Hh;
    acq Hi;
    private int vE = 0;
    protected List Hj = new ArrayList();

    protected abstract String hj();

    protected abstract String[] hk();

    protected abstract Class[] getParameterTypes();

    protected abstract Object[] f(Object var1);

    public void start() {
        try {
            assert (this.Pb != null);
            ClassLoader classLoader = this.Pb.getClass().getClassLoader();
            this.Hi = new acq(this.hj(), He, this.hk(), this.getParameterTypes(), Hf, classLoader);
            super.start();
        }
        catch (Exception exception) {
            this.e("Could not start evaluator with expression [" + this.Hh + "]", exception);
        }
    }

    public boolean w(Object object) {
        if (!this.isStarted()) {
            throw new IllegalStateException("Evaluator [" + this.name + "] was called in stopped state");
        }
        try {
            Boolean bl2 = (Boolean)this.Hi.b(this.f(object));
            return bl2;
        }
        catch (Exception exception) {
            ++this.vE;
            if (this.vE >= 4) {
                this.stop();
            }
            throw new Gp("Evaluator [" + this.name + "] caused an exception", exception);
        }
    }

    public String getExpression() {
        return this.Hh;
    }

    public void setExpression(String string) {
        this.Hh = string;
    }

    public void a(aaa_2 aaa_22) {
        this.Hj.add(aaa_22);
    }

    public List ql() {
        return this.Hj;
    }

    static {
        lt.Hf[0] = Gp.class;
    }
}

