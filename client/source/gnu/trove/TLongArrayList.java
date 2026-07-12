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
/*     */ public class TLongArrayList
/*     */   implements Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected long[] _data;
/*     */   protected int _pos;
/*     */   protected static final int DEFAULT_CAPACITY = 10;
/*     */   
/*     */   public TLongArrayList() {
/*  60 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TLongArrayList(int capacity) {
/*  70 */     this._data = new long[capacity];
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
/*     */   public TLongArrayList(long[] values) {
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
/* 100 */       long[] tmp = new long[newCap];
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
/* 130 */       long[] tmp = new long[size()];
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
/*     */   public void add(long val) {
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
/*     */   public void add(long[] vals) {
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
/*     */   public void add(long[] vals, int offset, int length) {
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
/*     */   public void insert(int offset, long value) {
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
/*     */   public void insert(int offset, long[] values) {
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
/*     */   public void insert(int offset, long[] values, int valOffset, int len) {
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
/*     */   public long get(int offset) {
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
/*     */   public long getQuick(int offset) {
/* 251 */     return this._data[offset];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int offset, long val) {
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
/*     */   public long getSet(int offset, long val) {
/* 276 */     if (offset >= this._pos) {
/* 277 */       throw new ArrayIndexOutOfBoundsException(offset);
/*     */     }
/* 279 */     long old = this._data[offset];
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
/*     */   public void set(int offset, long[] values) {
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
/*     */   public void set(int offset, long[] values, int valOffset, int length) {
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
/*     */   public void setQuick(int offset, long val) {
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
/* 338 */     this._data = new long[capacity];
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
/* 352 */     fill(0L);
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
/*     */   public long remove(int offset) {
/* 380 */     long old = get(offset);
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
/*     */   public void transformValues(TLongFunction function) {
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
/* 469 */     long tmp = this._data[i];
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
/* 483 */     TLongArrayList list = null;
/*     */     try {
/* 485 */       list = (TLongArrayList)super.clone();
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
/*     */   public TLongArrayList subList(int begin, int end) {
/* 504 */     if (end < begin) throw new IllegalArgumentException("end index " + end + " greater than begin index " + begin); 
/* 505 */     if (begin < 0) throw new IndexOutOfBoundsException("begin index can not be < 0"); 
/* 506 */     if (end > this._data.length) throw new IndexOutOfBoundsException("end index < " + this._data.length); 
/* 507 */     TLongArrayList list = new TLongArrayList(end - begin);
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
/*     */   public long[] toNativeArray() {
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
/*     */   public long[] toNativeArray(int offset, int len) {
/* 532 */     long[] rv = new long[len];
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
/*     */   public void toNativeArray(long[] dest, int offset, int len) {
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
/* 566 */     if (other instanceof TLongArrayList) {
/* 567 */       TLongArrayList that = (TLongArrayList)other;
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
/*     */   public boolean forEach(TLongProcedure procedure) {
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
/*     */   public boolean forEachDescending(TLongProcedure procedure) {
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
/*     */   public void fill(long val) {
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
/*     */   public void fill(int fromIndex, int toIndex, long val) {
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
/*     */   public int binarySearch(long value) {
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
/*     */   public int binarySearch(long value, int fromIndex, int toIndex) {
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
/* 714 */       long midVal = this._data[mid];
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
/*     */   public int indexOf(long value) {
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
/*     */   public int indexOf(int offset, long value) {
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
/*     */   public int lastIndexOf(long value) {
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
/*     */   public int lastIndexOf(int offset, long value) {
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
/*     */   public boolean contains(long value) {
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
/*     */   public TLongArrayList grep(TLongProcedure condition) {
/* 811 */     TLongArrayList list = new TLongArrayList();
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
/*     */   public TLongArrayList inverseGrep(TLongProcedure condition) {
/* 828 */     TLongArrayList list = new TLongArrayList();
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
/*     */   public long max() {
/* 844 */     if (size() == 0) {
/* 845 */       throw new IllegalStateException("cannot find maximum of an empty list");
/*     */     }
/* 847 */     long max = this._data[this._pos - 1];
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
/*     */   public long min() {
/* 863 */     if (size() == 0) {
/* 864 */       throw new IllegalStateException("cannot find minimum of an empty list");
/*     */     }
/* 866 */     long min = this._data[this._pos - 1];
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
/* 907 */       out.writeLong(this._data[i]);
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
/* 922 */     this._data = new long[len];
/* 923 */     for (int i = 0; i < len; i++)
/* 924 */       this._data[i] = in.readLong(); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TLongArrayList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */