/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Renamed from Nw
 */
public class nw_2 {
    private static final int bzA = -889275714;
    public static final short bzB = 45;
    public static final short bzC = 3;
    public static final short bzD = 46;
    public static final short bzE = 0;
    public static final short bzF = 47;
    public static final short bzG = 0;
    public static final short bzH = 48;
    public static final short bzI = 0;
    public static final short bzJ = 49;
    public static final short bzK = 0;
    private short bzL;
    private short bzM;
    public List bzN;
    public short aEp;
    public short thisClass;
    public short bzO;
    public short[] bzP;
    public List bzQ;
    public List bzR;
    private List attributes;
    private Map bzS;

    public nw_2(short s, String string, String string2, String[] stringArray) {
        this.bzL = (short)45;
        this.bzM = (short)3;
        this.bzN = new ArrayList();
        this.bzN.add(null);
        this.bzS = new HashMap();
        this.aEp = s;
        this.thisClass = this.fs(string);
        this.bzO = this.fs(string2);
        this.bzP = new short[stringArray.length];
        for (int j = 0; j < stringArray.length; ++j) {
            this.bzP[j] = this.fs(stringArray[j]);
        }
        this.bzQ = new ArrayList();
        this.bzR = new ArrayList();
        this.attributes = new ArrayList();
    }

    public void fr(String string) {
        this.attributes.add(new vn_0(this.ft("SourceFile"), this.ft(string)));
    }

    public void aaz() {
        this.attributes.add(new ts_0(this.ft("Deprecated")));
    }

    public aig_2 aaA() {
        Short s = (Short)this.bzS.get(new aby_1("InnerClasses"));
        if (s == null) {
            return null;
        }
        Iterator iterator = this.attributes.iterator();
        while (iterator.hasNext()) {
            ov_2 ov_22 = (ov_2)iterator.next();
            if (ov_2.a(ov_22) != s || !(ov_22 instanceof aig_2)) continue;
            return (aig_2)ov_22;
        }
        return null;
    }

    public void a(aqw_0 aqw_02) {
        aig_2 aig_22 = this.aaA();
        if (aig_22 == null) {
            aig_22 = new aig_2(this.ft("InnerClasses"));
            this.attributes.add(aig_22);
        }
        aig_22.ayu().add(aqw_02);
    }

    public nw_2(InputStream inputStream) {
        DataInputStream dataInputStream = inputStream instanceof DataInputStream ? (DataInputStream)inputStream : new DataInputStream(inputStream);
        int n2 = dataInputStream.readInt();
        if (n2 != -889275714) {
            throw new ClassFormatError("Invalid magic number");
        }
        this.bzM = dataInputStream.readShort();
        this.bzL = dataInputStream.readShort();
        if (!nw_2.q(this.bzL, this.bzM)) {
            throw new ClassFormatError("Unrecognized class file format version " + this.bzL + "/" + this.bzM);
        }
        this.bzN = new ArrayList();
        this.bzS = new HashMap();
        this.c(dataInputStream);
        this.aEp = dataInputStream.readShort();
        this.thisClass = dataInputStream.readShort();
        this.bzO = dataInputStream.readShort();
        this.bzP = nw_2.b(dataInputStream);
        this.bzQ = Collections.unmodifiableList(this.d(dataInputStream));
        this.bzR = Collections.unmodifiableList(this.e(dataInputStream));
        this.attributes = Collections.unmodifiableList(this.f(dataInputStream));
    }

    public String aaB() {
        return this.aO(this.thisClass).replace('/', '.');
    }

    public void p(short s, short s2) {
        this.bzL = s;
        this.bzM = s2;
    }

    public short aaC() {
        return this.bzL;
    }

    public short aaD() {
        return this.bzM;
    }

    public static boolean q(short s, short s2) {
        return s == 45 && s2 == 3 || s == 46 && s2 == 0 || s == 47 && s2 == 0 || s == 48 && s2 == 0 || s == 49 && s2 == 0;
    }

    public short fs(String string) {
        String string2;
        if (sA.bV(string)) {
            string2 = sA.cd(string);
        } else if (sA.bW(string)) {
            string2 = string;
        } else {
            throw new aHY("\"" + sA.toString(string) + "\" is neither a class nor an array");
        }
        return this.a(new acp_2(this.ft(string2)));
    }

    public short e(String string, String string2, String string3) {
        return this.a(new cx_2(this.fs(string), this.y(string2, string3)));
    }

    public short f(String string, String string2, String string3) {
        return this.a(new ng_1(this.fs(string), this.y(string2, string3)));
    }

    public short g(String string, String string2, String string3) {
        return this.a(new dz_1(this.fs(string), this.y(string2, string3)));
    }

    public short dk(String string) {
        return this.a(new aom_0(this.ft(string)));
    }

    public short eB(int n2) {
        return this.a(new aok(n2));
    }

    public short ac(float f) {
        return this.a(new ajg_1(f));
    }

    public short cf(long l2) {
        return this.a(new ahz_2(l2));
    }

    public short o(double d) {
        return this.a(new si(d));
    }

    private short y(String string, String string2) {
        return this.a(new ow_0(this.ft(string), this.ft(string2)));
    }

    public short ft(String string) {
        return this.a(new aby_1(string));
    }

    private short aa(Object object) {
        if (object instanceof String) {
            return this.dk((String)object);
        }
        if (object instanceof Byte || object instanceof Short || object instanceof Integer) {
            return this.eB(((Number)object).intValue());
        }
        if (object instanceof Boolean) {
            return this.eB((Boolean)object != false ? 1 : 0);
        }
        if (object instanceof Character) {
            return this.eB(((Character)object).charValue());
        }
        if (object instanceof Float) {
            return this.ac(((Float)object).floatValue());
        }
        if (object instanceof Long) {
            return this.cf((Long)object);
        }
        if (object instanceof Double) {
            return this.o((Double)object);
        }
        throw new aHY("Unexpected constant value type \"" + object.getClass().getName() + "\"");
    }

    private short a(anv anv2) {
        Short s = (Short)this.bzS.get(anv2);
        if (s != null) {
            return s;
        }
        int n2 = this.bzN.size();
        if (n2 > 65535) {
            throw new aHY("Constant pool has grown past JVM limit of 0xFFFF");
        }
        this.bzN.add(anv2);
        if (anv2.isWide()) {
            this.bzN.add(null);
        }
        this.bzS.put(anv2, new Short((short)n2));
        return (short)n2;
    }

    public axo_0 a(short s, String string, String string2, Object object) {
        ArrayList<oY> arrayList = new ArrayList<oY>();
        if (object != null) {
            arrayList.add(new oY(this.ft("ConstantValue"), this.aa(object)));
        }
        axo_0 axo_02 = new axo_0(s, this.ft(string), this.ft(string2), arrayList);
        this.bzQ.add(axo_02);
        return axo_02;
    }

    public adz a(short s, String string, String string2) {
        adz adz2 = new adz(this, s, this.ft(string), this.ft(string2), new ArrayList());
        this.bzR.add(adz2);
        return adz2;
    }

    public anv aN(short s) {
        return (anv)this.bzN.get(0xFFFF & s);
    }

    public String aO(short s) {
        acp_2 acp_22 = (acp_2)this.aN(s);
        aby_1 aby_12 = (aby_1)this.aN(acp_2.a(acp_22));
        return aby_1.a(aby_12);
    }

    public String aP(short s) {
        aby_1 aby_12 = (aby_1)this.aN(s);
        return aby_1.a(aby_12);
    }

    private static byte[] a(DataInputStream dataInputStream) {
        byte[] byArray = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(byArray);
        return byArray;
    }

    private static short[] b(DataInputStream dataInputStream) {
        int n2 = dataInputStream.readShort();
        short[] sArray = new short[n2];
        for (int j = 0; j < n2; ++j) {
            sArray[j] = dataInputStream.readShort();
        }
        return sArray;
    }

    private void c(DataInputStream dataInputStream) {
        this.bzN.clear();
        this.bzS.clear();
        short s = dataInputStream.readShort();
        this.bzN.add(null);
        for (short s2 = 1; s2 < s; s2 = (short)(s2 + 1)) {
            anv anv2 = anv.l(dataInputStream);
            this.bzN.add(anv2);
            this.bzS.put(anv2, new Short(s2));
            if (!anv2.isWide()) continue;
            this.bzN.add(null);
            s2 = (short)(s2 + 1);
        }
    }

    private List d(DataInputStream dataInputStream) {
        int n2 = dataInputStream.readShort();
        ArrayList<axo_0> arrayList = new ArrayList<axo_0>(n2);
        for (int j = 0; j < n2; ++j) {
            arrayList.add(new axo_0(dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort(), this.f(dataInputStream)));
        }
        return arrayList;
    }

    private List e(DataInputStream dataInputStream) {
        int n2 = dataInputStream.readShort();
        ArrayList<adz> arrayList = new ArrayList<adz>(n2);
        for (int j = 0; j < n2; ++j) {
            arrayList.add(this.g(dataInputStream));
        }
        return arrayList;
    }

    private List f(DataInputStream dataInputStream) {
        int n2 = dataInputStream.readShort();
        ArrayList<ov_2> arrayList = new ArrayList<ov_2>(n2);
        for (int j = 0; j < n2; ++j) {
            arrayList.add(this.h(dataInputStream));
        }
        return arrayList;
    }

    public void b(OutputStream outputStream) {
        DataOutputStream dataOutputStream = outputStream instanceof DataOutputStream ? (DataOutputStream)outputStream : new DataOutputStream(outputStream);
        dataOutputStream.writeInt(-889275714);
        dataOutputStream.writeShort(this.bzM);
        dataOutputStream.writeShort(this.bzL);
        nw_2.a(dataOutputStream, this.bzN);
        dataOutputStream.writeShort(this.aEp);
        dataOutputStream.writeShort(this.thisClass);
        dataOutputStream.writeShort(this.bzO);
        nw_2.a(dataOutputStream, this.bzP);
        nw_2.b(dataOutputStream, this.bzQ);
        nw_2.c(dataOutputStream, this.bzR);
        nw_2.d(dataOutputStream, this.attributes);
    }

    private static void a(DataOutputStream dataOutputStream, List list) {
        dataOutputStream.writeShort(list.size());
        for (int j = 1; j < list.size(); ++j) {
            anv anv2 = (anv)list.get(j);
            if (anv2 == null) continue;
            anv2.a(dataOutputStream);
        }
    }

    private static void a(DataOutputStream dataOutputStream, short[] sArray) {
        dataOutputStream.writeShort(sArray.length);
        for (int j = 0; j < sArray.length; ++j) {
            dataOutputStream.writeShort(sArray[j]);
        }
    }

    private static void b(DataOutputStream dataOutputStream, List list) {
        dataOutputStream.writeShort(list.size());
        for (int j = 0; j < list.size(); ++j) {
            ((axo_0)list.get(j)).a(dataOutputStream);
        }
    }

    private static void c(DataOutputStream dataOutputStream, List list) {
        dataOutputStream.writeShort(list.size());
        for (int j = 0; j < list.size(); ++j) {
            ((adz)list.get(j)).a(dataOutputStream);
        }
    }

    private static void d(DataOutputStream dataOutputStream, List list) {
        dataOutputStream.writeShort(list.size());
        for (int j = 0; j < list.size(); ++j) {
            ((ov_2)list.get(j)).a(dataOutputStream);
        }
    }

    public static String fu(String string) {
        int n2 = string.lastIndexOf(46) + 1;
        if ((n2 = string.indexOf(36, n2)) != -1) {
            string = string.substring(0, n2);
        }
        return string.replace('.', '/') + ".java";
    }

    public static String fv(String string) {
        return string.replace('.', '/') + ".class";
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            this.b(byteArrayOutputStream);
        }
        catch (IOException iOException) {
            throw new aHY(iOException.toString());
        }
        return byteArrayOutputStream.toByteArray();
    }

    private adz g(DataInputStream dataInputStream) {
        return new adz(this, dataInputStream.readShort(), dataInputStream.readShort(), dataInputStream.readShort(), this.f(dataInputStream));
    }

    private ov_2 h(DataInputStream dataInputStream) {
        ov_2 ov_22;
        short s = dataInputStream.readShort();
        int n2 = dataInputStream.readInt();
        byte[] byArray = new byte[n2];
        dataInputStream.readFully(byArray);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
        DataInputStream dataInputStream2 = new DataInputStream(byteArrayInputStream);
        String string = this.aP(s);
        if ("ConstantValue".equals(string)) {
            ov_22 = oY.c(s, dataInputStream2);
        } else if ("Code".equals(string)) {
            ov_22 = akf_0.a(s, this, dataInputStream2);
        } else if ("Exceptions".equals(string)) {
            ov_22 = im_1.b(s, dataInputStream2);
        } else if ("InnerClasses".equals(string)) {
            ov_22 = aig_2.h(s, dataInputStream2);
        } else if ("Synthetic".equals(string)) {
            ov_22 = abe.g(s, dataInputStream2);
        } else if ("SourceFile".equals(string)) {
            ov_22 = vn_0.f(s, dataInputStream2);
        } else if ("LineNumberTable".equals(string)) {
            ov_22 = asa_0.i(s, dataInputStream2);
        } else if ("LocalVariableTable".equals(string)) {
            ov_22 = CI.e(s, dataInputStream2);
        } else if ("Deprecated".equals(string)) {
            ov_22 = ts_0.d(s, dataInputStream2);
        } else {
            return new km_2(this, s, byArray);
        }
        if (byteArrayInputStream.available() > 0) {
            throw new ClassFormatError(byArray.length - byteArrayInputStream.available() + " bytes of trailing garbage in body of attribute \"" + string + "\"");
        }
        return ov_22;
    }

    static void e(DataOutputStream dataOutputStream, List list) {
        nw_2.d(dataOutputStream, list);
    }

    static short[] i(DataInputStream dataInputStream) {
        return nw_2.b(dataInputStream);
    }

    static void b(DataOutputStream dataOutputStream, short[] sArray) {
        nw_2.a(dataOutputStream, sArray);
    }

    static byte[] j(DataInputStream dataInputStream) {
        return nw_2.a(dataInputStream);
    }

    static ov_2 a(nw_2 nw_22, DataInputStream dataInputStream) {
        return nw_22.h(dataInputStream);
    }
}

