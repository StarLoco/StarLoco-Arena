/*
 * Decompiled with CFR 0.152.
 */
import java.io.FilterReader;
import java.io.Reader;
import java.io.StringReader;

public class axP
extends FilterReader {
    private int dkn = -1;
    private boolean dko = false;

    public axP(Reader reader) {
        super(reader);
    }

    public int read() {
        int n2;
        if (this.dkn == -1) {
            n2 = this.in.read();
        } else {
            n2 = this.dkn;
            this.dkn = -1;
        }
        if (n2 != 92 || this.dko) {
            this.dko = false;
            return n2;
        }
        n2 = this.in.read();
        if (n2 != 117) {
            this.dkn = n2;
            this.dko = true;
            return 92;
        }
        do {
            if ((n2 = this.in.read()) != -1) continue;
            throw new abr_0("Incomplete escape sequence");
        } while (n2 == 117);
        char[] cArray = new char[4];
        cArray[0] = (char)n2;
        if (this.in.read(cArray, 1, 3) != 3) {
            throw new abr_0("Incomplete escape sequence");
        }
        try {
            return 0xFFFF & Integer.parseInt(new String(cArray), 16);
        }
        catch (NumberFormatException numberFormatException) {
            throw new abr_0("Invalid escape sequence \"\\u" + new String(cArray) + "\"");
        }
    }

    public int read(char[] cArray, int n2, int n3) {
        int n4;
        if (n3 == 0) {
            return 0;
        }
        int n5 = 0;
        while ((n4 = this.read()) != -1) {
            cArray[n2++] = (char)n4;
            if (++n5 < n3) continue;
        }
        return n5 == 0 ? -1 : n5;
    }

    public static void main(String[] stringArray) {
        int n2;
        axP axP2 = new axP(new StringReader(stringArray[0]));
        while ((n2 = ((Reader)axP2).read()) != -1) {
            System.out.print((char)n2);
        }
        System.out.println();
    }
}

