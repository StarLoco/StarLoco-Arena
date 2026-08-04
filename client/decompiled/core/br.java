/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import org.apache.log4j.Logger;

public class br {
    private static final Logger a = Logger.getLogger(br.class);
    private static final File fQ;
    private static File fR;

    public static boolean a(bb_2 bb_22) {
        boolean bl2 = false;
        if (bb_22 == bb_2.ep) {
            a.error((Object)("Impossible de lire les donn\u00e9es : SerializableData \u00e9gal \u00e0 " + bb_2.ep + "."));
        } else {
            byte by = -128;
            String string = bb_22.getDirectoryName();
            String string2 = bb_22.getFileName();
            byte[] byArray = bb_2.eu;
            if (string == bb_2.es || string2 == bb_2.et) {
                a.error((Object)("Impossible de lire les donn\u00e9es : Mauvais nom de fichier \"" + string + System.getProperty("file.separator") + string2 + "\"."));
            } else if (fR == fQ) {
                a.error((Object)("Impossible de lire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : R\u00e9pertoire de sauvegarde non trouv\u00e9."));
            } else {
                try {
                    if (!new File(fR.getPath() + System.getProperty("file.separator") + string).exists()) {
                        a.error((Object)("Impossible de lire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : R\u00e9pertoire de sauvegarde non trouv\u00e9."));
                    } else {
                        String string3 = fR.getPath() + System.getProperty("file.separator") + string + System.getProperty("file.separator") + string2;
                        File file = new File(string3);
                        if (!file.exists()) {
                            a.error((Object)("Impossible de lire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : Fichier non trouv\u00e9."));
                        } else {
                            long l2 = file.length();
                            if (Integer.MAX_VALUE < l2) {
                                a.error((Object)("Impossible de lire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : Taille du fichier sup\u00e9rieure \u00e0 " + Integer.MAX_VALUE + "."));
                            } else {
                                FileInputStream fileInputStream = new FileInputStream(string3);
                                by = (byte)fileInputStream.read();
                                int n2 = (int)l2 - 1;
                                byte[] byArray2 = new byte[n2];
                                int n3 = fileInputStream.read(byArray2);
                                if (n3 != n2) {
                                    a.error((Object)("Impossible de lire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : Nombre d'octets lus diff\u00e9rents de la taille attendue, " + n3 + " != " + n2 + "."));
                                } else {
                                    byArray = byArray2;
                                }
                                fileInputStream.close();
                            }
                        }
                    }
                }
                catch (Exception exception) {
                    a.error((Object)("Impossible de lire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : "), (Throwable)exception);
                }
            }
            if (bl2 = byArray != bb_2.eu) {
                bb_22.b(by);
                bb_22.b(byArray);
            }
        }
        return bl2;
    }

    public static boolean b(bb_2 bb_22) {
        boolean bl2 = false;
        if (bb_22 == bb_2.ep) {
            a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es : SerializableData \u00e9gal \u00e0 " + bb_2.ep + "."));
        } else {
            String string = bb_22.getDirectoryName();
            String string2 = bb_22.getFileName();
            byte[] byArray = bb_22.cd();
            if (string == bb_2.es || string2 == bb_2.et) {
                a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es : Mauvais nom de fichier \"" + string + System.getProperty("file.separator") + string2 + "\"."));
            } else if (byArray == bb_2.eu) {
                a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : Donn\u00e9es \u00e9gales \u00e0 " + Arrays.toString(bb_2.eu) + "."));
            } else if (fR == fQ) {
                a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : R\u00e9pertoire de sauvegarde non trouv\u00e9."));
            } else {
                try {
                    File file = new File(fR.getPath() + System.getProperty("file.separator") + string);
                    if (!file.exists() && !file.mkdir()) {
                        a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : R\u00e9pertoire de sauvegarde non cr\u00e9\u00e9."));
                    } else {
                        String string3 = fR.getPath() + System.getProperty("file.separator") + string + System.getProperty("file.separator") + string2;
                        File file2 = new File(string3);
                        if (file2.exists() && !file2.delete()) {
                            a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : Fichier non effac\u00e9."));
                        } else if (!file2.createNewFile()) {
                            a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : Fichier non cr\u00e9\u00e9."));
                        } else {
                            FileOutputStream fileOutputStream = new FileOutputStream(string3);
                            fileOutputStream.write(bb_22.cc());
                            fileOutputStream.write(byArray);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            bl2 = true;
                        }
                    }
                }
                catch (Exception exception) {
                    a.error((Object)("Impossible d'\u00e9crire les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : "), (Throwable)exception);
                }
            }
        }
        return bl2;
    }

    public static boolean c(bb_2 bb_22) {
        boolean bl2 = false;
        if (bb_22 == bb_2.ep) {
            a.error((Object)("Impossible de supprimer les donn\u00e9es : SerializableData \u00e9gal \u00e0 " + bb_2.ep + "."));
        } else {
            String string = bb_22.getDirectoryName();
            String string2 = bb_22.getFileName();
            if (string == bb_2.es || string2 == bb_2.et) {
                a.error((Object)("Impossible de supprimer les donn\u00e9es : Mauvais nom de fichier \"" + string + System.getProperty("file.separator") + string2 + "\"."));
            } else if (fR == fQ) {
                a.error((Object)("Impossible de supprimer les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : R\u00e9pertoire de sauvegarde non trouv\u00e9."));
            } else {
                try {
                    File file = new File(fR.getPath() + System.getProperty("file.separator") + string);
                    if (!file.exists()) {
                        a.error((Object)("Impossible de supprimer les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : R\u00e9pertoire de sauvegarde n'existe pas."));
                    } else {
                        String string3 = fR.getPath() + System.getProperty("file.separator") + string + System.getProperty("file.separator") + string2;
                        File file2 = new File(string3);
                        if (!file2.exists()) {
                            a.error((Object)("Impossible de supprimer les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : Fichier n'existe pas."));
                        } else {
                            bl2 = file2.delete();
                        }
                    }
                }
                catch (Exception exception) {
                    a.error((Object)("Impossible de supprimer les donn\u00e9es du fichier de nom \"" + string + System.getProperty("file.separator") + string2 + "\" : "), (Throwable)exception);
                }
            }
        }
        return bl2;
    }

    static {
        fR = fQ = null;
        try {
            File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + mu_1.rM().getString("savesPath"));
            if (file.exists() || file.mkdir()) {
                fR = file;
            } else {
                a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des sauvegardes : R\u00e9pertoire non cr\u00e9\u00e9.");
            }
        }
        catch (Exception exception) {
            a.error((Object)"Impossible d'initialiser le r\u00e9pertoire des sauvegardes : ", (Throwable)exception);
        }
    }
}

