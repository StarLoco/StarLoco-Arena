/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/*
 * Renamed from aHu
 */
public class ahu_1 {
    private static final ga_2 xa = ga_2.Qo();
    private static final wb_2 dMm = new id_1(new afa_0());

    public static iv_1[] a(aat_0 aat_02, iv_1[] iv_1Array, yx_2 yx_22, axm_0 axm_02) {
        return ahu_1.a(aat_02, iv_1Array, yx_22, axm_02, xa.Qp());
    }

    public static iv_1[] a(aat_0 aat_02, iv_1[] iv_1Array, yx_2 yx_22, axm_0 axm_02, long l2) {
        uW uW2 = new uW();
        uW2.a(Arrays.asList(iv_1Array));
        mx_2 mx_22 = ahu_1.a(aat_02, uW2, yx_22, axm_02, l2);
        return mx_22.size() == 0 ? new iv_1[]{} : ((uW)mx_22).AZ();
    }

    public static mx_2 a(aat_0 aat_02, mx_2 mx_22, yx_2 yx_22, axm_0 axm_02, long l2) {
        if (mx_22.size() == 0) {
            aat_02.l("No sources found.", 3);
            return afx_1.csW;
        }
        mx_22 = uW.c(mx_22);
        ahu_1.a(aat_02, mx_22, l2);
        uW uW2 = new uW();
        Iterator iterator = mx_22.iterator();
        while (iterator.hasNext()) {
            iv_1 iv_12 = (iv_1)iterator.next();
            String string = iv_12.getName();
            string = string == null ? string : string.replace('/', File.separatorChar);
            String[] stringArray = null;
            try {
                stringArray = yx_22.bT(string);
            }
            catch (Exception exception) {
                aat_02.l("Caught " + exception + " mapping resource " + iv_12, 3);
            }
            if (stringArray == null || stringArray.length == 0) {
                aat_02.l(iv_12 + " skipped - don't know how to handle it", 3);
                continue;
            }
            uW uW3 = new uW();
            for (int j = 0; j < stringArray.length; ++j) {
                uW3.a(axm_02.gj(stringArray[j].replace(File.separatorChar, '/')));
            }
            Ue ue = new Ue();
            ue.a(new ls_1(iv_12, l2));
            ue.a(uW3);
            if (ue.size() > 0) {
                uW2.a(iv_12);
                iv_1 iv_13 = (iv_1)ue.iterator().next();
                aat_02.l(iv_12.getName() + " added as " + iv_13.getName() + (iv_13.lI() ? " is outdated." : " doesn't exist."), 3);
                continue;
            }
            aat_02.l(iv_12.getName() + " omitted as " + uW3.toString() + (uW3.size() == 1 ? " is" : " are ") + " up to date.", 3);
        }
        return uW2;
    }

    public static void a(iv_1 iv_12, iv_1 iv_13) {
        ahu_1.a(iv_12, iv_13, null);
    }

    public static void a(iv_1 iv_12, iv_1 iv_13, UI uI) {
        ahu_1.a(iv_12, iv_13, null, null, false, false, null, null, uI);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void a(iv_1 iv_12, iv_1 iv_13, agd_2 agd_22, Vector vector, boolean bl2, boolean bl3, String string, String string2, UI uI) {
        block18: {
            boolean bl4;
            if (!bl2) {
                long l2 = iv_12.getLastModified();
                if (iv_13.lI() && l2 != 0L && iv_13.getLastModified() > l2) {
                    return;
                }
            }
            boolean bl5 = agd_22 != null && agd_22.anv();
            boolean bl6 = bl4 = vector != null && vector.size() > 0;
            if (bl5) {
                BufferedReader bufferedReader = null;
                BufferedWriter bufferedWriter = null;
                try {
                    Object object;
                    Object object2;
                    InputStreamReader inputStreamReader = null;
                    inputStreamReader = string == null ? new InputStreamReader(iv_12.getInputStream()) : new InputStreamReader(iv_12.getInputStream(), string);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    OutputStreamWriter outputStreamWriter = null;
                    outputStreamWriter = string2 == null ? new OutputStreamWriter(iv_13.getOutputStream()) : new OutputStreamWriter(iv_13.getOutputStream(), string2);
                    bufferedWriter = new BufferedWriter(outputStreamWriter);
                    if (bl4) {
                        object2 = new li_0();
                        ((li_0)object2).setBufferSize(8192);
                        ((li_0)object2).f(bufferedReader);
                        ((li_0)object2).c(vector);
                        ((li_0)object2).l(uI);
                        object = ((li_0)object2).Xx();
                        bufferedReader = new BufferedReader((Reader)object);
                    }
                    object2 = new ia_0();
                    ((ia_0)object2).x(true);
                    object = null;
                    String string3 = ((ia_0)object2).a(bufferedReader);
                    while (string3 != null) {
                        if (string3.length() == 0) {
                            bufferedWriter.newLine();
                        } else {
                            object = agd_22.gT(string3);
                            bufferedWriter.write((String)object);
                        }
                        string3 = ((ia_0)object2).a(bufferedReader);
                    }
                    Object var19_30 = null;
                }
                catch (Throwable throwable) {
                    Object var19_31 = null;
                    ga_2.a(bufferedWriter);
                    ga_2.e(bufferedReader);
                    throw throwable;
                }
                ga_2.a(bufferedWriter);
                ga_2.e(bufferedReader);
                {
                    break block18;
                }
            }
            if (bl4 || string != null && !string.equals(string2) || string == null && string2 != null) {
                BufferedReader bufferedReader = null;
                BufferedWriter bufferedWriter = null;
                try {
                    int n2;
                    Object object;
                    InputStreamReader inputStreamReader = null;
                    inputStreamReader = string == null ? new InputStreamReader(iv_12.getInputStream()) : new InputStreamReader(iv_12.getInputStream(), string);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    OutputStreamWriter outputStreamWriter = null;
                    outputStreamWriter = string2 == null ? new OutputStreamWriter(iv_13.getOutputStream()) : new OutputStreamWriter(iv_13.getOutputStream(), string2);
                    bufferedWriter = new BufferedWriter(outputStreamWriter);
                    if (bl4) {
                        object = new li_0();
                        ((li_0)object).setBufferSize(8192);
                        ((li_0)object).f(bufferedReader);
                        ((li_0)object).c(vector);
                        ((li_0)object).l(uI);
                        Reader reader = ((li_0)object).Xx();
                        bufferedReader = new BufferedReader(reader);
                    }
                    object = new char[8192];
                    while ((n2 = bufferedReader.read((char[])object, 0, ((Object)object).length)) != -1) {
                        bufferedWriter.write((char[])object, 0, n2);
                    }
                    Object var21_33 = null;
                }
                catch (Throwable throwable) {
                    Object var21_34 = null;
                    ga_2.a(bufferedWriter);
                    ga_2.e(bufferedReader);
                    throw throwable;
                }
                ga_2.a(bufferedWriter);
                ga_2.e(bufferedReader);
                {
                    break block18;
                }
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = iv_12.getInputStream();
                outputStream = iv_13.getOutputStream();
                byte[] byArray = new byte[8192];
                int n3 = 0;
                do {
                    outputStream.write(byArray, 0, n3);
                } while ((n3 = inputStream.read(byArray, 0, byArray.length)) != -1);
                Object var23_36 = null;
            }
            catch (Throwable throwable) {
                Object var23_37 = null;
                ga_2.a(outputStream);
                ga_2.h(inputStream);
                throw throwable;
            }
            ga_2.a(outputStream);
            ga_2.h(inputStream);
            {
            }
        }
        if (bl3 && iv_13 instanceof qO) {
            ahu_1.a((qO)((Object)iv_13), iv_12.getLastModified());
        }
    }

    public static void a(qO qO2, long l2) {
        qO2.aC(l2 < 0L ? System.currentTimeMillis() : l2);
    }

    public static boolean a(iv_1 iv_12, iv_1 iv_13, boolean bl2) {
        if (iv_12.lI() != iv_13.lI()) {
            return false;
        }
        if (!iv_12.lI()) {
            return true;
        }
        if (iv_12.isDirectory() || iv_13.isDirectory()) {
            return false;
        }
        if (iv_12.equals(iv_13)) {
            return true;
        }
        if (!bl2) {
            long l2 = iv_12.getSize();
            long l3 = iv_13.getSize();
            if (l2 != -1L && l3 != -1L && l2 != l3) {
                return false;
            }
        }
        return ahu_1.b(iv_12, iv_13, bl2) == 0;
    }

    public static int b(iv_1 iv_12, iv_1 iv_13, boolean bl2) {
        if (iv_12.equals(iv_13)) {
            return 0;
        }
        boolean bl3 = iv_12.lI();
        boolean bl4 = iv_13.lI();
        if (!bl3 && !bl4) {
            return 0;
        }
        if (bl3 != bl4) {
            return bl3 ? 1 : -1;
        }
        boolean bl5 = iv_12.isDirectory();
        boolean bl6 = iv_13.isDirectory();
        if (bl5 && bl6) {
            return 0;
        }
        if (bl5 || bl6) {
            return bl5 ? -1 : 1;
        }
        return bl2 ? ahu_1.c(iv_12, iv_13) : ahu_1.b(iv_12, iv_13);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int b(iv_1 iv_12, iv_1 iv_13) {
        int n2;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2;
        block5: {
            int n3;
            block4: {
                bufferedInputStream2 = null;
                bufferedInputStream = null;
                try {
                    bufferedInputStream2 = new BufferedInputStream(iv_12.getInputStream());
                    bufferedInputStream = new BufferedInputStream(iv_13.getInputStream());
                    n2 = ((InputStream)bufferedInputStream2).read();
                    while (n2 != -1) {
                        int n4 = ((InputStream)bufferedInputStream).read();
                        if (n2 != n4) {
                            n3 = n2 > n4 ? 1 : -1;
                            Object var8_7 = null;
                            break block4;
                        }
                        n2 = ((InputStream)bufferedInputStream2).read();
                    }
                    n2 = ((InputStream)bufferedInputStream).read() == -1 ? 0 : -1;
                    break block5;
                }
                catch (Throwable throwable) {
                    Object var8_9 = null;
                    ga_2.h(bufferedInputStream2);
                    ga_2.h(bufferedInputStream);
                    throw throwable;
                }
            }
            ga_2.h(bufferedInputStream2);
            ga_2.h(bufferedInputStream);
            return n3;
        }
        Object var8_8 = null;
        ga_2.h(bufferedInputStream2);
        ga_2.h(bufferedInputStream);
        return n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int c(iv_1 iv_12, iv_1 iv_13) {
        int n2;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        block5: {
            int n3;
            block4: {
                bufferedReader2 = null;
                bufferedReader = null;
                try {
                    bufferedReader2 = new BufferedReader(new InputStreamReader(iv_12.getInputStream()));
                    bufferedReader = new BufferedReader(new InputStreamReader(iv_13.getInputStream()));
                    String string = bufferedReader2.readLine();
                    while (string != null) {
                        String string2 = bufferedReader.readLine();
                        if (!string.equals(string2)) {
                            n3 = string.compareTo(string2);
                            Object var8_8 = null;
                            break block4;
                        }
                        string = bufferedReader2.readLine();
                    }
                    n2 = bufferedReader.readLine() == null ? 0 : -1;
                    break block5;
                }
                catch (Throwable throwable) {
                    Object var8_10 = null;
                    ga_2.e(bufferedReader2);
                    ga_2.e(bufferedReader);
                    throw throwable;
                }
            }
            ga_2.e(bufferedReader2);
            ga_2.e(bufferedReader);
            return n3;
        }
        Object var8_9 = null;
        ga_2.e(bufferedReader2);
        ga_2.e(bufferedReader);
        return n2;
    }

    private static void a(aat_0 aat_02, mx_2 mx_22, long l2) {
        long l3 = System.currentTimeMillis() + l2;
        aaG aaG2 = new aaG();
        aaG2.setMillis(l3);
        aaG2.a(mm_2.Jw);
        Ue ue = new Ue();
        ue.a(aaG2);
        ue.a(mx_22);
        Iterator iterator = ue.iterator();
        while (iterator.hasNext()) {
            aat_02.l("Warning: " + ((iv_1)iterator.next()).getName() + " modified in the future.", 1);
        }
    }
}

