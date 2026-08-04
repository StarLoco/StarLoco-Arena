/*
 * Decompiled with CFR 0.152.
 */
import java.text.CharacterIterator;

/*
 * Renamed from aCR
 */
class acr_0
implements CharacterIterator {
    CharSequence duX;
    int duY;
    int duZ;

    acr_0() {
    }

    acr_0(CharSequence charSequence) {
        this.initFromCharSequence(charSequence);
    }

    public void initFromCharSequence(CharSequence charSequence) {
        this.duX = charSequence;
        this.duY = this.duX.length();
        this.duZ = 0;
    }

    public char last() {
        this.duZ = Math.max(0, this.duY - 1);
        return this.current();
    }

    public char current() {
        if (this.duY == 0 || this.duZ >= this.duY) {
            return '\uffff';
        }
        return this.duX.charAt(this.duZ);
    }

    public char next() {
        ++this.duZ;
        return this.current();
    }

    public char previous() {
        this.duZ = Math.max(this.duZ - 1, 0);
        return this.current();
    }

    public char setIndex(int n2) {
        this.duZ = n2;
        return this.current();
    }

    public int getBeginIndex() {
        return 0;
    }

    public int getEndIndex() {
        return this.duY;
    }

    public int getIndex() {
        return this.duZ;
    }

    public Object clone() {
        acr_0 acr_02 = new acr_0(this.duX);
        acr_02.duZ = this.duZ;
        return acr_02;
    }

    public char first() {
        if (this.duY == 0) {
            return '\uffff';
        }
        this.duZ = 0;
        return this.current();
    }
}

