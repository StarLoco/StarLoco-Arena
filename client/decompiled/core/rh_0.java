/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from rh
 */
public class rh_0
extends avg
implements Cloneable {
    protected fa_1 afN = null;
    protected String classname = null;
    protected bk_2 sK = null;
    protected String afO = null;
    protected String afP = null;
    private aIz afQ = null;

    public rh_0(UI uI) {
        this.l(uI);
    }

    public void a(fa_1 fa_12) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.afN = fa_12;
    }

    public void a(yx_2 yx_22) {
        this.b(yx_22);
    }

    public void b(yx_2 yx_22) {
        if (this.aId()) {
            throw this.aIi();
        }
        if (this.afQ == null) {
            if (this.afN == null && this.classname == null) {
                this.afQ = new all_2();
            } else {
                yx_2 yx_23 = this.wl();
                if (yx_23 instanceof aIz) {
                    this.afQ = (aIz)yx_23;
                } else {
                    throw new eq_2(String.valueOf(yx_23) + " mapper implementation does not support nested mappers!");
                }
            }
        }
        this.afQ.b(yx_22);
    }

    public void a(rh_0 rh_02) {
        this.b(rh_02.wl());
    }

    public void setClassname(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.classname = string;
    }

    public void e(bk_2 bk_22) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (this.sK == null) {
            this.sK = bk_22;
        } else {
            this.sK.b(bk_22);
        }
    }

    public bk_2 jz() {
        if (this.aId()) {
            throw this.aIi();
        }
        if (this.sK == null) {
            this.sK = new bk_2(this.TP());
        }
        return this.sK.dB();
    }

    public void d(awq_0 awq_02) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.jz().a(awq_02);
    }

    public void setFrom(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.afO = string;
    }

    public void setTo(String string) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.afP = string;
    }

    public void a(awq_0 awq_02) {
        if (this.afN != null || this.afO != null || this.afP != null) {
            throw this.aIh();
        }
        super.a(awq_02);
    }

    public yx_2 wl() {
        if (this.aId()) {
            this.aIf();
            awq_0 awq_02 = this.aIk();
            Object object = awq_02.P(this.TP());
            if (object instanceof yx_2) {
                return (yx_2)object;
            }
            if (object instanceof rh_0) {
                return ((rh_0)object).wl();
            }
            String string = object == null ? "null" : object.getClass().getName();
            throw new eq_2(string + " at reference '" + awq_02.aJC() + "' is not a valid mapper reference.");
        }
        if (this.afN == null && this.classname == null && this.afQ == null) {
            throw new eq_2("nested mapper or one of the attributes type or classname is required");
        }
        if (this.afQ != null) {
            return this.afQ;
        }
        if (this.afN != null && this.classname != null) {
            throw new eq_2("must not specify both type and classname attribute");
        }
        try {
            yx_2 yx_22 = (yx_2)this.getImplementationClass().newInstance();
            UI uI = this.TP();
            if (uI != null) {
                uI.at(yx_22);
            }
            yx_22.setFrom(this.afO);
            yx_22.setTo(this.afP);
            return yx_22;
        }
        catch (eq_2 eq_22) {
            throw eq_22;
        }
        catch (Throwable throwable) {
            throw new eq_2(throwable);
        }
    }

    protected Class getImplementationClass() {
        String string = this.classname;
        if (this.afN != null) {
            string = this.afN.hZ();
        }
        ClassLoader classLoader = this.sK == null ? this.getClass().getClassLoader() : this.TP().g(this.sK);
        return Class.forName(string, true, classLoader);
    }

    protected rh_0 wm() {
        return (rh_0)this.aIg();
    }
}

