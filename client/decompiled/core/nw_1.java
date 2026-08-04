/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Renamed from nw
 */
public final class nw_1
extends and_1
implements gx_2 {
    private String OA = null;
    private static final String OB = "org.apache.tools.ant.filters.util.JavaClassHelper";
    static Class OC;

    public nw_1() {
    }

    public nw_1(Reader reader) {
        super(reader);
    }

    public int read() {
        int n2 = -1;
        if (this.OA != null && this.OA.length() == 0) {
            this.OA = null;
        }
        if (this.OA != null) {
            n2 = this.OA.charAt(0);
            this.OA = this.OA.substring(1);
            if (this.OA.length() == 0) {
                this.OA = null;
            }
        } else {
            String string = this.aCh();
            if (string == null || string.length() == 0) {
                n2 = -1;
            } else {
                byte[] byArray = string.getBytes("ISO-8859-1");
                try {
                    Object[] objectArray;
                    Class[] classArray;
                    Method method;
                    StringBuffer stringBuffer;
                    Class<?> clazz = Class.forName(OB);
                    if (clazz != null && (stringBuffer = (StringBuffer)(method = clazz.getMethod("getConstants", classArray = new Class[]{OC == null ? (OC = nw_1.a("[B")) : OC})).invoke(null, objectArray = new Object[]{byArray})).length() > 0) {
                        this.OA = stringBuffer.toString();
                        return this.read();
                    }
                }
                catch (NoClassDefFoundError noClassDefFoundError) {
                    throw noClassDefFoundError;
                }
                catch (RuntimeException runtimeException) {
                    throw runtimeException;
                }
                catch (InvocationTargetException invocationTargetException) {
                    Throwable throwable = invocationTargetException.getTargetException();
                    if (throwable instanceof NoClassDefFoundError) {
                        throw (NoClassDefFoundError)throwable;
                    }
                    if (throwable instanceof RuntimeException) {
                        throw (RuntimeException)throwable;
                    }
                    throw new eq_2(throwable);
                }
                catch (Exception exception) {
                    throw new eq_2(exception);
                }
            }
        }
        return n2;
    }

    public Reader b(Reader reader) {
        nw_1 nw_12 = new nw_1(reader);
        return nw_12;
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

