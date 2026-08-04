/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class atA {
    private static final Bk ub = LD.p(atA.class);
    private static atA cUb = new atA();
    private String[] cUc;
    private String[] cUd;
    private aCW cUe = null;
    private Map cUf = new HashMap();

    public static void a(String string, Long l2) {
        atA.aGE().b(string, l2);
    }

    public static void loadLibrary(String string) {
        atA.a(string, null);
    }

    static atA aGE() {
        return cUb;
    }

    private atA() {
        ub.trace("<init>");
    }

    synchronized void b(String string, Long l2) {
        if (this.e(string, l2)) {
            return;
        }
        List list = this.d(string, l2);
        if (list != null && list.size() > 0 && !this.a(string, l2, list)) {
            try {
                System.loadLibrary(string);
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                ub.e("Could not load library: {}; version: {}; Visit http://www.xuggle.com/xuggler/faq/ to find common solutions to this problem", (Object)string, l2 == null ? "" : l2);
                throw unsatisfiedLinkError;
            }
            this.c(string, l2);
        }
        ub.a("Successfully Loaded library: {}; Version: {}", (Object)string, (Object)l2);
    }

    void c(String string, Long l2) {
        HashSet<Long> hashSet = (HashSet<Long>)this.cUf.get(string);
        if (hashSet == null) {
            hashSet = new HashSet<Long>();
            this.cUf.put(string, hashSet);
        }
        hashSet.add(l2);
    }

    boolean a(String string, Long l2, List list) {
        boolean bl2 = false;
        for (String string2 : list) {
            ub.a("Attempt: library load of library: {}; version: {}: relative path: {}", new Object[]{string, l2 == null ? "<unspecified>" : Long.valueOf(l2), string2});
            File file = new File(string2);
            if (!file.exists()) continue;
            String string3 = file.getAbsolutePath();
            try {
                ub.a("Attempt: library load of library: {}; version: {}: absolute path: {}", new Object[]{string, l2 == null ? "<unspecified>" : Long.valueOf(l2), string3});
                System.load(string3);
                ub.a("Success: library load of library: {}; version: {}: absolute path: {}", new Object[]{string, l2 == null ? "<unspecified>" : Long.valueOf(l2), string3});
                this.c(string, l2);
                bl2 = true;
                break;
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                ub.warn("Failure: library load of library: {}; version: {}: absolute path: {}; error: {}", new Object[]{string, l2 == null ? "<unspecified>" : Long.valueOf(l2), string3, unsatisfiedLinkError});
            }
            catch (SecurityException securityException) {
                ub.warn("Failure: library load of library: {}; version: {}: absolute path: {}; error: {}", new Object[]{string, l2 == null ? "<unspecified>" : Long.valueOf(l2), string3, securityException});
            }
        }
        return bl2;
    }

    List d(String string, Long l2) {
        LinkedList linkedList = new LinkedList();
        String[] stringArray = null;
        String[] stringArray2 = null;
        String[] stringArray3 = null;
        String[] stringArray4 = null;
        switch (this.aGG()) {
            case dvi: 
            case dvl: {
                String[] stringArray5;
                stringArray = new String[]{"lib", ""};
                stringArray2 = new String[]{".so"};
                stringArray3 = new String[]{""};
                if (l2 == null) {
                    String[] stringArray6 = new String[1];
                    stringArray5 = stringArray6;
                    stringArray6[0] = "";
                } else {
                    String[] stringArray7 = new String[1];
                    stringArray5 = stringArray7;
                    stringArray7[0] = "." + l2;
                }
                stringArray4 = stringArray5;
                break;
            }
            case dvj: {
                String[] stringArray8;
                stringArray = new String[]{"lib", "", "cyg"};
                stringArray2 = new String[]{".dll"};
                if (l2 == null) {
                    String[] stringArray9 = new String[1];
                    stringArray8 = stringArray9;
                    stringArray9[0] = "";
                } else {
                    String[] stringArray10 = new String[1];
                    stringArray8 = stringArray10;
                    stringArray10[0] = "-" + l2;
                }
                stringArray3 = stringArray8;
                stringArray4 = new String[]{""};
                break;
            }
            case dvk: {
                String[] stringArray11;
                stringArray = new String[]{"lib", ""};
                stringArray2 = new String[]{".dylib"};
                if (l2 == null) {
                    String[] stringArray12 = new String[1];
                    stringArray11 = stringArray12;
                    stringArray12[0] = "";
                } else {
                    String[] stringArray13 = new String[1];
                    stringArray11 = stringArray13;
                    stringArray13[0] = "." + l2;
                }
                stringArray3 = stringArray11;
                stringArray4 = new String[]{""};
            }
        }
        this.aGF();
        if (l2 != null) {
            for (String string2 : this.cUc) {
                this.a(linkedList, string2, string, stringArray, stringArray2, stringArray3, stringArray4, true);
            }
            for (String string2 : this.cUd) {
                this.a(linkedList, string2, string, stringArray, stringArray2, stringArray3, stringArray4, true);
            }
        }
        for (String string2 : this.cUc) {
            this.a(linkedList, string2, string, stringArray, stringArray2, stringArray3, stringArray4, false);
        }
        for (String string2 : this.cUd) {
            this.a(linkedList, string2, string, stringArray, stringArray2, stringArray3, stringArray4, false);
        }
        return linkedList;
    }

    void a(List list, String string, String string2, String[] stringArray, String[] stringArray2, String[] stringArray3, String[] stringArray4, boolean bl2) {
        String string3 = File.separator;
        if (!string.endsWith(string3)) {
            string = string + string3;
        }
        for (String string4 : stringArray2) {
            for (String string5 : stringArray) {
                if (bl2) {
                    for (String string6 : stringArray3) {
                        for (String string7 : stringArray4) {
                            String string8 = string + string5 + string2 + string6 + string4 + string7;
                            list.add(string8);
                        }
                    }
                    continue;
                }
                String string9 = string + string5 + string2 + string4;
                list.add(string9);
            }
        }
    }

    private void aGF() {
        String string = null;
        if (this.cUc == null) {
            string = System.getProperty("java.library.path", "");
            ub.i("property java.library.path: {}", string);
            this.cUc = this.jB(string);
        }
        if (this.cUd == null) {
            String string2 = this.aGH();
            string = System.getenv(string2);
            ub.a("OS environment runtime shared library path ({}): {}", (Object)string2, (Object)string);
            this.cUd = this.jB(string);
        }
    }

    aCW aGG() {
        if (this.cUe != null) {
            return this.cUe;
        }
        aCW aCW2 = aCW.dvl;
        String string = System.getProperty("os.name", "Linux");
        if (string.length() > 0) {
            aCW2 = string.startsWith("Windows") ? aCW.dvj : (string.startsWith("Mac") ? aCW.dvk : (string.startsWith("Linux") ? aCW.dvl : aCW.dvl));
        }
        this.cUe = aCW2;
        ub.i("Detected OS: {}", (Object)this.cUe);
        return aCW2;
    }

    void a(aCW aCW2) {
        this.cUe = aCW2;
    }

    String aGH() {
        String string = "LD_LIBRARY_PATH";
        switch (this.aGG()) {
            case dvj: {
                string = "PATH";
                break;
            }
            case dvk: {
                string = "DYLD_LIBRARY_PATH";
                break;
            }
        }
        return string;
    }

    String[] jB(String string) {
        String[] stringArray = null;
        String string2 = File.pathSeparator;
        if (string == null || string.length() == 0) {
            stringArray = new String[]{"."};
            ub.trace("Have empty path var; assuming current directory to find native libraries");
        } else {
            ub.i("Parsing path var: {}", string);
            int n2 = string.length();
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            n4 = 1;
            n3 = string.indexOf(string2);
            while (n3 >= 0) {
                ++n4;
                n3 = string.indexOf(string2, n3 + 1);
            }
            ub.a("Found {} paths in path var: {}", n4, (Object)string);
            stringArray = new String[n4];
            n3 = 0;
            n4 = 0;
            n5 = string.indexOf(string2);
            while (n5 >= 0) {
                if (n5 - n3 > 0) {
                    stringArray[n4] = string.substring(n3, n5);
                    ub.a("Added path {} for path var: {}", (Object)stringArray[n4], (Object)string);
                    ++n4;
                } else if (n5 - n3 == 0) {
                    stringArray[n4] = ".";
                    ub.a("Added path {} for path var: {}", (Object)stringArray[n4], (Object)string);
                    ++n4;
                }
                n3 = n5 + 1;
                n5 = string.indexOf(string2, n3);
            }
            stringArray[n4] = string.substring(n3, n2);
            ub.a("Adding last path {} for path var: {}", (Object)stringArray[n4], (Object)string);
            if (stringArray[n4] == null || stringArray[n4].length() == 0) {
                stringArray[n4] = ".";
                ub.a("Faking last path {} for malformed path var: {}", (Object)stringArray[n4], (Object)string);
            }
        }
        return stringArray;
    }

    boolean e(String string, Long l2) {
        boolean bl2 = false;
        Set set = (Set)this.cUf.get(string);
        if (set != null) {
            if (l2 == null || set.contains(l2)) {
                bl2 = true;
            } else {
                ub.warn("Attempting load of {}, version {}, but already loaded verions: {}.  We will attempt to load the specified version but behavior is undefined", new Object[]{string, l2, set.toArray()});
            }
        }
        return bl2;
    }
}

