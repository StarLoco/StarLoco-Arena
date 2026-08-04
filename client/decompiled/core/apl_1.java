/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Renamed from apL
 */
public final class apl_1
extends ace_0 {
    private final lb_0 cMW = new lb_0();
    private final lb_0 cCZ = new lb_0();
    private static final boolean cMX = false;
    private final String cMY;
    private int cMZ = 20000000;
    private int cNa = 500;
    private la_2 cNb;
    private final Object no = new Object();
    private final StringBuilder cNc = new StringBuilder(20);
    private static final Pattern cNd = Pattern.compile("[^a-zA-Z0-9-_/\\.]");
    private boolean cDf;
    private final File cNe;
    private final File cNf;
    private final File cNg;
    private final File cNh;
    private final cp_2 cNi = new cp_2();
    private final lb_0 cNj = new lb_0();
    private static final String cNk = "data.";
    private static final String cNl = ".bdat";
    private static final String cNm = "index.";
    private static final String cNn = ".bdat";
    private static final String cNo = "metadata.bdat";
    private final mt_0 cDd;
    private final mt_0 cDe = mt_0.b(mt_0.btS);
    private final mt_0 cNp = mt_0.b(mt_0.btS);

    protected apl_1(String string) {
        this(string, false);
    }

    private apl_1(String string, boolean bl2) {
        this.cMY = apl_1.iU(string);
        this.cNh = new File(this.cMY);
        this.cNe = new File(this.cMY + "~building_index.tmp");
        this.cNf = new File(this.cMY + "~building_data.tmp");
        this.cNg = new File(this.cMY + cNo);
        this.setName("BinaryStorage (" + this.cMY + ")");
        mt_0 mt_02 = this.cDd = bl2 ? mt_0.b(mt_0.btT) : mt_0.b(mt_0.btS);
        if (this.art()) {
            this.start();
        } else {
            a.error((Object)("Echec de l'initialisation du binary storage " + this));
        }
    }

    public static boolean iT(String string) {
        File file = new File(apl_1.iU(string) + cNo);
        return file.exists();
    }

    private static String iU(String string) {
        String string2 = string;
        if ((string2 = cNd.matcher(string2).replaceAll("_")).charAt(0) == '/') {
            string2 = string2.substring(1, string2.length());
        }
        if (string2.charAt(string2.length() - 1) != '/') {
            string2 = string2 + "/";
        }
        return string2;
    }

    public boolean azI() {
        return this.cDf;
    }

    protected String aru() {
        return this.cMY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean art() {
        Object object = this.no;
        synchronized (object) {
            block29: {
                if (this.cDf) {
                    a.error((Object)("Binary storage already initialize : " + this.cMY));
                    return false;
                }
                try {
                    if (this.cNh.exists() && !this.cNh.isDirectory()) {
                        a.error((Object)("Tentative de changement de workspace [" + this.cMY + "] vers un fichier [not directory] existant [Aborted & Shutdown]"));
                        return false;
                    }
                    if (!this.cNh.exists() && !this.cNh.mkdirs()) {
                        a.error((Object)("Impossible de creer l'arborescence de repertoire [" + this.cMY + "] lors d'un changement de workspace inexistant [Aborted & Shutdown]"));
                        return false;
                    }
                    this.cMW.clear();
                    if (!this.cNg.exists()) {
                        this.cNg.createNewFile();
                        a.info((Object)"Fichier de meta donn\u00e9es non trouv\u00e9 pour le chargement de la source binaire : Creation d'une nouvelle source");
                        break block29;
                    }
                    Object object2 = null;
                    Object object3 = null;
                    try {
                        object2 = new FileInputStream(this.cNg);
                        object3 = this.cNp.a((FileInputStream)object2);
                        try {
                            while (true) {
                                int n2 = ((DataInputStream)object3).readInt();
                                int n3 = ((DataInputStream)object3).readInt();
                                int n4 = ((DataInputStream)object3).readInt();
                                int n5 = ((DataInputStream)object3).readInt();
                                int n6 = n2;
                                ArrayList<la_2> arrayList = (ArrayList<la_2>)this.cMW.get(n6);
                                if (arrayList == null) {
                                    arrayList = new ArrayList<la_2>();
                                    this.cMW.c(n6, arrayList);
                                }
                                arrayList.add(new la_2(this, n6, n3, n4, n5));
                            }
                        }
                        catch (EOFException eOFException) {
                            if (object3 != null) {
                                try {
                                    ((FilterInputStream)object3).close();
                                }
                                catch (IOException iOException) {
                                    a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                                }
                            }
                            if (object2 != null) {
                                try {
                                    ((FileInputStream)object2).close();
                                }
                                catch (IOException iOException) {
                                    a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                                }
                            }
                        }
                    }
                    catch (Throwable throwable) {
                        if (object3 != null) {
                            try {
                                ((FilterInputStream)object3).close();
                            }
                            catch (IOException iOException) {
                                a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                            }
                        }
                        if (object2 != null) {
                            try {
                                ((FileInputStream)object2).close();
                            }
                            catch (IOException iOException) {
                                a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                            }
                        }
                        throw throwable;
                    }
                }
                catch (FileNotFoundException fileNotFoundException) {
                    a.error((Object)fileNotFoundException.getMessage(), (Throwable)fileNotFoundException);
                    return false;
                }
                catch (IOException iOException) {
                    a.error((Object)iOException.getMessage(), (Throwable)iOException);
                    return false;
                }
            }
            this.cDf = true;
            for (Object object3 : this.ckD) {
                object3.a((ace_0)this, this.aru());
            }
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void aDG() {
        try {
            FileOutputStream fileOutputStream = null;
            FilterOutputStream filterOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(this.cNg, false);
                FilterOutputStream filterOutputStream2 = filterOutputStream = this.cNp.a(fileOutputStream);
                if (!this.cMW.isEmpty()) {
                    this.cMW.a(new azo(this, (DataOutputStream)filterOutputStream2));
                }
            }
            finally {
                if (filterOutputStream != null) {
                    try {
                        filterOutputStream.close();
                    }
                    catch (IOException iOException) {
                        a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    }
                    catch (IOException iOException) {
                        a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                    }
                }
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            a.error((Object)fileNotFoundException.getMessage(), (Throwable)fileNotFoundException);
        }
        catch (IOException iOException) {
            a.error((Object)iOException.getMessage(), (Throwable)iOException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void c(lJ lJ2) {
        Object object = this.no;
        synchronized (object) {
            ArrayList arrayList = this.a(ckI, lJ2.cq(), (Object)lJ2.qw(), 1);
            if (arrayList.size() <= 0) {
                this.d(lJ2);
            } else {
                this.a(lJ2, (wo_0)arrayList.get(0));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void d(lJ lJ2) {
        byte[] byArray = lJ2.cr();
        if (byArray == null) {
            a.error((Object)("Tentative de sauvegarde d'un binary storable qui n'a aucun bloc de donn\u00e9es " + lJ2));
            return;
        }
        long l2 = lJ2.m(byArray);
        int n2 = byArray.length + 4 + 2 + 4;
        this.bP(lJ2.cq(), n2);
        try {
            Field[] fieldArray;
            long l3;
            File file = this.cNb.ll;
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = null;
            FilterOutputStream filterOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(this.cNb.ll, true);
                filterOutputStream = this.cDd.a(fileOutputStream);
                l3 = fileOutputStream.getChannel().size();
                fieldArray = new azf(lJ2.qw(), lJ2.qx(), byArray);
                fieldArray.write((DataOutputStream)filterOutputStream);
            }
            finally {
                if (filterOutputStream != null) {
                    try {
                        filterOutputStream.close();
                    }
                    catch (IOException iOException) {
                        a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    }
                    catch (IOException iOException) {
                        a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                    }
                }
            }
            ++this.cNb.Gd;
            this.cNb.size += n2;
            this.a(ckI, lJ2.qw(), lJ2.cq(), this.cNb.Gc, l3, l2);
            for (Field field : fieldArray = lJ2.getClass().getDeclaredFields()) {
                Object object;
                if (!field.isAnnotationPresent(ays_0.class)) continue;
                ays_0 ays_02 = field.getAnnotation(ays_0.class);
                if (field.isAccessible()) {
                    object = field.get(lJ2);
                } else {
                    field.setAccessible(true);
                    object = field.get(lJ2);
                    field.setAccessible(false);
                }
                this.a(ays_02.name().hashCode(), object, lJ2.cq(), this.cNb.Gc, l3, l2);
            }
            this.aDG();
        }
        catch (IOException iOException) {
            a.error((Object)iOException.getMessage(), (Throwable)iOException);
        }
        catch (IllegalAccessException illegalAccessException) {
            a.error((Object)illegalAccessException.getMessage(), (Throwable)illegalAccessException);
        }
    }

    private void a(lJ lJ2, wo_0 wo_02) {
        wo_0 wo_03 = wo_02;
        byte[] byArray = lJ2.cr();
        if (byArray == null) {
            a.error((Object)("Tentative de sauvegarde d'un binary storable qui n'a aucun bloc de donn\u00e9es " + lJ2));
            return;
        }
        if (wo_03.bTY != lJ2.m(byArray)) {
            this.b(lJ2, wo_02);
            this.d(lJ2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void b(lJ lJ2) {
        Object object = this.no;
        synchronized (object) {
            ArrayList arrayList = this.a(ckI, lJ2.cq(), (Object)lJ2.qw(), 1);
            if (!arrayList.isEmpty()) {
                this.b(lJ2, (wo_0)arrayList.get(0));
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(lJ lJ2, wo_0 wo_02) {
        this.bQ(lJ2.cq(), wo_02.Gc);
        try {
            IOException iOException3;
            int n2;
            FileOutputStream fileOutputStream;
            FileInputStream fileInputStream;
            block20: {
                fileInputStream = null;
                FilterInputStream filterInputStream = null;
                fileOutputStream = null;
                try {
                    this.cNc.setLength(0);
                    fileInputStream = new FileInputStream(this.cNb.ll);
                    filterInputStream = this.cDd.a(fileInputStream);
                    int n3 = (int)fileInputStream.getChannel().size();
                    fileOutputStream = new FileOutputStream(this.cNf, false);
                    fileInputStream.getChannel().position(wo_02.bTX);
                    n2 = azf.m((DataInputStream)filterInputStream);
                    long l2 = wo_02.bTX + (long)n2;
                    fileInputStream.getChannel().transferTo(0L, wo_02.bTX, fileOutputStream.getChannel());
                    fileInputStream.getChannel().transferTo(l2, (long)n3 - l2, fileOutputStream.getChannel());
                    Object var11_12 = null;
                    if (filterInputStream == null) break block20;
                }
                catch (Throwable throwable) {
                    IOException iOException22;
                    Object var11_13 = null;
                    if (filterInputStream != null) {
                        try {
                            filterInputStream.close();
                        }
                        catch (IOException iOException22) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException22);
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        }
                        catch (IOException iOException22) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException22);
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        }
                        catch (IOException iOException22) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException22);
                        }
                    }
                    throw throwable;
                }
                try {
                    filterInputStream.close();
                }
                catch (IOException iOException3) {
                    a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException3);
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (IOException iOException3) {
                    a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException3);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException iOException3) {
                    a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException3);
                }
            }
            this.cNc.setLength(0);
            File file = this.cNb.ll;
            if (file.exists()) {
                file.delete();
            }
            this.cNf.renameTo(file);
            --this.cNb.Gd;
            this.cNb.size -= n2;
            this.a(wo_02.Gc, wo_02.bTX, n2, lJ2);
            this.aDG();
        }
        catch (FileNotFoundException fileNotFoundException) {
            a.error((Object)fileNotFoundException.getMessage(), (Throwable)fileNotFoundException);
        }
        catch (IOException iOException) {
            a.error((Object)iOException.getMessage(), (Throwable)iOException);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private void a(int var1_1, long var2_2, long var4_3, lJ var6_4) {
        block15: {
            try {
                var7_5 = var6_4.cq();
                var8_7 = (lb_0)this.cCZ.get(var7_5);
                if (var8_7 != null) {
                    var9_8 = var8_7.pK();
                    while (var9_8.hasNext()) {
                        var9_8.fK();
                        var10_9 = null;
                        var11_10 = null;
                        try {
                            var10_9 = new FileOutputStream(this.cNe, false);
                            var11_10 = this.cDe.a(var10_9);
                            var12_11 = ((ArrayList)var9_8.value()).iterator();
                            while (var12_11.hasNext()) {
                                var13_12 = (wo_0)var12_11.next();
                                if (var13_12.Gc == var1_1 && var13_12.bTX > var2_2) {
                                    var13_12.bTX -= var4_3;
                                    var13_12.write((DataOutputStream)var11_10);
                                    continue;
                                }
                                if (var13_12.Gc == var1_1 && var13_12.bTX == var2_2) {
                                    var12_11.remove();
                                    var13_12.release();
                                    continue;
                                }
                                if (var1_1 == var13_12.Gc && (var1_1 != var13_12.Gc || var13_12.bTX >= var2_2)) continue;
                                var13_12.write((DataOutputStream)var11_10);
                            }
                            var15_13 = null;
                            ** if (var11_10 == null) goto lbl-1000
                        }
                        catch (Throwable var14_15) {
                            var15_13 = null;
                            if (var11_10 != null) {
                                try {
                                    var11_10.close();
                                }
                                catch (IOException var16_14) {
                                    apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var16_14);
                                }
                            }
                            throw var14_15;
                        }
lbl-1000:
                        // 1 sources

                        {
                            try {
                                var11_10.close();
                            }
                            catch (IOException var16_14) {
                                apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var16_14);
                            }
                        }
lbl-1000:
                        // 2 sources

                        {
                        }
                        if ((var12_11 = this.bR(var9_8.kR(), var7_5)).exists()) {
                            var12_11.delete();
                        }
                        this.cNe.renameTo((File)var12_11);
                    }
                    break block15;
                }
                apl_1.a.error((Object)"Situation anormale : on met a jour des indexes qu'on a pas encore mont\u00e9 en memoire");
            }
            catch (IOException var7_6) {
                apl_1.a.error((Object)var7_6.getMessage(), (Throwable)var7_6);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private lJ[] a(ArrayList var1_1, lJ var2_2) {
        if (var1_1.size() == 0) {
            return null;
        }
        var3_3 = new ArrayList<lJ>();
        var4_4 = var2_2.cq();
        var5_5 = var1_1.iterator();
        block12: while (true) {
            if (!var5_5.hasNext()) {
                if (var3_3.size() <= 0) return null;
                return var3_3.toArray(new lJ[var3_3.size()]);
            }
            var6_6 = (wo_0)var5_5.next();
            var7_7 = var6_6.Gc;
            var8_8 = var6_6.bTX;
            this.bQ(var4_4, var7_7);
            try {
                block26: {
                    block24: {
                        block22: {
                            block23: {
                                this.cNc.setLength(0);
                                var10_9 = this.cNb.ll;
                                if (!var10_9.exists()) {
                                    return null;
                                }
                                var11_11 = null;
                                var12_12 = null;
                                var13_13 = null;
                                try {
                                    var11_11 = new FileInputStream(var10_9);
                                    var12_12 = this.cDd.a(var11_11);
                                    if (var8_8 < 0L) {
                                        apl_1.a.fatal((Object)"position n\u00e9gative");
                                        var14_14 = null;
                                        var16_17 = null;
                                        if (var12_12 == null) break block22;
                                        break block23;
                                    }
                                    var11_11.getChannel().position(var8_8);
                                    var13_13 = new azf();
                                    var13_13.read(var12_12);
                                    break block24;
                                }
                                catch (Throwable var15_16) {
                                    var16_17 = null;
                                    if (var12_12 != null) {
                                        try {
                                            var12_12.close();
                                        }
                                        catch (IOException var17_18) {
                                            apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var17_18);
                                        }
                                    }
                                    if (var11_11 == null) throw var15_16;
                                    try {
                                        var11_11.close();
                                        throw var15_16;
                                    }
                                    catch (IOException var17_18) {
                                        apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var17_18);
                                    }
                                    throw var15_16;
                                }
                            }
                            ** try [egrp 2[TRYBLOCK] [3 : 204->212)] { 
lbl58:
                            // 1 sources

                            var12_12.close();
                            break block22;
lbl60:
                            // 1 sources

                            catch (IOException var17_18) {
                                apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var17_18);
                            }
                        }
                        if (var11_11 == null) return var14_14;
                        ** try [egrp 3[TRYBLOCK] [4 : 229->237)] { 
lbl65:
                        // 1 sources

                        var11_11.close();
                        return var14_14;
lbl67:
                        // 1 sources

                        catch (IOException var17_18) {
                            apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var17_18);
                        }
                        return var14_14;
                    }
                    var16_17 = null;
                    if (var12_12 != null) {
                        ** try [egrp 2[TRYBLOCK] [3 : 204->212)] { 
lbl74:
                        // 1 sources

                        var12_12.close();
                        break block26;
lbl76:
                        // 1 sources

                        catch (IOException var17_18) {
                            apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var17_18);
                        }
                    }
                }
                if (var11_11 != null) {
                    ** try [egrp 3[TRYBLOCK] [4 : 229->237)] { 
lbl81:
                    // 1 sources

                    var11_11.close();
lbl83:
                    // 1 sources

                    catch (IOException var17_18) {
                        apl_1.a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)var17_18);
                    }
                }
                var14_14 = var2_2.cs();
                var15_15 = ByteBuffer.wrap(var13_13.getData());
                var14_14.a(var15_15, var13_13.getId(), var13_13.qx());
                if (var15_15.remaining() != 0) {
                    apl_1.a.warn((Object)("Objet restaur\u00e9 du binary storage : " + var15_15.remaining() + " bytes restants non lus [type:" + var2_2.cq() + " | id:" + var13_13.getId() + "]"));
                }
                var3_3.add(var14_14);
                var16_17 = this.ckD.iterator();
                while (true) {
                    if (!var16_17.hasNext()) continue block12;
                    var17_18 = (tp_2)var16_17.next();
                    var17_18.c(this, var14_14);
                }
            }
            catch (IOException var10_10) {
                apl_1.a.error((Object)var10_10.getMessage(), (Throwable)var10_10);
                continue;
            }
            break;
        }
    }

    public lJ[] a(String string, Object object, lJ lJ2) {
        return this.a(string.hashCode(), object, lJ2);
    }

    public lJ[] a(int n2, Object object, lJ lJ2) {
        return this.a(n2, object, lJ2, Integer.MAX_VALUE);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public lJ[] a(int n2, Object object, lJ lJ2, int n3) {
        Object object2 = this.no;
        synchronized (object2) {
            return this.a(this.a(n2, lJ2.cq(), object, n3), lJ2);
        }
    }

    public lJ a(int n2, lJ lJ2) {
        lJ[] lJArray = this.a(ckI, (Object)n2, lJ2, 1);
        if (lJArray != null && lJArray.length > 0) {
            return lJArray[0];
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public lJ[] a(lJ lJ2) {
        Object object = this.no;
        synchronized (object) {
            ArrayList arrayList;
            lb_0 lb_02 = (lb_0)this.cCZ.get(lJ2.cq());
            if (lb_02 == null) {
                this.lM(lJ2.cq());
                lb_02 = (lb_0)this.cCZ.get(lJ2.cq());
            }
            if ((arrayList = (ArrayList)lb_02.get(ckI)) == null || arrayList.isEmpty()) {
                return null;
            }
            return this.a(arrayList, lJ2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void arv() {
        Object object = this.no;
        synchronized (object) {
            File[] fileArray;
            for (File file : fileArray = this.cNh.listFiles(new azu_0(this))) {
                file.delete();
            }
        }
    }

    private void bP(int n2, int n3) {
        ArrayList<la_2> arrayList = (ArrayList<la_2>)this.cMW.get(n2);
        la_2 la_22 = null;
        if (arrayList == null) {
            la_22 = new la_2(this, n2, 1, 0, 0);
            arrayList = new ArrayList<la_2>();
            arrayList.add(la_22);
            this.cMW.c(n2, arrayList);
        }
        int n4 = 0;
        for (la_2 la_23 : arrayList) {
            if (la_23.Gc > n4) {
                n4 = la_23.Gc;
            }
            if (this.cNa >= 0 && la_23.Gd + 1 > this.cNa || this.cMZ >= 0 && n3 + la_23.size > this.cMZ) continue;
            la_22 = la_23;
            break;
        }
        if (la_22 == null) {
            la_22 = new la_2(this, n2, n4 + 1, 0, 0);
            arrayList.add(la_22);
        }
        this.a(la_22);
    }

    private boolean bQ(int n2, int n3) {
        ArrayList<la_2> arrayList = (ArrayList<la_2>)this.cMW.get(n2);
        la_2 la_22 = null;
        if (arrayList == null) {
            la_22 = new la_2(this, n2, 1, 0, 0);
            arrayList = new ArrayList<la_2>();
            arrayList.add(la_22);
            this.cMW.c(n2, arrayList);
        }
        for (la_2 la_23 : arrayList) {
            if (la_23.Gc != n3) continue;
            this.a(la_23);
            return true;
        }
        return false;
    }

    private void a(la_2 la_22) {
        this.cNb = la_22;
    }

    private File bR(int n2, int n3) {
        long l2 = ej_0.o(n3, n2);
        File file = (File)this.cNi.t(l2);
        if (file != null) {
            return file;
        }
        this.cNc.setLength(0);
        String string = this.cNc.append(this.cMY).append(cNm).append(n3).append("_").append(n2).append(".bdat").toString();
        file = new File(string);
        this.cNi.a(l2, file);
        return file;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(int n2, Object object, int n3, int n4, long l2, long l3) {
        try {
            lb_0 lb_02;
            IOException iOException3;
            ArrayList<wo_0> arrayList;
            wo_0 wo_02;
            FileOutputStream fileOutputStream;
            block16: {
                File file = this.bR(n2, n3);
                if (!file.exists()) {
                    file.createNewFile();
                }
                fileOutputStream = null;
                FilterOutputStream filterOutputStream = null;
                wo_02 = null;
                try {
                    fileOutputStream = new FileOutputStream(file, true);
                    filterOutputStream = this.cDe.a(fileOutputStream);
                    wo_02 = wo_0.a(object.toString(), n4, l2, l3);
                    wo_02.write((DataOutputStream)filterOutputStream);
                    arrayList = null;
                    if (filterOutputStream == null) break block16;
                }
                catch (Throwable throwable) {
                    IOException iOException22;
                    Object var14_13 = null;
                    if (filterOutputStream != null) {
                        try {
                            filterOutputStream.close();
                        }
                        catch (IOException iOException22) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException22);
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        }
                        catch (IOException iOException22) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException22);
                        }
                    }
                    throw throwable;
                }
                try {
                    filterOutputStream.close();
                }
                catch (IOException iOException3) {
                    a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException3);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException iOException3) {
                    a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException3);
                }
            }
            if ((lb_02 = (lb_0)this.cCZ.get(n3)) == null) {
                this.lM(n3);
                lb_02 = (lb_0)this.cCZ.get(n3);
            }
            if ((arrayList = (ArrayList<wo_0>)lb_02.get(n2)) == null) {
                arrayList = new ArrayList<wo_0>(50);
                lb_02.c(n2, arrayList);
            }
            arrayList.add(wo_02);
        }
        catch (IOException iOException) {
            a.error((Object)iOException.getMessage(), (Throwable)iOException);
        }
    }

    private ArrayList a(int n2, int n3, Object object, int n4) {
        ArrayList arrayList;
        lb_0 lb_02 = (lb_0)this.cCZ.get(n3);
        if (lb_02 == null) {
            this.lM(n3);
            lb_02 = (lb_0)this.cCZ.get(n3);
        }
        ArrayList<wo_0> arrayList2 = new ArrayList<wo_0>();
        if (lb_02 != null && (arrayList = (ArrayList)lb_02.get(n2)) != null) {
            int n5 = arrayList.size();
            String string = object.toString();
            for (int j = 0; j < n5; ++j) {
                wo_0 wo_02 = (wo_0)arrayList.get(j);
                if (!wo_02.value.equals(string)) continue;
                arrayList2.add(wo_02);
                if (arrayList2.size() >= n4) break;
            }
        }
        return arrayList2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lM(int n2) {
        Pattern pattern = Pattern.compile(cNm.replaceAll("\\.", "\\\\\\.") + n2 + "_([a-zA-Z0-9_.-]+)" + ".bdat".replaceAll("\\.", "\\\\\\."));
        File[] fileArray = this.cNh.listFiles(new azt(this, pattern));
        lb_0 lb_02 = (lb_0)this.cCZ.get(n2);
        if (lb_02 == null) {
            lb_02 = new lb_0();
            this.cCZ.c(n2, lb_02);
        }
        for (File file : fileArray) {
            int n3;
            Matcher matcher = pattern.matcher(file.getName());
            if (!matcher.matches()) continue;
            try {
                n3 = Integer.parseInt(matcher.group(1));
            }
            catch (NumberFormatException numberFormatException) {
                a.error((Object)("Nom de fichier d'index mal form\u00e9 : " + file.getName()));
                continue;
            }
            try {
                IOException iOException2;
                Object var16_18;
                FileInputStream fileInputStream = null;
                DataInputStream dataInputStream = null;
                try {
                    fileInputStream = new FileInputStream(file);
                    dataInputStream = this.cDe.a(fileInputStream);
                    try {
                        while (true) {
                            wo_0 wo_02 = wo_0.ajd();
                            wo_02.read(dataInputStream);
                            ArrayList<wo_0> arrayList = (ArrayList<wo_0>)lb_02.get(n3);
                            if (arrayList == null) {
                                arrayList = new ArrayList<wo_0>();
                                lb_02.c(n3, arrayList);
                            }
                            arrayList.add(wo_02);
                        }
                    }
                    catch (EOFException eOFException) {
                        var16_18 = null;
                        if (dataInputStream != null) {
                            try {
                                dataInputStream.close();
                            }
                            catch (IOException iOException2) {
                                a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException2);
                            }
                        }
                        if (fileInputStream == null) continue;
                        try {
                            fileInputStream.close();
                        }
                        catch (IOException iOException2) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException2);
                        }
                    }
                }
                catch (Throwable throwable) {
                    var16_18 = null;
                    if (dataInputStream != null) {
                        try {
                            dataInputStream.close();
                        }
                        catch (IOException iOException2) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException2);
                        }
                    }
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        }
                        catch (IOException iOException2) {
                            a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException2);
                        }
                    }
                    throw throwable;
                }
            }
            catch (FileNotFoundException fileNotFoundException) {
                a.error((Object)fileNotFoundException.getMessage());
            }
            catch (IOException iOException) {
                a.error((Object)iOException.getMessage());
            }
        }
    }

    public String toString() {
        return "BinaryStorage working under " + this.cMY;
    }

    static /* synthetic */ StringBuilder a(apl_1 apl_12) {
        return apl_12.cNc;
    }

    static /* synthetic */ String b(apl_1 apl_12) {
        return apl_12.cMY;
    }

    static /* synthetic */ lb_0 c(apl_1 apl_12) {
        return apl_12.cNj;
    }
}

