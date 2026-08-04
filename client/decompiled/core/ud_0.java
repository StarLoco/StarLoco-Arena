/*
 * Decompiled with CFR 0.152.
 */
import java.lang.reflect.InvocationTargetException;

/*
 * Renamed from uD
 */
public final class ud_0 {
    private amw_2 aqG;
    private Object parent;
    private UI hL;
    private Object aqH;
    private String aqI;

    private ud_0(UI uI, Object object, amw_2 amw_22) {
        this.hL = uI;
        this.parent = object;
        this.aqG = amw_22;
    }

    public void cy(String string) {
        this.aqI = string;
    }

    public Object create() {
        if (this.aqI != null) {
            if (!this.aqG.aBL()) {
                throw new eq_2("Not allowed to use the polymorphic form for this element");
            }
            abm_1 abm_12 = abm_1.D(this.hL);
            this.aqH = abm_12.hp(this.aqI);
            if (this.aqH == null) {
                throw new eq_2("Unable to create object of type " + this.aqI);
            }
        }
        try {
            this.aqH = this.aqG.a(this.hL, this.parent, this.aqH);
            if (this.hL != null) {
                this.hL.at(this.aqH);
            }
            return this.aqH;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2(illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2(instantiationException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            if (this.aqI == null) {
                throw illegalArgumentException;
            }
            throw new eq_2("Invalid type used " + this.aqI);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw hm_2.b(invocationTargetException);
        }
    }

    public Object AI() {
        return this.aqG.AI();
    }

    public void store() {
        try {
            this.aqG.j(this.parent, this.aqH);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new eq_2(illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            throw new eq_2(instantiationException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            if (this.aqI == null) {
                throw illegalArgumentException;
            }
            throw new eq_2("Invalid type used " + this.aqI);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw hm_2.b(invocationTargetException);
        }
    }

    ud_0(UI uI, Object object, amw_2 amw_22, ari ari2) {
        this(uI, object, amw_22);
    }
}

