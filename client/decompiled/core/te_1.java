/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/*
 * Renamed from TE
 */
public class te_1 {
    private static Pattern bOy = Pattern.compile("(^([a-zA-Z.]+):)?([a-zA-Z]+){1}(\\((([_a-zA-Z0-9]+([,]?[\\s]*[_a-zA-Z0-9]+)*)*)\\))?");
    public static final String bOz = ";";
    protected static Logger a = Logger.getLogger(te_1.class);
    protected String[] bOA;
    protected String nq = null;
    protected aji_1 blb = null;

    public void fS(String string) {
        this.nq = string;
        this.bOA = string.split(bOz);
    }

    public void c(String string, aji_1 aji_12) {
        this.nq = string;
        this.bOA = string.split(bOz);
        this.blb = aji_12;
    }

    public void setElementMap(aji_1 aji_12) {
        this.blb = aji_12;
    }

    public Object agg() {
        Object object = null;
        for (String string : this.bOA) {
            Matcher matcher = bOy.matcher(string);
            if (matcher.matches()) {
                String string2 = matcher.group(2);
                String string3 = matcher.group(3);
                String string4 = matcher.group(5);
                String[] stringArray = string4 != null ? matcher.group(5).split(",") : new String[]{};
                object = this.a(string2, string3, stringArray);
                continue;
            }
            a.error((Object)("Erreur de syntaxe : '" + string + "' n'est pas du type 'package:method(param1,param2,...)'."));
        }
        return object;
    }

    private Object a(String string, String string2, String[] stringArray) {
        ArrayList arrayList = new ArrayList();
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        this.a(stringArray, arrayList, arrayList2);
        Class clazz = add_1.aOG().kH(string);
        if (clazz != null) {
            Method[] methodArray;
            for (Method method : methodArray = clazz.getMethods()) {
                if (!method.getName().equals(string2)) continue;
                boolean bl2 = false;
                Class<?>[] classArray = method.getParameterTypes();
                if (classArray.length <= arrayList.size()) {
                    for (int j = 0; j < classArray.length; ++j) {
                        Class<?> clazz2 = classArray[j];
                        if (arrayList.size() <= j) {
                            bl2 = false;
                        } else if (clazz2.isArray()) {
                            if (clazz2.isAssignableFrom((Class)arrayList.get(j))) {
                                bl2 = true;
                            } else {
                                int n2 = 0;
                                int n3 = arrayList.size();
                                for (int i2 = j; i2 < n3 && clazz2.getComponentType().isAssignableFrom((Class)arrayList.get(i2)); ++i2) {
                                    ++n2;
                                }
                                if (n2 > 0) {
                                    Object object = Array.newInstance(clazz2.getComponentType(), n2);
                                    for (int i3 = 0; i3 < n2; ++i3) {
                                        arrayList.remove(j);
                                        Array.set(object, i3, arrayList2.remove(j));
                                    }
                                    arrayList2.add(j, object);
                                    arrayList.add(j, clazz2);
                                } else {
                                    bl2 = false;
                                }
                            }
                        } else {
                            bl2 = classArray[j].isAssignableFrom((Class)arrayList.get(j));
                        }
                        if (!bl2) break;
                    }
                }
                if (classArray.length != arrayList.size()) {
                    bl2 = false;
                }
                if (!bl2) continue;
                try {
                    return method.invoke(null, arrayList2.toArray());
                }
                catch (Exception exception) {
                    a.error((Object)("Erreur lors du invokeCallBack sur la m\u00e9thode " + method.getName() + " de la classe " + clazz.getName()), (Throwable)exception);
                    if (exception.getCause() != null) {
                        a.error((Object)"Raison : ", exception.getCause());
                    }
                    return null;
                }
            }
            a.error((Object)("La m\u00e9thode '" + (string != null ? string + ":" : "") + string2 + "(" + this.k(arrayList) + ")' est inconnue !"));
        } else {
            a.error((Object)("La m\u00e9thode '" + (string != null ? string + ":" : "") + string2 + "(" + this.k(arrayList) + ")' est inconnue !"));
        }
        return null;
    }

    protected void a(String[] stringArray, List list, List list2) {
        for (int j = 0; j < stringArray.length; ++j) {
            na_1 na_12 = null;
            if (this.blb != null) {
                na_12 = this.blb.R(stringArray[j]);
            }
            if (na_12 != null) {
                Object object = na_12.getElementValue();
                if (object == null) continue;
                list.add(object.getClass());
                list2.add(object);
                continue;
            }
            if (stringArray[j].length() == 0) continue;
            list.add(String.class);
            list2.add(stringArray[j]);
        }
    }

    private String k(List list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Class clazz : list) {
            stringBuilder.append(",").append(clazz.getName());
        }
        return stringBuilder.toString().substring(1);
    }
}

