/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

/*
 * Renamed from Li
 */
public final class li_0 {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    public Reader bqf;
    public int bufferSize = 8192;
    public Vector bqg = new Vector();
    private UI hL = null;
    static Class bqh;
    static Class bqi;
    static Class bqj;

    public void f(Reader reader) {
        this.bqf = reader;
    }

    public void l(UI uI) {
        this.hL = uI;
    }

    public UI TP() {
        return this.hL;
    }

    public void setBufferSize(int n2) {
        this.bufferSize = n2;
    }

    public void c(Vector vector) {
        this.bqg = vector;
    }

    public Reader Xx() {
        Vector vector;
        int n2;
        if (this.bqf == null) {
            throw new eq_2("primaryReader must not be null.");
        }
        Reader reader = this.bqf;
        int n3 = this.bqg.size();
        Vector vector2 = new Vector();
        for (n2 = 0; n2 < n3; ++n2) {
            PH pH = (PH)this.bqg.elementAt(n2);
            vector = pH.acn();
            int n4 = vector.size();
            for (int j = 0; j < n4; ++j) {
                vector2.addElement(vector.elementAt(j));
            }
        }
        n2 = vector2.size();
        if (n2 > 0) {
            for (int j = 0; j < n2; ++j) {
                vector = vector2.elementAt(j);
                if (vector instanceof avt_0) {
                    avt_0 avt_02 = (avt_0)vector2.elementAt(j);
                    String string = avt_02.getClassName();
                    bk_2 bk_22 = avt_02.jC();
                    UI uI = avt_02.TP();
                    if (string == null) continue;
                    try {
                        Object[] objectArray;
                        int n5;
                        Constructor<?>[] constructorArray;
                        Class<?> clazz = null;
                        if (bk_22 == null) {
                            clazz = Class.forName(string);
                        } else {
                            constructorArray = uI.g(bk_22);
                            clazz = Class.forName(string, true, (ClassLoader)constructorArray);
                        }
                        if (clazz == null) continue;
                        if (!(bqh == null ? li_0.a("java.io.FilterReader") : bqh).isAssignableFrom(clazz)) {
                            throw new eq_2(string + " does not extend java.io.FilterReader");
                        }
                        constructorArray = clazz.getConstructors();
                        boolean bl2 = false;
                        for (n5 = 0; n5 < constructorArray.length; ++n5) {
                            objectArray = constructorArray[n5].getParameterTypes();
                            if (objectArray.length != 1 || !((Class)objectArray[0]).isAssignableFrom(bqi == null ? li_0.a("java.io.Reader") : bqi)) continue;
                            bl2 = true;
                            break;
                        }
                        if (!bl2) {
                            throw new eq_2(string + " does not define a public constructor" + " that takes in a Reader as its " + "single argument.");
                        }
                        objectArray = new Reader[]{reader};
                        reader = (Reader)constructorArray[n5].newInstance(objectArray);
                        this.Y(reader);
                        if (!(bqj == null ? li_0.a("aee") : bqj).isAssignableFrom(clazz)) continue;
                        vj_0[] vj_0Array = avt_02.aIA();
                        ((aee_0)((Object)reader)).a(vj_0Array);
                        continue;
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        throw new eq_2(classNotFoundException);
                    }
                    catch (InstantiationException instantiationException) {
                        throw new eq_2(instantiationException);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        throw new eq_2(illegalAccessException);
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        throw new eq_2(invocationTargetException);
                    }
                }
                if (!(vector instanceof gx_2)) continue;
                this.Y(vector);
                reader = ((gx_2)((Object)vector)).b(reader);
                this.Y(reader);
            }
        }
        return reader;
    }

    private void Y(Object object) {
        if (this.hL == null) {
            return;
        }
        if (object instanceof and_1) {
            ((and_1)object).l(this.hL);
            return;
        }
        this.hL.at(object);
    }

    public String c(Reader reader) {
        return ga_2.a(reader, this.bufferSize);
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

