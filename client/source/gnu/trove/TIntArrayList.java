/*     */ package gnu.trove;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TIntArrayList
/*     */   implements Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected int[] _data;
/*     */   protected int _pos;
/*     */   protected static final int DEFAULT_CAPACITY = 10;
/*     */   
/*     */   public TIntArrayList() {
/*  60 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntArrayList(int capacity) {
/*  70 */     this._data = new int[capacity];
/*  71 */     this._pos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntArrayList(int[] values) {
/*  83 */     this(Math.max(values.length, 10));
/*  84 */     add(values);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/*  98 */     if (capacity > this._data.length) {
/*  99 */       int newCap = Math.max(this._data.length << 1, capacity);
/* 100 */       int[] tmp = new int[newCap];
/* 101 */       System.arraycopy(this._data, 0, tmp, 0, this._data.length);
/* 102 */       this._data = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 112 */     return this._pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 121 */     return (this._pos == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimToSize() {
/* 129 */     if (this._data.length > size()) {
/* 130 */       int[] tmp = new int[size()];
/* 131 */       toNativeArray(tmp, 0, tmp.length);
/* 132 */       this._data = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int val) {
/* 144 */     ensureCapacity(this._pos + 1);
/* 145 */     this._data[this._pos++] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int[] vals) {
/* 155 */     add(vals, 0, vals.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int[] vals, int offset, int length) {
/* 167 */     ensureCapacity(this._pos + length);
/* 168 */     System.arraycopy(vals, offset, this._data, this._pos, length);
/* 169 */     this._pos += length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(int offset, int value) {
/* 181 */     if (offset == this._pos) {
/* 182 */       add(value);
/*     */       return;
/*     */     } 
/* 185 */     ensureCapacity(this._pos + 1);
/*     */     
/* 187 */     System.arraycopy(this._data, offset, this._data, offset + 1, this._pos - offset);
/*     */     
/* 189 */     this._data[offset] = value;
/* 190 */     this._pos++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(int offset, int[] values) {
/* 202 */     insert(offset, values, 0, values.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(int offset, int[] values, int valOffset, int len) {
/* 217 */     if (offset == this._pos) {
/* 218 */       add(values, valOffset, len);
/*     */       
/*     */       return;
/*     */     } 
/* 222 */     ensureCapacity(this._pos + len);
/*     */     
/* 224 */     System.arraycopy(this._data, offset, this._data, offset + len, this._pos - offset);
/*     */     
/* 226 */     System.arraycopy(values, valOffset, this._data, offset, len);
/* 227 */     this._pos += len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(int offset) {
/* 237 */     if (offset >= this._pos) {
/* 238 */       throw new ArrayIndexOutOfBoundsException(offset);
/*     */     }
/* 240 */     return this._data[offset];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQuick(int offset) {
/* 251 */     return this._data[offset];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int offset, int val) {
/* 261 */     if (offset >= this._pos) {
/* 262 */       throw new ArrayIndexOutOfBoundsException(offset);
/*     */     }
/* 264 */     this._data[offset] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSet(int offset, int val) {
/* 276 */     if (offset >= this._pos) {
/* 277 */       throw new ArrayIndexOutOfBoundsException(offset);
/*     */     }
/* 279 */     int old = this._data[offset];
/* 280 */     this._data[offset] = val;
/* 281 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int offset, int[] values) {
/* 292 */     set(offset, values, 0, values.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int offset, int[] values, int valOffset, int length) {
/* 306 */     if (offset < 0 || offset + length > this._pos) {
/* 307 */       throw new ArrayIndexOutOfBoundsException(offset);
/*     */     }
/* 309 */     System.arraycopy(values, valOffset, this._data, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQuick(int offset, int val) {
/* 320 */     this._data[offset] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 328 */     clear(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear(int capacity) {
/* 338 */     this._data = new int[capacity];
/* 339 */     this._pos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 351 */     this._pos = 0;
/* 352 */     fill(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetQuick() {
/* 370 */     this._pos = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remove(int offset) {
/* 380 */     int old = get(offset);
/* 381 */     remove(offset, 1);
/* 382 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int offset, int length) {
/* 393 */     if (offset < 0 || offset >= this._pos) {
/* 394 */       throw new ArrayIndexOutOfBoundsException(offset);
/*     */     }
/*     */     
/* 397 */     if (offset == 0) {
/*     */       
/* 399 */       System.arraycopy(this._data, length, this._data, 0, this._pos - length);
/* 400 */     } else if (this._pos - length != offset) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 405 */       System.arraycopy(this._data, offset + length, this._data, offset, this._pos - offset + length);
/*     */     } 
/*     */     
/* 408 */     this._pos -= length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformValues(TIntFunction function) {
/* 420 */     for (int i = this._pos; i-- > 0;) {
/* 421 */       this._data[i] = function.execute(this._data[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reverse() {
/* 429 */     reverse(0, this._pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reverse(int from, int to) {
/* 439 */     if (from == to) {
/*     */       return;
/*     */     }
/* 442 */     if (from > to) {
/* 443 */       throw new IllegalArgumentException("from cannot be greater than to");
/*     */     }
/* 445 */     for (int i = from, j = to - 1; i < j; i++, j--) {
/* 446 */       swap(i, j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shuffle(Random rand) {
/* 457 */     for (int i = this._pos; i-- > 1;) {
/* 458 */       swap(i, rand.nextInt(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void swap(int i, int j) {
/* 469 */     int tmp = this._data[i];
/* 470 */     this._data[i] = this._data[j];
/* 471 */     this._data[j] = tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 483 */     TIntArrayList list = null;
/*     */     try {
/* 485 */       list = (TIntArrayList)super.clone();
/* 486 */       list._data = toNativeArray();
/* 487 */     } catch (CloneNotSupportedException e) {}
/*     */ 
/*     */     
/* 490 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntArrayList subList(int begin, int end) {
/* 504 */     if (end < begin) throw new IllegalArgumentException("end index " + end + " greater than begin index " + begin); 
/* 505 */     if (begin < 0) throw new IndexOutOfBoundsException("begin index can not be < 0"); 
/* 506 */     if (end > this._data.length) throw new IndexOutOfBoundsException("end index < " + this._data.length); 
/* 507 */     TIntArrayList list = new TIntArrayList(end - begin);
/* 508 */     for (int i = begin; i < end; i++) {
/* 509 */       list.add(this._data[i]);
/*     */     }
/* 511 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] toNativeArray() {
/* 521 */     return toNativeArray(0, this._pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] toNativeArray(int offset, int len) {
/* 532 */     int[] rv = new int[len];
/* 533 */     toNativeArray(rv, offset, len);
/* 534 */     return rv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toNativeArray(int[] dest, int offset, int len) {
/* 545 */     if (len == 0) {
/*     */       return;
/*     */     }
/* 548 */     if (offset < 0 || offset >= this._pos) {
/* 549 */       throw new ArrayIndexOutOfBoundsException(offset);
/*     */     }
/* 551 */     System.arraycopy(this._data, offset, dest, 0, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 564 */     if (other == this)
/* 565 */       return true; 
/* 566 */     if (other instanceof TIntArrayList) {
/* 567 */       TIntArrayList that = (TIntArrayList)other;
/* 568 */       if (that.size() != size()) {
/* 569 */         return false;
/*     */       }
/* 571 */       for (int i = this._pos; i-- > 0;) {
/* 572 */         if (this._data[i] != that._data[i]) {
/* 573 */           return false;
/*     */         }
/*     */       } 
/* 576 */       return true;
/*     */     } 
/*     */     
/* 579 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 584 */     int h = 0;
/* 585 */     for (int i = this._pos; i-- > 0;) {
/* 586 */       h += HashFunctions.hash(this._data[i]);
/*     */     }
/* 588 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEach(TIntProcedure procedure) {
/* 601 */     for (int i = 0; i < this._pos; i++) {
/* 602 */       if (!procedure.execute(this._data[i])) {
/* 603 */         return false;
/*     */       }
/*     */     } 
/* 606 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean forEachDescending(TIntProcedure procedure) {
/* 617 */     for (int i = this._pos; i-- > 0;) {
/* 618 */       if (!procedure.execute(this._data[i])) {
/* 619 */         return false;
/*     */       }
/*     */     } 
/* 622 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/* 634 */     Arrays.sort(this._data, 0, this._pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort(int fromIndex, int toIndex) {
/* 646 */     Arrays.sort(this._data, fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(int val) {
/* 657 */     Arrays.fill(this._data, 0, this._pos, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(int fromIndex, int toIndex, int val) {
/* 668 */     if (toIndex > this._pos) {
/* 669 */       ensureCapacity(toIndex);
/* 670 */       this._pos = toIndex;
/*     */     } 
/* 672 */     Arrays.fill(this._data, fromIndex, toIndex, val);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int binarySearch(int value) {
/* 687 */     return binarySearch(value, 0, this._pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int binarySearch(int value, int fromIndex, int toIndex) {
/* 702 */     if (fromIndex < 0) {
/* 703 */       throw new ArrayIndexOutOfBoundsException(fromIndex);
/*     */     }
/* 705 */     if (toIndex > this._pos) {
/* 706 */       throw new ArrayIndexOutOfBoundsException(toIndex);
/*     */     }
/*     */     
/* 709 */     int low = fromIndex;
/* 710 */     int high = toIndex - 1;
/*     */     
/* 712 */     while (low <= high) {
/* 713 */       int mid = low + high >> 1;
/* 714 */       int midVal = this._data[mid];
/*     */       
/* 716 */       if (midVal < value) {
/* 717 */         low = mid + 1; continue;
/* 718 */       }  if (midVal > value) {
/* 719 */         high = mid - 1; continue;
/*     */       } 
/* 721 */       return mid;
/*     */     } 
/*     */     
/* 724 */     return -(low + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(int value) {
/* 737 */     return indexOf(0, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int indexOf(int offset, int value) {
/* 752 */     for (int i = offset; i < this._pos; i++) {
/* 753 */       if (this._data[i] == value) {
/* 754 */         return i;
/*     */       }
/*     */     } 
/* 757 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lastIndexOf(int value) {
/* 770 */     return lastIndexOf(this._pos, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int lastIndexOf(int offset, int value) {
/* 785 */     for (int i = offset; i-- > 0;) {
/* 786 */       if (this._data[i] == value) {
/* 787 */         return i;
/*     */       }
/*     */     } 
/* 790 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(int value) {
/* 800 */     return (lastIndexOf(value) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntArrayList grep(TIntProcedure condition) {
/* 811 */     TIntArrayList list = new TIntArrayList();
/* 812 */     for (int i = 0; i < this._pos; i++) {
/* 813 */       if (condition.execute(this._data[i])) {
/* 814 */         list.add(this._data[i]);
/*     */       }
/*     */     } 
/* 817 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TIntArrayList inverseGrep(TIntProcedure condition) {
/* 828 */     TIntArrayList list = new TIntArrayList();
/* 829 */     for (int i = 0; i < this._pos; i++) {
/* 830 */       if (!condition.execute(this._data[i])) {
/* 831 */         list.add(this._data[i]);
/*     */       }
/*     */     } 
/* 834 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int max() {
/* 844 */     if (size() == 0) {
/* 845 */       throw new IllegalStateException("cannot find maximum of an empty list");
/*     */     }
/* 847 */     int max = this._data[this._pos - 1];
/* 848 */     for (int i = this._pos - 1; i-- > 0;) {
/* 849 */       if (this._data[this._pos] > max) {
/* 850 */         max = this._data[this._pos];
/*     */       }
/*     */     } 
/* 853 */     return max;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int min() {
/* 863 */     if (size() == 0) {
/* 864 */       throw new IllegalStateException("cannot find minimum of an empty list");
/*     */     }
/* 866 */     int min = this._data[this._pos - 1];
/* 867 */     for (int i = this._pos - 1; i-- > 0;) {
/* 868 */       if (this._data[this._pos] > min) {
/* 869 */         min = this._data[this._pos];
/*     */       }
/*     */     } 
/* 872 */     return min;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 883 */     StringBuffer buf = new StringBuffer("{");
/* 884 */     for (int i = 0, end = this._pos - 1; i < end; i++) {
/* 885 */       buf.append(this._data[i]);
/* 886 */       buf.append(", ");
/*     */     } 
/* 888 */     if (size() > 0) {
/* 889 */       buf.append(this._data[this._pos - 1]);
/*     */     }
/* 891 */     buf.append("}");
/* 892 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 898 */     out.writeByte(0);
/*     */ 
/*     */     
/* 901 */     out.writeInt(this._pos);
/*     */ 
/*     */     
/* 904 */     int len = this._data.length;
/* 905 */     out.writeInt(len);
/* 906 */     for (int i = 0; i < len; i++) {
/* 907 */       out.writeInt(this._data[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 915 */     in.readByte();
/*     */ 
/*     */     
/* 918 */     this._pos = in.readInt();
/*     */ 
/*     */     
/* 921 */     int len = in.readInt();
/* 922 */     this._data = new int[len];
/* 923 */     for (int i = 0; i < len; i++)
/* 924 */       this._data[i] = in.readInt(); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TIntArrayList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */