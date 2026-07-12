/*     */ package gnu.trove;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.AbstractSequentialList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class TLinkedList<T extends TLinkable>
/*     */   extends AbstractSequentialList<T>
/*     */   implements Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected T _head;
/*     */   protected T _tail;
/*  69 */   protected int _size = 0;
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
/*     */   public ListIterator<T> listIterator(int index) {
/*  92 */     return new IteratorImpl(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 101 */     return this._size;
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
/*     */   public void add(int index, T linkable) {
/* 113 */     if (index < 0 || index > size()) {
/* 114 */       throw new IndexOutOfBoundsException("index:" + index);
/*     */     }
/* 116 */     insert(index, linkable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(T linkable) {
/* 126 */     insert(this._size, linkable);
/* 127 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFirst(T linkable) {
/* 136 */     insert(0, linkable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLast(T linkable) {
/* 145 */     insert(size(), linkable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 153 */     if (null != this._head) {
/* 154 */       TLinkable link = this._head.getNext();
/* 155 */       for (; link != null; 
/* 156 */         link = link.getNext()) {
/* 157 */         TLinkable prev = link.getPrevious();
/* 158 */         prev.setNext(null);
/* 159 */         link.setPrevious(null);
/*     */       } 
/* 161 */       this._head = this._tail = null;
/*     */     } 
/* 163 */     this._size = 0;
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
/*     */   public Object[] toArray() {
/* 178 */     Object[] o = new Object[this._size];
/* 179 */     int i = 0;
/* 180 */     for (T t = this._head; t != null; tLinkable = t.getNext()) {
/* 181 */       TLinkable tLinkable; o[i++] = t;
/*     */     } 
/* 183 */     return o;
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
/*     */   public Object[] toUnlinkedArray() {
/* 197 */     Object[] o = new Object[this._size];
/* 198 */     int i = 0;
/* 199 */     for (T link = this._head, tmp = null; link != null; i++) {
/* 200 */       o[i] = link;
/* 201 */       tmp = link;
/* 202 */       TLinkable tLinkable = link.getNext();
/* 203 */       tmp.setNext(null);
/* 204 */       tmp.setPrevious(null);
/*     */     } 
/* 206 */     this._size = 0;
/* 207 */     this._head = this._tail = null;
/* 208 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 218 */     for (T t = this._head; t != null; tLinkable = t.getNext()) {
/* 219 */       TLinkable tLinkable; if (o.equals(t)) {
/* 220 */         return true;
/*     */       }
/*     */     } 
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(int index) {
/*     */     TLinkable tLinkable;
/* 233 */     if (index == 0) return this._head; 
/* 234 */     if (index == this._size - 1) return this._tail;
/*     */ 
/*     */     
/* 237 */     if (index < 0 || index >= this._size) {
/* 238 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this._size);
/*     */     }
/*     */ 
/*     */     
/* 242 */     if (index > this._size >> 1) {
/* 243 */       int i = this._size - 1;
/* 244 */       T t = this._tail;
/*     */       
/* 246 */       while (i > index) {
/* 247 */         tLinkable = t.getPrevious();
/* 248 */         i--;
/*     */       } 
/*     */       
/* 251 */       return (T)tLinkable;
/*     */     } 
/*     */     
/* 254 */     int position = 0;
/* 255 */     T node = this._head;
/*     */     
/* 257 */     while (position < index) {
/* 258 */       tLinkable = node.getNext();
/* 259 */       position++;
/*     */     } 
/*     */     
/* 262 */     return (T)tLinkable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getFirst() {
/* 273 */     return this._head;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getLast() {
/* 282 */     return this._tail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T removeFirst() {
/* 291 */     T o = this._head;
/* 292 */     TLinkable tLinkable = o.getNext();
/* 293 */     o.setNext(null);
/*     */     
/* 295 */     if (null != tLinkable) {
/* 296 */       tLinkable.setPrevious(null);
/*     */     }
/*     */     
/* 299 */     this._head = (T)tLinkable;
/* 300 */     if (--this._size == 0) {
/* 301 */       this._tail = null;
/*     */     }
/* 303 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T removeLast() {
/* 312 */     T o = this._tail;
/* 313 */     TLinkable tLinkable = o.getPrevious();
/* 314 */     o.setPrevious(null);
/*     */     
/* 316 */     if (null != tLinkable) {
/* 317 */       tLinkable.setNext(null);
/*     */     }
/* 319 */     this._tail = (T)tLinkable;
/* 320 */     if (--this._size == 0) {
/* 321 */       this._head = null;
/*     */     }
/* 323 */     return o;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void insert(int index, T linkable) {
/* 333 */     T newLink = linkable;
/*     */     
/* 335 */     if (this._size == 0) {
/* 336 */       this._head = this._tail = newLink;
/* 337 */     } else if (index == 0) {
/* 338 */       newLink.setNext((TLinkable)this._head);
/* 339 */       this._head.setPrevious((TLinkable)newLink);
/* 340 */       this._head = newLink;
/* 341 */     } else if (index == this._size) {
/* 342 */       this._tail.setNext((TLinkable)newLink);
/* 343 */       newLink.setPrevious((TLinkable)this._tail);
/* 344 */       this._tail = newLink;
/*     */     } else {
/* 346 */       T node = get(index);
/*     */       
/* 348 */       TLinkable tLinkable = node.getPrevious();
/* 349 */       if (tLinkable != null) tLinkable.setNext((TLinkable)linkable);
/*     */       
/* 351 */       linkable.setPrevious(tLinkable);
/* 352 */       linkable.setNext((TLinkable)node);
/* 353 */       node.setPrevious((TLinkable)linkable);
/*     */     } 
/* 355 */     this._size++;
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
/*     */   public boolean remove(Object o) {
/* 368 */     if (o instanceof TLinkable) {
/*     */       
/* 370 */       TLinkable link = (TLinkable)o;
/*     */       
/* 372 */       TLinkable tLinkable1 = link.getPrevious();
/* 373 */       TLinkable tLinkable2 = link.getNext();
/*     */       
/* 375 */       if (tLinkable2 == null && tLinkable1 == null) {
/* 376 */         this._head = this._tail = null;
/* 377 */       } else if (tLinkable2 == null) {
/*     */         
/* 379 */         link.setPrevious(null);
/* 380 */         tLinkable1.setNext(null);
/* 381 */         this._tail = (T)tLinkable1;
/* 382 */       } else if (tLinkable1 == null) {
/*     */         
/* 384 */         link.setNext(null);
/* 385 */         tLinkable2.setPrevious(null);
/* 386 */         this._head = (T)tLinkable2;
/*     */       } else {
/* 388 */         tLinkable1.setNext(tLinkable2);
/* 389 */         tLinkable2.setPrevious(tLinkable1);
/* 390 */         link.setNext(null);
/* 391 */         link.setPrevious(null);
/*     */       } 
/*     */       
/* 394 */       this._size--;
/* 395 */       return true;
/*     */     } 
/* 397 */     return false;
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
/*     */   public void addBefore(T current, T newElement) {
/* 411 */     if (current == this._head) {
/* 412 */       addFirst(newElement);
/* 413 */     } else if (current == null) {
/* 414 */       addLast(newElement);
/*     */     } else {
/* 416 */       TLinkable p = current.getPrevious();
/* 417 */       newElement.setNext((TLinkable)current);
/* 418 */       p.setNext((TLinkable)newElement);
/* 419 */       newElement.setPrevious(p);
/* 420 */       current.setPrevious((TLinkable)newElement);
/* 421 */       this._size++;
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
/*     */   
/*     */   public boolean forEachValue(TObjectProcedure<T> procedure) {
/* 434 */     T node = this._head;
/* 435 */     while (node != null) {
/* 436 */       boolean keep_going = procedure.execute(node);
/* 437 */       if (!keep_going) return false;
/*     */       
/* 439 */       TLinkable tLinkable = node.getNext();
/*     */     } 
/*     */     
/* 442 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeExternal(ObjectOutput out) throws IOException {
/* 449 */     out.writeByte(0);
/*     */ 
/*     */     
/* 452 */     out.writeInt(this._size);
/*     */ 
/*     */     
/* 455 */     out.writeObject(this._head);
/*     */ 
/*     */     
/* 458 */     out.writeObject(this._head);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
/* 465 */     in.readByte();
/*     */ 
/*     */     
/* 468 */     this._size = in.readInt();
/*     */ 
/*     */     
/* 471 */     this._head = (T)in.readObject();
/*     */ 
/*     */     
/* 474 */     this._tail = (T)in.readObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final class IteratorImpl
/*     */     implements ListIterator<T>
/*     */   {
/* 483 */     private int _nextIndex = 0;
/*     */ 
/*     */     
/*     */     private T _next;
/*     */ 
/*     */     
/*     */     private T _lastReturned;
/*     */ 
/*     */ 
/*     */     
/*     */     IteratorImpl(int position) {
/* 494 */       if (position < 0 || position > TLinkedList.this._size) {
/* 495 */         throw new IndexOutOfBoundsException();
/*     */       }
/*     */       
/* 498 */       this._nextIndex = position;
/* 499 */       if (position == 0) {
/* 500 */         this._next = TLinkedList.this._head;
/* 501 */       } else if (position == TLinkedList.this._size) {
/* 502 */         this._next = null;
/* 503 */       } else if (position < TLinkedList.this._size >> 1) {
/* 504 */         int pos = 0;
/* 505 */         for (this._next = TLinkedList.this._head; pos < position; pos++) {
/* 506 */           this._next = (T)this._next.getNext();
/*     */         }
/*     */       } else {
/* 509 */         int pos = TLinkedList.this._size - 1;
/* 510 */         for (this._next = TLinkedList.this._tail; pos > position; pos--) {
/* 511 */           this._next = (T)this._next.getPrevious();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void add(T linkable) {
/* 523 */       this._lastReturned = null;
/* 524 */       this._nextIndex++;
/*     */       
/* 526 */       if (TLinkedList.this._size == 0) {
/* 527 */         TLinkedList.this.add(linkable);
/*     */       } else {
/* 529 */         TLinkedList.this.addBefore(this._next, linkable);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean hasNext() {
/* 539 */       return (this._nextIndex != TLinkedList.this._size);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean hasPrevious() {
/* 548 */       return (this._nextIndex != 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final T next() {
/* 559 */       if (this._nextIndex == TLinkedList.this._size) {
/* 560 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 563 */       this._lastReturned = this._next;
/* 564 */       this._next = (T)this._next.getNext();
/* 565 */       this._nextIndex++;
/* 566 */       return this._lastReturned;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int nextIndex() {
/* 576 */       return this._nextIndex;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final T previous() {
/* 587 */       if (this._nextIndex == 0) {
/* 588 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 591 */       if (this._nextIndex == TLinkedList.this._size) {
/* 592 */         this._lastReturned = this._next = TLinkedList.this._tail;
/*     */       } else {
/* 594 */         this._lastReturned = this._next = (T)this._next.getPrevious();
/*     */       } 
/*     */       
/* 597 */       this._nextIndex--;
/* 598 */       return this._lastReturned;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int previousIndex() {
/* 607 */       return this._nextIndex - 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void remove() {
/* 619 */       if (this._lastReturned == null) {
/* 620 */         throw new IllegalStateException("must invoke next or previous before invoking remove");
/*     */       }
/*     */       
/* 623 */       if (this._lastReturned != this._next) {
/* 624 */         this._nextIndex--;
/*     */       }
/* 626 */       this._next = (T)this._lastReturned.getNext();
/* 627 */       TLinkedList.this.remove(this._lastReturned);
/* 628 */       this._lastReturned = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void set(T linkable) {
/* 638 */       if (this._lastReturned == null) {
/* 639 */         throw new IllegalStateException();
/*     */       }
/* 641 */       T l = linkable;
/*     */ 
/*     */ 
/*     */       
/* 645 */       if (this._lastReturned == TLinkedList.this._head) {
/* 646 */         TLinkedList.this._head = l;
/*     */       }
/*     */       
/* 649 */       if (this._lastReturned == TLinkedList.this._tail) {
/* 650 */         TLinkedList.this._tail = l;
/*     */       }
/*     */       
/* 653 */       swap(this._lastReturned, l);
/* 654 */       this._lastReturned = l;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void swap(T from, T to) {
/* 664 */       TLinkable tLinkable1 = from.getPrevious();
/* 665 */       TLinkable tLinkable2 = from.getNext();
/*     */       
/* 667 */       if (null != tLinkable1) {
/* 668 */         to.setPrevious(tLinkable1);
/* 669 */         tLinkable1.setNext((TLinkable)to);
/*     */       } 
/* 671 */       if (null != tLinkable2) {
/* 672 */         to.setNext(tLinkable2);
/* 673 */         tLinkable2.setPrevious((TLinkable)to);
/*     */       } 
/* 675 */       from.setNext(null);
/* 676 */       from.setPrevious(null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TLinkedList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */