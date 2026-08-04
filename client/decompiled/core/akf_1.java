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
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/*
 * Renamed from akf
 */
public class akf_1
extends ace_0 {
    private static final lJ[] cCX = new lJ[0];
    private String cCY = null;
    private final lb_0 cCZ = new lb_0();
    private final Object cDa = new Object();
    private final String cDb;
    private final String cDc;
    private final mt_0 cDd;
    private static final mt_0 cDe = mt_0.b(mt_0.btS);
    private boolean cDf;

    public akf_1(String string, String string2, boolean bl2) {
        this.cDb = string;
        this.cDc = string2;
        this.cDd = bl2 ? mt_0.b(mt_0.btT) : mt_0.b(mt_0.btS);
        this.start();
    }

    public akf_1(String string, String string2) {
        this(string, string2, false);
    }

    public lJ a(int n2, lJ lJ2) {
        if (!this.azI()) {
            a.error((Object)"Tentative d'acces au (Simple)BinaryStorage alors qu'il n'est pas initialis\u00e9");
            return null;
        }
        lJ[] lJArray = this.a("id", n2, lJ2);
        if (lJArray != null && lJArray.length > 0) {
            return lJArray[0];
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public lJ[] a(String string, Object object, lJ lJ2) {
        if (!this.azI()) {
            a.error((Object)"Tentative d'acces au (Simple)BinaryStorage alors qu'il n'est pas initialis\u00e9");
            return cCX;
        }
        LinkedList<lJ> linkedList = new LinkedList<lJ>();
        Object object2 = this.cDa;
        synchronized (object2) {
            HashMap hashMap = (HashMap)this.cCZ.get(lJ2.cq());
            if (hashMap == null) {
                return cCX;
            }
            if (hashMap.get(string) == null) {
                return cCX;
            }
            try {
                File file = new File(this.cCY + this.cDb);
                if (!file.exists()) {
                    return cCX;
                }
                String string2 = object.toString();
                for (le_2 le_22 : (ArrayList)hashMap.get(string)) {
                    if (!le_22.XW().equals(string2)) continue;
                    FileInputStream fileInputStream = null;
                    FilterInputStream filterInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(file);
                        filterInputStream = this.cDd.a(fileInputStream);
                        FileChannel fileChannel = fileInputStream.getChannel();
                        fileChannel.position(le_22.getPosition());
                        azf azf2 = new azf();
                        azf2.read((DataInputStream)filterInputStream);
                        lJ lJ3 = lJ2.cs();
                        ByteBuffer byteBuffer = ByteBuffer.wrap(azf2.getData());
                        lJ3.a(byteBuffer, azf2.getId(), azf2.qx());
                        if (byteBuffer.remaining() != 0) {
                            a.warn((Object)("Objet restaur\u00e9 du simple binary storage : " + byteBuffer.remaining() + " bytes restants non lus [type:" + lJ2.cq() + " | id:" + azf2.getId() + "]"));
                        }
                        linkedList.add(lJ3);
                        for (tp_2 tp_22 : this.ckD) {
                            tp_22.c(this, lJ3);
                        }
                    }
                    finally {
                        if (filterInputStream != null) {
                            try {
                                filterInputStream.close();
                            }
                            catch (IOException iOException) {
                                a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                            }
                        }
                        if (fileInputStream == null) continue;
                        try {
                            fileInputStream.close();
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
        if (linkedList.size() > 0) {
            return linkedList.toArray(new lJ[linkedList.size()]);
        }
        return cCX;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public lJ[] a(lJ lJ2) {
        if (!this.azI()) {
            a.error((Object)"Tentative d'acces au (Simple)BinaryStorage alors qu'il n'est pas initialis\u00e9");
            return cCX;
        }
        LinkedList<lJ> linkedList = new LinkedList<lJ>();
        Object object = this.cDa;
        synchronized (object) {
            HashMap hashMap = (HashMap)this.cCZ.get(lJ2.cq());
            if (hashMap == null) {
                return cCX;
            }
            try {
                File file = new File(this.cCY + this.cDb);
                if (!file.exists()) {
                    return cCX;
                }
                for (le_2 le_22 : (ArrayList)hashMap.get("id")) {
                    azf azf2;
                    FileInputStream fileInputStream = null;
                    FilterInputStream filterInputStream = null;
                    try {
                        fileInputStream = new FileInputStream(file);
                        filterInputStream = this.cDd.a(fileInputStream);
                        fileInputStream.getChannel().position(le_22.getPosition());
                        azf2 = new azf();
                        azf2.read((DataInputStream)filterInputStream);
                    }
                    finally {
                        if (filterInputStream != null) {
                            try {
                                filterInputStream.close();
                            }
                            catch (IOException iOException) {
                                a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            }
                            catch (IOException iOException) {
                                a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                            }
                        }
                    }
                    lJ lJ3 = lJ2.cs();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(azf2.getData());
                    lJ3.a(byteBuffer, azf2.getId(), azf2.qx());
                    if (byteBuffer.remaining() != 0) {
                        a.warn((Object)("Objet restaur\u00e9 du simple binary storage : " + byteBuffer.remaining() + " bytes restants non lus [type:" + lJ2.cq() + " | id:" + azf2.getId() + "]"));
                    }
                    linkedList.add(lJ3);
                    for (tp_2 tp_22 : this.ckD) {
                        tp_22.c(this, lJ3);
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
        if (linkedList.size() > 0) {
            return linkedList.toArray(new lJ[linkedList.size()]);
        }
        return cCX;
    }

    public void iv(String string) {
        if (string != null) {
            String string2 = string;
            if (string2.charAt(string2.length() - 1) != '/') {
                string2 = string2 + "/";
            }
            this.cDf = false;
            this.cCY = string2;
        }
    }

    public boolean azI() {
        return this.cDf;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean art() {
        assert (this.cCY != null) : "Il faut initialiser m_baseWorkspace";
        Object object = this.cDa;
        synchronized (object) {
            try {
                File file = new File(this.cCY);
                if (file.exists() && !file.isDirectory()) {
                    a.error((Object)("Tentative de changement de workspace [" + this.cCY + "] vers un fichier [not directory] existant [Aborted & Shutdown]"));
                    return false;
                }
                if (!file.exists() && !file.mkdirs()) {
                    a.error((Object)("Impossible de creer l'arborescence de repertoire [" + this.cCY + "] lors d'un changement de workspace inexistant [Aborted & Shutdown]"));
                    return false;
                }
                this.cCZ.clear();
                File file2 = new File(this.cCY + this.cDc);
                if (!file2.exists()) {
                    file2.createNewFile();
                    a.info((Object)"Fichier d'index non trouv\u00e9 pour le chargement de la source binaire : Creation d'une nouvelle source");
                    this.azJ();
                    return true;
                }
                DataInputStream dataInputStream = null;
                try {
                    dataInputStream = cDe.a(new FileInputStream(file2));
                    try {
                        while (true) {
                            le_2 le_22 = new le_2();
                            le_22.read(dataInputStream);
                            this.a(le_22);
                        }
                    }
                    catch (EOFException eOFException) {
                        if (dataInputStream != null) {
                            try {
                                dataInputStream.close();
                            }
                            catch (IOException iOException) {
                                a.fatal((Object)"Impossible de fermer le descripteur ouvert sur un fichier !", (Throwable)iOException);
                            }
                        }
                    }
                }
                catch (Throwable throwable) {
                    if (dataInputStream != null) {
                        try {
                            dataInputStream.close();
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
            }
            catch (IOException iOException) {
                a.error((Object)iOException.getMessage(), (Throwable)iOException);
            }
            this.azJ();
            return true;
        }
    }

    private void azJ() {
        this.cDf = true;
        for (tp_2 tp_22 : this.ckD) {
            tp_22.a((ace_0)this, this.aru());
        }
    }

    private void a(le_2 le_22) {
        ArrayList<le_2> arrayList;
        HashMap<String, ArrayList<le_2>> hashMap = (HashMap<String, ArrayList<le_2>>)this.cCZ.get(le_22.getType());
        if (hashMap == null) {
            hashMap = new HashMap<String, ArrayList<le_2>>(5);
            this.cCZ.c(le_22.getType(), hashMap);
        }
        if ((arrayList = (ArrayList<le_2>)hashMap.get(le_22.getIndexName())) == null) {
            arrayList = new ArrayList<le_2>(300);
            hashMap.put(le_22.getIndexName(), arrayList);
        }
        arrayList.add(le_22);
    }

    protected void b(lJ lJ2) {
        a.error((Object)"Remove call on a ReadOnlyBinaryStorage");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void c(lJ lJ2) {
        Object object = this.cDa;
        synchronized (object) {
            byte[] byArray = lJ2.cr();
            if (byArray == null) {
                a.error((Object)("Tentative de sauvegarde d'un binary storable qui n'a aucun bloc de donn\u00e9es " + lJ2));
                return;
            }
            try {
                Field[] fieldArray;
                long l2;
                File file = new File(this.cCY + this.cDb);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fileOutputStream = null;
                FilterOutputStream filterOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(this.cCY + this.cDb, true);
                    filterOutputStream = this.cDd.a(fileOutputStream);
                    l2 = fileOutputStream.getChannel().size();
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
                this.a("id", lJ2.qw(), lJ2.cq(), l2);
                for (Field field : fieldArray = lJ2.getClass().getDeclaredFields()) {
                    Object object2;
                    if (!field.isAnnotationPresent(ays_0.class)) continue;
                    ays_0 ays_02 = field.getAnnotation(ays_0.class);
                    if (field.isAccessible()) {
                        object2 = field.get(lJ2);
                    } else {
                        field.setAccessible(true);
                        object2 = field.get(lJ2);
                        field.setAccessible(false);
                    }
                    this.a(ays_02.name(), object2, lJ2.cq(), l2);
                }
            }
            catch (IOException iOException) {
                a.error((Object)iOException.getMessage(), (Throwable)iOException);
            }
            catch (IllegalAccessException illegalAccessException) {
                a.error((Object)illegalAccessException.getMessage(), (Throwable)illegalAccessException);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(String string, Object object, int n2, long l2) {
        try {
            FilterOutputStream filterOutputStream = null;
            try {
                filterOutputStream = cDe.a(new FileOutputStream(this.cCY + this.cDc, true));
                le_2 le_22 = new le_2(n2, string, object.toString(), l2);
                le_22.write((DataOutputStream)filterOutputStream);
                this.a(le_22);
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
            }
        }
        catch (IOException iOException) {
            a.error((Object)iOException.getMessage());
        }
    }

    protected String aru() {
        return this.cCY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void arv() {
        Object object = this.cDa;
        synchronized (object) {
            System.out.println("cleanUpFiles m_baseWorkspace " + this.cCY);
            File file = new File(this.cCY + this.cDc);
            File file2 = new File(this.cCY + this.cDb);
            file.delete();
            file2.delete();
            this.cCZ.clear();
        }
    }
}

