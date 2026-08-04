/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Renamed from avo
 */
public class avo_0 {
    private static final boolean DEBUG = false;
    private static final int ddW = 128;
    private static final byte ddX = -1;
    private static final byte ddY = -2;
    private static final int ddZ = 254;
    private nw_2 aEo;
    private short maxStack;
    private short maxLocals;
    private byte[] dea;
    private va_2 deb;
    private aNc dec;
    private aNc ded;
    private List dee;
    private List def = new ArrayList();
    private List deg = new ArrayList();
    private short deh = 0;
    private final List dei = new ArrayList();
    private static final Map dej = avo_0.aIu();

    public avo_0(nw_2 nw_22) {
        this.aEo = nw_22;
        this.maxStack = 0;
        this.maxLocals = 0;
        this.dea = new byte[128];
        this.deb = new va_2(this);
        this.ded = this.dec = new aNc(this);
        this.dee = new ArrayList();
        this.deb.offset = 0;
        this.dec.offset = 0;
        this.deb.bSF = this.dec;
        this.dec.bSE = this.deb;
    }

    public nw_2 asW() {
        return this.aEo;
    }

    public short cb(short s) {
        return this.a(s, (String)null, (asn)null).jl();
    }

    public xl_2 a(short s, String string, asn asn2) {
        List list = null;
        if (this.deg.size() == 0) {
            throw new Error("saveLocalVariables must be called first");
        }
        list = (List)this.deg.get(this.deg.size() - 1);
        xl_2 xl_22 = new xl_2(string, this.deh, asn2);
        if (xl_22.getName() != null) {
            xl_22.a(this.aIv());
        }
        this.deh = (short)(this.deh + s);
        list.add(xl_22);
        this.def.add(xl_22);
        if (this.deh > this.maxLocals) {
            this.maxLocals = this.deh;
        }
        return xl_22;
    }

    public List aIr() {
        ArrayList arrayList = new ArrayList();
        this.deg.add(arrayList);
        return arrayList;
    }

    public void aIs() {
        Iterator iterator = ((List)this.deg.remove(this.deg.size() - 1)).iterator();
        while (iterator.hasNext()) {
            xl_2 xl_22 = (xl_2)iterator.next();
            if (xl_22.getName() == null) continue;
            xl_22.b(this.aIv());
        }
    }

    protected void a(DataOutputStream dataOutputStream, short s, short s2) {
        to_2[] to_2Array;
        Iterator<to_2> iterator;
        dataOutputStream.writeShort(this.maxStack);
        dataOutputStream.writeShort(this.maxLocals);
        dataOutputStream.writeInt(this.dec.offset);
        dataOutputStream.write(this.dea, 0, this.dec.offset);
        dataOutputStream.writeShort(this.dee.size());
        for (int j = 0; j < this.dee.size(); ++j) {
            iterator = (xd_0)this.dee.get(j);
            dataOutputStream.writeShort(((xd_0)((Object)iterator)).bWN.offset);
            dataOutputStream.writeShort(((xd_0)((Object)iterator)).bWO.offset);
            dataOutputStream.writeShort(((xd_0)((Object)iterator)).bWP.offset);
            dataOutputStream.writeShort(((xd_0)((Object)iterator)).catchType);
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        if (s != 0) {
            iterator = new ArrayList();
            to_2Array = this.deb;
            while (to_2Array != null) {
                if (to_2Array instanceof aCr) {
                    iterator.add(new to_2(to_2Array.offset, aCr.a((aCr)to_2Array)));
                }
                to_2Array = to_2Array.bSF;
            }
            to_2Array = iterator.toArray((to_2[])new to_2[iterator.size()]);
            arrayList.add(new asa_0(s, to_2Array));
        }
        if (s2 != 0 && (iterator = this.a(dataOutputStream, s2)) != null) {
            arrayList.add(iterator);
        }
        dataOutputStream.writeShort(arrayList.size());
        iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            to_2Array = (ov_2)iterator.next();
            to_2Array.a(dataOutputStream);
        }
    }

    protected ov_2 a(DataOutputStream dataOutputStream, short s) {
        zs_0[] zs_0Array;
        nw_2 nw_22 = this.asW();
        Iterator iterator = this.aIz().iterator();
        ArrayList<zs_0> arrayList = new ArrayList<zs_0>();
        while (iterator.hasNext()) {
            zs_0Array = (zs_0[])iterator.next();
            if (zs_0Array.getName() == null) continue;
            String string = zs_0Array.tF().getDescriptor();
            short s2 = nw_22.ft(string);
            short s3 = nw_22.ft(zs_0Array.getName());
            zs_0 zs_02 = new zs_0((short)zs_0Array.als().offset, (short)(zs_0Array.alt().offset - zs_0Array.als().offset), s3, s2, zs_0Array.jl());
            arrayList.add(zs_02);
        }
        if (arrayList.size() > 0) {
            zs_0Array = arrayList.toArray(new zs_0[arrayList.size()]);
            return new CI(s, zs_0Array);
        }
        return null;
    }

    public void jQ(String string) {
        int n2;
        short[] sArray = new short[this.dec.offset];
        Arrays.fill(sArray, (short)-1);
        this.a(string, this.dea, this.dec.offset, 0, (short)0, sArray);
        int n3 = 0;
        while (n3 != this.dee.size()) {
            for (n2 = 0; n2 < this.dee.size(); ++n2) {
                xd_0 xd_02 = (xd_0)this.dee.get(n2);
                if (sArray[xd_02.bWN.offset] == -1) continue;
                this.a(string, this.dea, this.dec.offset, xd_02.bWP.offset, (short)(sArray[xd_02.bWN.offset] + 1), sArray);
                ++n3;
            }
        }
        this.maxStack = 0;
        for (n2 = 0; n2 < sArray.length; ++n2) {
            short s = sArray[n2];
            if (s == -1) {
                throw new aHY(string + ": Unexamined code at offset " + n2);
            }
            if (s <= this.maxStack) continue;
            this.maxStack = s;
        }
    }

    private void a(String string, byte[] byArray, int n2, int n3, short s, short[] sArray) {
        while (true) {
            short s2;
            if (n3 < 0 || n3 >= n2) {
                throw new aHY(string + ": Offset out of range");
            }
            short s3 = sArray[n3];
            if (s3 == s) {
                return;
            }
            if (s3 == -2) {
                throw new aHY(string + ": Invalid offset");
            }
            if (s3 != -1) {
                throw new aHY(string + ": Operand stack inconsistent at offset " + n3 + ": Previous size " + s3 + ", now " + s);
            }
            sArray[n3] = s;
            byte by = byArray[n3];
            int n4 = n3 + 1;
            if (by == -60) {
                by = byArray[n4++];
                s2 = fs_0.baj[0xFF & by];
            } else {
                s2 = fs_0.bai[0xFF & by];
            }
            if (s2 == -1) {
                throw new aHY(string + ": Invalid opcode " + (0xFF & by) + " at offset " + n3);
            }
            switch (s2 & 0x1F) {
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: {
                    s = (short)(s + ((s2 & 0x1F) - 4));
                    break;
                }
                case 7: {
                    s = 0;
                    break;
                }
                case 9: {
                    s = (short)(s - 1);
                }
                case 10: {
                    s = (short)(s + this.cc((short)this.a(0, n4, byArray)));
                    break;
                }
                case 11: {
                    s = (short)(s - 1);
                }
                case 12: {
                    s = (short)(s - this.cc((short)this.a(0, n4, byArray)));
                    break;
                }
                case 13: 
                case 14: 
                case 16: {
                    s = (short)(s - 1);
                }
                case 15: {
                    s = (short)(s - this.cd((short)this.a(0, n4, byArray)));
                    break;
                }
                case 18: {
                    s = (short)(s - (byArray[n4 + 2] - 1));
                    break;
                }
                default: {
                    throw new aHY(string + ": Invalid stack delta");
                }
            }
            if (s < 0) {
                String string2 = this.aEo.aaB() + '.' + string + ": Operand stack underrun at offset " + n3;
                throw new aHY(string2);
            }
            if (s > 254) {
                String string3 = this.aEo.aaB() + '.' + string + ": Operand stack overflow at offset " + n3;
                throw new aHY(string3);
            }
            switch (s2 & 0x1E0) {
                case 0: {
                    break;
                }
                case 32: 
                case 64: 
                case 128: 
                case 192: {
                    ++n4;
                    break;
                }
                case 96: 
                case 160: 
                case 224: {
                    n4 += 2;
                    break;
                }
                case 256: {
                    this.a(string, byArray, n2, this.a(n3, n4, byArray), s, sArray);
                    n4 += 2;
                    break;
                }
                case 384: {
                    int n5 = this.a(n3, n4, byArray);
                    n4 += 2;
                    if (sArray[n5] != -1) break;
                    this.a(string, byArray, n2, n5, (short)(s + 1), sArray);
                    break;
                }
                case 288: {
                    this.a(string, byArray, n2, this.b(n3, n4, byArray), s, sArray);
                    n4 += 4;
                    break;
                }
                case 320: {
                    int n6;
                    while ((n4 & 3) != 0) {
                        ++n4;
                    }
                    this.a(string, byArray, n2, this.b(n3, n4, byArray), s, sArray);
                    int n7 = this.b(0, n4 += 4, byArray);
                    n4 += 4;
                    for (n6 = 0; n6 < n7; ++n6) {
                        this.a(string, byArray, n2, this.b(n3, n4 += 4, byArray), s, sArray);
                        n4 += 4;
                    }
                    break;
                }
                case 352: {
                    while ((n4 & 3) != 0) {
                        ++n4;
                    }
                    this.a(string, byArray, n2, this.b(n3, n4, byArray), s, sArray);
                    int n6 = this.b(n3, n4 += 4, byArray);
                    int n8 = this.b(n3, n4 += 4, byArray);
                    n4 += 4;
                    for (int j = n6; j <= n8; ++j) {
                        this.a(string, byArray, n2, this.b(n3, n4, byArray), s, sArray);
                        n4 += 4;
                    }
                    break;
                }
                default: {
                    throw new aHY(string + ": Invalid OP1");
                }
            }
            switch (s2 & 0x600) {
                case 0: {
                    break;
                }
                case 512: {
                    ++n4;
                    break;
                }
                case 1024: {
                    n4 += 2;
                    break;
                }
                default: {
                    throw new aHY(string + ": Invalid OP2");
                }
            }
            switch (s2 & 0x800) {
                case 0: {
                    break;
                }
                case 2048: {
                    ++n4;
                    break;
                }
                default: {
                    throw new aHY(string + ": Invalid OP3");
                }
            }
            Arrays.fill(sArray, n3 + 1, n4, (short)-2);
            if ((s2 & Short.MIN_VALUE) != 0) {
                return;
            }
            n3 = n4;
        }
    }

    private int a(int n2, int n3, byte[] byArray) {
        int n4 = n2 + ((byArray[n3] << 8) + (byArray[n3 + 1] & 0xFF));
        return n4;
    }

    private int b(int n2, int n3, byte[] byArray) {
        int n4 = n2 + ((byArray[n3] << 24) + ((0xFF & byArray[n3 + 1]) << 16) + ((0xFF & byArray[n3 + 2]) << 8) + (0xFF & byArray[n3 + 3]));
        return n4;
    }

    public void aIt() {
        do {
            this.aAv();
        } while (!this.zi());
    }

    private void aAv() {
        va_2 va_22 = this.deb;
        while (va_22 != this.dec) {
            if (va_22 instanceof alg) {
                ((alg)((Object)va_22)).aAv();
            }
            va_22 = va_22.bSF;
        }
    }

    private boolean zi() {
        boolean bl2 = true;
        for (int j = 0; j < this.dei.size(); ++j) {
            boolean bl3 = ((tv_0)this.dei.get(j)).zi();
            bl2 = bl2 && bl3;
        }
        return bl2;
    }

    private int cc(short s) {
        cx_2 cx_22 = (cx_2)this.aEo.aN(s);
        ow_0 ow_02 = (ow_0)this.aEo.aN(cx_22.eP());
        aby_1 aby_12 = (aby_1)this.aEo.aN(ow_02.abx());
        return sA.bY(aby_12.getString());
    }

    /*
     * Unable to fully structure code
     */
    private int cd(short var1_1) {
        var2_2 = this.aEo.aN(var1_1);
        var3_3 = (ow_0)this.aEo.aN(var2_2 instanceof dz_1 != false ? ((dz_1)var2_2).eP() : ((ng_1)var2_2).eP());
        var4_4 = (aby_1)this.aEo.aN(var3_3.abx());
        var5_5 = var4_4.getString();
        if (var5_5.charAt(0) != '(') {
            throw new aHY("Method descriptor does not start with \"(\"");
        }
        var6_6 = 1;
        var7_7 = 0;
        block7: while (true) {
            switch (var5_5.charAt(var6_6++)) {
                case ')': {
                    return var7_7 - sA.bY(var5_5.substring(var6_6));
                }
                case 'B': 
                case 'C': 
                case 'F': 
                case 'I': 
                case 'S': 
                case 'Z': {
                    ++var7_7;
                    continue block7;
                }
                case 'D': 
                case 'J': {
                    var7_7 += 2;
                    continue block7;
                }
                case '[': {
                    ++var7_7;
                    while (var5_5.charAt(var6_6) == '[') {
                        ++var6_6;
                    }
                    if ("BCFISZDJ".indexOf(var5_5.charAt(var6_6)) != -1) {
                        ++var6_6;
                        continue block7;
                    }
                    if (var5_5.charAt(var6_6) != 'L') {
                        throw new aHY("Invalid char after \"[\"");
                    }
                    while (true) {
                        v0 = ++var6_6;
                        ++var6_6;
                        if (var5_5.charAt(v0) == ';') continue block7;
                    }
                }
                case 'L': {
                    ++var7_7;
                    while (true) {
                        if (var5_5.charAt(var6_6++) != ';') ** break;
                        continue block7;
                    }
                }
            }
            break;
        }
        throw new aHY("Invalid method descriptor");
    }

    public void a(short s, byte[] byArray) {
        if (byArray.length == 0) {
            return;
        }
        int n2 = this.ded.offset;
        this.d(s, byArray.length);
        System.arraycopy(byArray, 0, this.dea, n2, byArray.length);
    }

    public void d(short s, byte by) {
        int n2 = this.ded.offset;
        this.d(s, 1);
        this.dea[n2] = by;
    }

    public void b(short s, byte by, byte by2) {
        int n2 = this.ded.offset;
        this.d(s, 2);
        this.dea[n2++] = by;
        this.dea[n2] = by2;
    }

    public void a(short s, byte by, byte by2, byte by3) {
        int n2 = this.ded.offset;
        this.d(s, 3);
        this.dea[n2++] = by;
        this.dea[n2++] = by2;
        this.dea[n2] = by3;
    }

    public void a(short s, byte by, byte by2, byte by3, byte by4) {
        int n2 = this.ded.offset;
        this.d(s, 4);
        this.dea[n2++] = by;
        this.dea[n2++] = by2;
        this.dea[n2++] = by3;
        this.dea[n2] = by4;
    }

    public void d(short s, int n2) {
        Object object;
        block9: {
            if (n2 == 0) {
                return;
            }
            if (s != -1) {
                va_2 va_22 = this.ded.bSE;
                while (va_22 != this.deb) {
                    if (va_22 instanceof aCr) {
                        if (aCr.a((aCr)va_22) != s) break;
                        break block9;
                    }
                    va_22 = va_22.bSE;
                }
                object = new aCr(this, this.ded.offset, s);
                object.bSE = this.ded.bSE;
                object.bSF = this.ded;
                this.ded.bSE.bSF = object;
                this.ded.bSE = object;
            }
        }
        int n3 = this.ded.offset;
        if (this.dec.offset + n2 <= this.dea.length) {
            if (n3 != this.dec.offset) {
                System.arraycopy(this.dea, n3, this.dea, n3 + n2, this.dec.offset - n3);
            }
        } else {
            object = this.dea;
            int n4 = Math.max(Math.min(((byte[])object).length * 2, 65535), ((byte[])object).length + n2);
            if (n4 > 65535) {
                throw new aHY("Code attribute in class \"" + this.aEo.aaB() + "\" grows beyond 64 KB");
            }
            this.dea = new byte[n4];
            System.arraycopy(object, 0, this.dea, 0, n3);
            System.arraycopy(object, n3, this.dea, n3 + n2, this.dec.offset - n3);
        }
        Arrays.fill(this.dea, n3, n3 + n2, (byte)0);
        object = this.ded;
        while (object != null) {
            object.offset += n2;
            object = object.bSF;
        }
    }

    public void e(short s, int n2) {
        this.b(s, (byte)(n2 >> 8), (byte)n2);
    }

    public void a(short s, int n2, va_2 va_22) {
        this.dei.add(new Fh(this, n2, va_22));
        this.a(s, (byte)n2, (byte)-1, (byte)-1);
    }

    private static byte aY(byte by) {
        return (Byte)dej.get(new Byte(by));
    }

    private static Map aIu() {
        HashMap<Byte, Byte> hashMap = new HashMap<Byte, Byte>();
        hashMap.put(new Byte(-91), new Byte(-90));
        hashMap.put(new Byte(-90), new Byte(-91));
        hashMap.put(new Byte(-97), new Byte(-96));
        hashMap.put(new Byte(-96), new Byte(-97));
        hashMap.put(new Byte(-94), new Byte(-95));
        hashMap.put(new Byte(-95), new Byte(-94));
        hashMap.put(new Byte(-93), new Byte(-92));
        hashMap.put(new Byte(-92), new Byte(-93));
        hashMap.put(new Byte(-103), new Byte(-102));
        hashMap.put(new Byte(-102), new Byte(-103));
        hashMap.put(new Byte(-100), new Byte(-101));
        hashMap.put(new Byte(-101), new Byte(-100));
        hashMap.put(new Byte(-99), new Byte(-98));
        hashMap.put(new Byte(-98), new Byte(-99));
        hashMap.put(new Byte(-58), new Byte(-57));
        hashMap.put(new Byte(-57), new Byte(-58));
        return Collections.unmodifiableMap(hashMap);
    }

    public void a(short s, va_2 va_22, va_2 va_23) {
        this.dei.add(new sw_2(this, this.aIv(), va_22, va_23));
        this.a(s, (byte)-1, (byte)-1, (byte)-1, (byte)-1);
    }

    public va_2 aIv() {
        va_2 va_22 = new va_2(this);
        va_22.set();
        return va_22;
    }

    public aNc aIw() {
        aNc aNc2 = new aNc(this);
        aNc2.set();
        return aNc2;
    }

    public aNc aIx() {
        return this.ded;
    }

    public void a(aNc aNc2) {
        if (aNc.b(aNc2) != null) {
            throw new aHY("An Inserter can only be pushed once at a time");
        }
        aNc.a(aNc2, this.ded);
        this.ded = aNc2;
    }

    public void aIy() {
        aNc aNc2 = aNc.b(this.ded);
        if (aNc2 == null) {
            throw new aHY("Code inserter stack underflow");
        }
        aNc.a(this.ded, null);
        this.ded = aNc2;
    }

    public void a(va_2 va_22, va_2 va_23, va_2 va_24, String string) {
        this.dee.add(new xd_0(va_22, va_23, va_24, string == null ? (short)0 : this.aEo.fs(string)));
    }

    public List aIz() {
        return this.def;
    }

    static byte aZ(byte by) {
        return avo_0.aY(by);
    }

    static byte[] b(avo_0 avo_02) {
        return avo_02.dea;
    }

    static aNc c(avo_0 avo_02) {
        return avo_02.ded;
    }

    static nw_2 d(avo_0 avo_02) {
        return avo_02.aEo;
    }
}

