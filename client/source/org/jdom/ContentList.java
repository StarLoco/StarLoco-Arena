/*      */ package org.jdom;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractList;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import org.jdom.filter.Filter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class ContentList
/*      */   extends AbstractList
/*      */   implements Serializable
/*      */ {
/*      */   private static final String CVS_ID = "@(#) $RCSfile: ContentList.java,v $ $Revision: 1.39 $ $Date: 2004/02/28 03:30:27 $ $Name: jdom_1_0 $";
/*      */   private static final int INITIAL_ARRAY_SIZE = 5;
/*      */   private static final int CREATE = 0;
/*      */   private static final int HASPREV = 1;
/*      */   private static final int HASNEXT = 2;
/*      */   private static final int PREV = 3;
/*      */   private static final int NEXT = 4;
/*      */   private static final int ADD = 5;
/*      */   private static final int REMOVE = 6;
/*      */   private Content[] elementData;
/*      */   private int size;
/*      */   private Parent parent;
/*      */   
/*      */   ContentList(Parent parent) {
/*  110 */     this.parent = parent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void uncheckedAddContent(Content c) {
/*  120 */     c.parent = this.parent;
/*  121 */     ensureCapacity(this.size + 1);
/*  122 */     this.elementData[this.size++] = c;
/*  123 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(int index, Object obj) {
/*  136 */     if (obj == null) {
/*  137 */       throw new IllegalAddException("Cannot add null object");
/*      */     }
/*  139 */     if (obj instanceof Content) {
/*  140 */       add(index, (Content)obj);
/*      */     } else {
/*  142 */       throw new IllegalAddException("Class " + 
/*  143 */           obj.getClass().getName() + 
/*  144 */           " is of unrecognized type and cannot be added");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void documentCanContain(int index, Content child) throws IllegalAddException {
/*  152 */     if (child instanceof Element) {
/*  153 */       if (indexOfFirstElement() >= 0) {
/*  154 */         throw new IllegalAddException(
/*  155 */             "Cannot add a second root element, only one is allowed");
/*      */       }
/*  157 */       if (indexOfDocType() > index) {
/*  158 */         throw new IllegalAddException(
/*  159 */             "A root element cannot be added before the DocType");
/*      */       }
/*      */     } 
/*  162 */     if (child instanceof DocType) {
/*  163 */       if (indexOfDocType() >= 0) {
/*  164 */         throw new IllegalAddException(
/*  165 */             "Cannot add a second doctype, only one is allowed");
/*      */       }
/*  167 */       int firstElt = indexOfFirstElement();
/*  168 */       if (firstElt != -1 && firstElt < index) {
/*  169 */         throw new IllegalAddException(
/*  170 */             "A DocType cannot be added after the root element");
/*      */       }
/*      */     } 
/*  173 */     if (child instanceof CDATA) {
/*  174 */       throw new IllegalAddException("A CDATA is not allowed at the document root");
/*      */     }
/*      */     
/*  177 */     if (child instanceof Text) {
/*  178 */       throw new IllegalAddException("A Text is not allowed at the document root");
/*      */     }
/*      */     
/*  181 */     if (child instanceof EntityRef) {
/*  182 */       throw new IllegalAddException("An EntityRef is not allowed at the document root");
/*      */     }
/*      */   }
/*      */   
/*      */   private static void elementCanContain(int index, Content child) throws IllegalAddException {
/*  187 */     if (child instanceof DocType) {
/*  188 */       throw new IllegalAddException(
/*  189 */           "A DocType is not allowed except at the document level");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void add(int index, Content child) {
/*  201 */     if (child == null) {
/*  202 */       throw new IllegalAddException("Cannot add null object");
/*      */     }
/*  204 */     if (this.parent instanceof Document) {
/*  205 */       documentCanContain(index, child);
/*      */     } else {
/*      */       
/*  208 */       elementCanContain(index, child);
/*      */     } 
/*      */     
/*  211 */     if (child.getParent() != null) {
/*  212 */       Parent p = child.getParent();
/*  213 */       if (p instanceof Document) {
/*  214 */         throw new IllegalAddException((Element)child, 
/*  215 */             "The Content already has an existing parent document");
/*      */       }
/*      */       
/*  218 */       throw new IllegalAddException(
/*  219 */           "The Content already has an existing parent \"" + (
/*  220 */           (Element)p).getQualifiedName() + "\"");
/*      */     } 
/*      */ 
/*      */     
/*  224 */     if (child == this.parent) {
/*  225 */       throw new IllegalAddException(
/*  226 */           "The Element cannot be added to itself");
/*      */     }
/*      */ 
/*      */     
/*  230 */     if (this.parent instanceof Element && child instanceof Element && (
/*  231 */       (Element)child).isAncestor((Element)this.parent)) {
/*  232 */       throw new IllegalAddException(
/*  233 */           "The Element cannot be added as a descendent of itself");
/*      */     }
/*      */     
/*  236 */     if (index < 0 || index > this.size) {
/*  237 */       throw new IndexOutOfBoundsException("Index: " + index + 
/*  238 */           " Size: " + size());
/*      */     }
/*      */     
/*  241 */     child.setParent(this.parent);
/*      */     
/*  243 */     ensureCapacity(this.size + 1);
/*  244 */     if (index == this.size) {
/*  245 */       this.elementData[this.size++] = child;
/*      */     } else {
/*  247 */       System.arraycopy(this.elementData, index, this.elementData, index + 1, this.size - index);
/*  248 */       this.elementData[index] = child;
/*  249 */       this.size++;
/*      */     } 
/*  251 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(Collection collection) {
/*  262 */     return addAll(size(), collection);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addAll(int index, Collection collection) {
/*  277 */     if (index < 0 || index > this.size) {
/*  278 */       throw new IndexOutOfBoundsException("Index: " + index + 
/*  279 */           " Size: " + size());
/*      */     }
/*      */     
/*  282 */     if (collection == null || collection.size() == 0) {
/*  283 */       return false;
/*      */     }
/*  285 */     ensureCapacity(size() + collection.size());
/*      */     
/*  287 */     int count = 0;
/*      */     try {
/*  289 */       Iterator i = collection.iterator();
/*  290 */       while (i.hasNext()) {
/*  291 */         Object obj = i.next();
/*  292 */         add(index + count, obj);
/*  293 */         count++;
/*      */       }
/*      */     
/*  296 */     } catch (RuntimeException exception) {
/*  297 */       for (int i = 0; i < count; i++) {
/*  298 */         remove(index);
/*      */       }
/*  300 */       throw exception;
/*      */     } 
/*      */     
/*  303 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  310 */     if (this.elementData != null) {
/*  311 */       for (int i = 0; i < this.size; i++) {
/*  312 */         Content obj = this.elementData[i];
/*  313 */         removeParent(obj);
/*      */       } 
/*  315 */       this.elementData = null;
/*  316 */       this.size = 0;
/*      */     } 
/*  318 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void clearAndSet(Collection collection) {
/*  329 */     Content[] old = this.elementData;
/*  330 */     int oldSize = this.size;
/*      */     
/*  332 */     this.elementData = null;
/*  333 */     this.size = 0;
/*      */     
/*  335 */     if (collection != null && collection.size() != 0) {
/*  336 */       ensureCapacity(collection.size());
/*      */       try {
/*  338 */         addAll(0, collection);
/*      */       }
/*  340 */       catch (RuntimeException exception) {
/*  341 */         this.elementData = old;
/*  342 */         this.size = oldSize;
/*  343 */         throw exception;
/*      */       } 
/*      */     } 
/*      */     
/*  347 */     if (old != null) {
/*  348 */       for (int i = 0; i < oldSize; i++) {
/*  349 */         removeParent(old[i]);
/*      */       }
/*      */     }
/*  352 */     this.modCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void ensureCapacity(int minCapacity) {
/*  363 */     if (this.elementData == null) {
/*  364 */       this.elementData = new Content[Math.max(minCapacity, 5)];
/*      */     } else {
/*  366 */       int oldCapacity = this.elementData.length;
/*  367 */       if (minCapacity > oldCapacity) {
/*  368 */         Content[] arrayOfContent = this.elementData;
/*  369 */         int newCapacity = oldCapacity * 3 / 2 + 1;
/*  370 */         if (newCapacity < minCapacity)
/*  371 */           newCapacity = minCapacity; 
/*  372 */         this.elementData = new Content[newCapacity];
/*  373 */         System.arraycopy(arrayOfContent, 0, this.elementData, 0, this.size);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object get(int index) {
/*  385 */     if (index < 0 || index >= this.size) {
/*  386 */       throw new IndexOutOfBoundsException("Index: " + index + 
/*  387 */           " Size: " + size());
/*      */     }
/*  389 */     return this.elementData[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List getView(Filter filter) {
/*  399 */     return new FilterList(this, filter);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int indexOfFirstElement() {
/*  410 */     if (this.elementData != null) {
/*  411 */       for (int i = 0; i < this.size; i++) {
/*  412 */         if (this.elementData[i] instanceof Element) {
/*  413 */           return i;
/*      */         }
/*      */       } 
/*      */     }
/*  417 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   int indexOfDocType() {
/*  428 */     if (this.elementData != null) {
/*  429 */       for (int i = 0; i < this.size; i++) {
/*  430 */         if (this.elementData[i] instanceof DocType) {
/*  431 */           return i;
/*      */         }
/*      */       } 
/*      */     }
/*  435 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object remove(int index) {
/*  445 */     if (index < 0 || index >= this.size) {
/*  446 */       throw new IndexOutOfBoundsException("Index: " + index + 
/*  447 */           " Size: " + size());
/*      */     }
/*  449 */     Content old = this.elementData[index];
/*  450 */     removeParent(old);
/*  451 */     int numMoved = this.size - index - 1;
/*  452 */     if (numMoved > 0)
/*  453 */       System.arraycopy(this.elementData, index + 1, this.elementData, index, numMoved); 
/*  454 */     this.elementData[--this.size] = null;
/*  455 */     this.modCount++;
/*  456 */     return old;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void removeParent(Content c) {
/*  462 */     c.setParent(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object set(int index, Object obj) {
/*  475 */     if (index < 0 || index >= this.size) {
/*  476 */       throw new IndexOutOfBoundsException("Index: " + index + 
/*  477 */           " Size: " + size());
/*      */     }
/*  479 */     if (obj instanceof Element && this.parent instanceof Document) {
/*  480 */       int root = indexOfFirstElement();
/*  481 */       if (root >= 0 && root != index) {
/*  482 */         throw new IllegalAddException(
/*  483 */             "Cannot add a second root element, only one is allowed");
/*      */       }
/*      */     } 
/*      */     
/*  487 */     if (obj instanceof DocType && this.parent instanceof Document) {
/*  488 */       int docTypeIndex = indexOfDocType();
/*  489 */       if (docTypeIndex >= 0 && docTypeIndex != index) {
/*  490 */         throw new IllegalAddException(
/*  491 */             "Cannot add a second doctype, only one is allowed");
/*      */       }
/*      */     } 
/*      */     
/*  495 */     Object old = remove(index);
/*      */     try {
/*  497 */       add(index, obj);
/*      */     }
/*  499 */     catch (RuntimeException exception) {
/*  500 */       add(index, old);
/*  501 */       throw exception;
/*      */     } 
/*  503 */     return old;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  512 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  521 */     return super.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private int getModCount() {
/*  526 */     return this.modCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class FilterList
/*      */     extends AbstractList
/*      */     implements Serializable
/*      */   {
/*      */     private final ContentList this$0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Filter filter;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int count;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int expected;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FilterList(ContentList this$0, Filter filter) {
/*  558 */       this.this$0 = this$0; this.count = 0; this.expected = -1;
/*  559 */       this.filter = filter;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void add(int index, Object obj) {
/*  572 */       if (this.filter.matches(obj)) {
/*  573 */         int adjusted = getAdjustedIndex(index);
/*  574 */         this.this$0.add(adjusted, obj);
/*  575 */         this.expected++;
/*  576 */         this.count++;
/*      */       } else {
/*  578 */         throw new IllegalAddException("Filter won't allow the " + 
/*  579 */             obj.getClass().getName() + 
/*  580 */             " '" + obj + "' to be added to the list");
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object get(int index) {
/*  590 */       int adjusted = getAdjustedIndex(index);
/*  591 */       return this.this$0.get(adjusted);
/*      */     }
/*      */     
/*      */     public Iterator iterator() {
/*  595 */       return new ContentList.FilterListIterator(this.this$0, this.filter, 0);
/*      */     }
/*      */     
/*      */     public ListIterator listIterator() {
/*  599 */       return new ContentList.FilterListIterator(this.this$0, this.filter, 0);
/*      */     }
/*      */     
/*      */     public ListIterator listIterator(int index) {
/*  603 */       return new ContentList.FilterListIterator(this.this$0, this.filter, index);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object remove(int index) {
/*  613 */       int adjusted = getAdjustedIndex(index);
/*  614 */       Object old = this.this$0.get(adjusted);
/*  615 */       if (this.filter.matches(old)) {
/*  616 */         old = this.this$0.remove(adjusted);
/*  617 */         this.expected++;
/*  618 */         this.count--;
/*      */       } else {
/*      */         
/*  621 */         throw new IllegalAddException("Filter won't allow the " + 
/*  622 */             old.getClass().getName() + 
/*  623 */             " '" + old + "' (index " + index + 
/*  624 */             ") to be removed");
/*      */       } 
/*  626 */       return old;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object set(int index, Object obj) {
/*  639 */       Object old = null;
/*  640 */       if (this.filter.matches(obj)) {
/*  641 */         int adjusted = getAdjustedIndex(index);
/*  642 */         old = this.this$0.get(adjusted);
/*  643 */         if (!this.filter.matches(old)) {
/*  644 */           throw new IllegalAddException("Filter won't allow the " + 
/*  645 */               old.getClass().getName() + 
/*  646 */               " '" + old + "' (index " + index + 
/*  647 */               ") to be removed");
/*      */         }
/*  649 */         old = this.this$0.set(adjusted, obj);
/*  650 */         this.expected += 2;
/*      */       } else {
/*      */         
/*  653 */         throw new IllegalAddException("Filter won't allow index " + 
/*  654 */             index + " to be set to " + 
/*  655 */             obj.getClass().getName());
/*      */       } 
/*  657 */       return old;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int size() {
/*  673 */       if (this.expected == this.this$0.getModCount()) {
/*  674 */         return this.count;
/*      */       }
/*      */       
/*  677 */       this.count = 0;
/*  678 */       for (int i = 0; i < this.this$0.size(); i++) {
/*  679 */         Object obj = this.this$0.elementData[i];
/*  680 */         if (this.filter.matches(obj)) {
/*  681 */           this.count++;
/*      */         }
/*      */       } 
/*  684 */       this.expected = this.this$0.getModCount();
/*  685 */       return this.count;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int getAdjustedIndex(int index) {
/*  695 */       int adjusted = 0;
/*  696 */       for (int i = 0; i < this.this$0.size; i++) {
/*  697 */         Object obj = this.this$0.elementData[i];
/*  698 */         if (this.filter.matches(obj)) {
/*  699 */           if (index == adjusted) {
/*  700 */             return i;
/*      */           }
/*  702 */           adjusted++;
/*      */         } 
/*      */       } 
/*      */       
/*  706 */       if (index == adjusted) {
/*  707 */         return this.this$0.size;
/*      */       }
/*      */       
/*  710 */       return this.this$0.size + 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class FilterListIterator
/*      */     implements ListIterator
/*      */   {
/*      */     private final ContentList this$0;
/*      */ 
/*      */     
/*      */     Filter filter;
/*      */ 
/*      */     
/*      */     int lastOperation;
/*      */ 
/*      */     
/*      */     int initialCursor;
/*      */ 
/*      */     
/*      */     int cursor;
/*      */ 
/*      */     
/*      */     int last;
/*      */     
/*      */     int expected;
/*      */ 
/*      */     
/*      */     FilterListIterator(ContentList this$0, Filter filter, int start) {
/*  740 */       this.this$0 = this$0;
/*  741 */       this.filter = filter;
/*  742 */       this.initialCursor = initializeCursor(start);
/*  743 */       this.last = -1;
/*  744 */       this.expected = this$0.getModCount();
/*  745 */       this.lastOperation = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: invokespecial checkConcurrentModification : ()V
/*      */       //   4: aload_0
/*      */       //   5: getfield lastOperation : I
/*      */       //   8: tableswitch default -> 123, 0 -> 52, 1 -> 106, 2 -> 133, 3 -> 63, 4 -> 74, 5 -> 74, 6 -> 91
/*      */       //   52: aload_0
/*      */       //   53: aload_0
/*      */       //   54: getfield initialCursor : I
/*      */       //   57: putfield cursor : I
/*      */       //   60: goto -> 133
/*      */       //   63: aload_0
/*      */       //   64: aload_0
/*      */       //   65: getfield last : I
/*      */       //   68: putfield cursor : I
/*      */       //   71: goto -> 133
/*      */       //   74: aload_0
/*      */       //   75: aload_0
/*      */       //   76: aload_0
/*      */       //   77: getfield last : I
/*      */       //   80: iconst_1
/*      */       //   81: iadd
/*      */       //   82: invokespecial moveForward : (I)I
/*      */       //   85: putfield cursor : I
/*      */       //   88: goto -> 133
/*      */       //   91: aload_0
/*      */       //   92: aload_0
/*      */       //   93: aload_0
/*      */       //   94: getfield last : I
/*      */       //   97: invokespecial moveForward : (I)I
/*      */       //   100: putfield cursor : I
/*      */       //   103: goto -> 133
/*      */       //   106: aload_0
/*      */       //   107: aload_0
/*      */       //   108: aload_0
/*      */       //   109: getfield cursor : I
/*      */       //   112: iconst_1
/*      */       //   113: iadd
/*      */       //   114: invokespecial moveForward : (I)I
/*      */       //   117: putfield cursor : I
/*      */       //   120: goto -> 133
/*      */       //   123: new java/lang/IllegalStateException
/*      */       //   126: dup
/*      */       //   127: ldc 'Unknown operation'
/*      */       //   129: invokespecial <init> : (Ljava/lang/String;)V
/*      */       //   132: athrow
/*      */       //   133: aload_0
/*      */       //   134: getfield lastOperation : I
/*      */       //   137: ifeq -> 145
/*      */       //   140: aload_0
/*      */       //   141: iconst_2
/*      */       //   142: putfield lastOperation : I
/*      */       //   145: aload_0
/*      */       //   146: getfield cursor : I
/*      */       //   149: aload_0
/*      */       //   150: getfield this$0 : Lorg/jdom/ContentList;
/*      */       //   153: invokevirtual size : ()I
/*      */       //   156: if_icmpge -> 163
/*      */       //   159: iconst_1
/*      */       //   160: goto -> 164
/*      */       //   163: iconst_0
/*      */       //   164: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #752	-> 0
/*      */       //   #754	-> 4
/*      */       //   #755	-> 52
/*      */       //   #756	-> 60
/*      */       //   #757	-> 63
/*      */       //   #758	-> 71
/*      */       //   #760	-> 74
/*      */       //   #761	-> 88
/*      */       //   #762	-> 91
/*      */       //   #763	-> 103
/*      */       //   #764	-> 106
/*      */       //   #765	-> 120
/*      */       //   #767	-> 123
/*      */       //   #770	-> 133
/*      */       //   #771	-> 140
/*      */       //   #94	-> 141
/*      */       //   #771	-> 142
/*      */       //   #774	-> 145
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	165	0	this	Lorg/jdom/ContentList$FilterListIterator;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object next() {
/*  781 */       checkConcurrentModification();
/*      */       
/*  783 */       if (hasNext()) {
/*  784 */         this.last = this.cursor;
/*      */       } else {
/*      */         
/*  787 */         this.last = this.this$0.size();
/*  788 */         throw new NoSuchElementException();
/*      */       } 
/*      */       
/*  791 */       this.lastOperation = 4;
/*  792 */       return this.this$0.get(this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasPrevious() {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: invokespecial checkConcurrentModification : ()V
/*      */       //   4: aload_0
/*      */       //   5: getfield lastOperation : I
/*      */       //   8: tableswitch default -> 135, 0 -> 52, 1 -> 145, 2 -> 107, 3 -> 90, 4 -> 124, 5 -> 124, 6 -> 90
/*      */       //   52: aload_0
/*      */       //   53: aload_0
/*      */       //   54: getfield initialCursor : I
/*      */       //   57: putfield cursor : I
/*      */       //   60: aload_0
/*      */       //   61: getfield this$0 : Lorg/jdom/ContentList;
/*      */       //   64: invokevirtual size : ()I
/*      */       //   67: istore_1
/*      */       //   68: aload_0
/*      */       //   69: getfield cursor : I
/*      */       //   72: iload_1
/*      */       //   73: if_icmplt -> 145
/*      */       //   76: aload_0
/*      */       //   77: aload_0
/*      */       //   78: iload_1
/*      */       //   79: iconst_1
/*      */       //   80: isub
/*      */       //   81: invokespecial moveBackward : (I)I
/*      */       //   84: putfield cursor : I
/*      */       //   87: goto -> 145
/*      */       //   90: aload_0
/*      */       //   91: aload_0
/*      */       //   92: aload_0
/*      */       //   93: getfield last : I
/*      */       //   96: iconst_1
/*      */       //   97: isub
/*      */       //   98: invokespecial moveBackward : (I)I
/*      */       //   101: putfield cursor : I
/*      */       //   104: goto -> 145
/*      */       //   107: aload_0
/*      */       //   108: aload_0
/*      */       //   109: aload_0
/*      */       //   110: getfield cursor : I
/*      */       //   113: iconst_1
/*      */       //   114: isub
/*      */       //   115: invokespecial moveBackward : (I)I
/*      */       //   118: putfield cursor : I
/*      */       //   121: goto -> 145
/*      */       //   124: aload_0
/*      */       //   125: aload_0
/*      */       //   126: getfield last : I
/*      */       //   129: putfield cursor : I
/*      */       //   132: goto -> 145
/*      */       //   135: new java/lang/IllegalStateException
/*      */       //   138: dup
/*      */       //   139: ldc 'Unknown operation'
/*      */       //   141: invokespecial <init> : (Ljava/lang/String;)V
/*      */       //   144: athrow
/*      */       //   145: aload_0
/*      */       //   146: getfield lastOperation : I
/*      */       //   149: ifeq -> 157
/*      */       //   152: aload_0
/*      */       //   153: iconst_1
/*      */       //   154: putfield lastOperation : I
/*      */       //   157: aload_0
/*      */       //   158: getfield cursor : I
/*      */       //   161: ifge -> 168
/*      */       //   164: iconst_0
/*      */       //   165: goto -> 169
/*      */       //   168: iconst_1
/*      */       //   169: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #800	-> 0
/*      */       //   #802	-> 4
/*      */       //   #803	-> 52
/*      */       //   #804	-> 60
/*      */       //   #805	-> 68
/*      */       //   #806	-> 76
/*      */       //   #808	-> 87
/*      */       //   #810	-> 90
/*      */       //   #811	-> 104
/*      */       //   #812	-> 107
/*      */       //   #813	-> 121
/*      */       //   #815	-> 124
/*      */       //   #816	-> 132
/*      */       //   #818	-> 135
/*      */       //   #821	-> 145
/*      */       //   #822	-> 152
/*      */       //   #93	-> 153
/*      */       //   #822	-> 154
/*      */       //   #825	-> 157
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	170	0	this	Lorg/jdom/ContentList$FilterListIterator;
/*      */       //   68	22	1	size	I
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object previous() {
/*  832 */       checkConcurrentModification();
/*      */       
/*  834 */       if (hasPrevious()) {
/*  835 */         this.last = this.cursor;
/*      */       } else {
/*      */         
/*  838 */         this.last = -1;
/*  839 */         throw new NoSuchElementException();
/*      */       } 
/*      */       
/*  842 */       this.lastOperation = 3;
/*  843 */       return this.this$0.get(this.last);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nextIndex() {
/*  851 */       checkConcurrentModification();
/*  852 */       hasNext();
/*      */       
/*  854 */       int count = 0;
/*  855 */       for (int i = 0; i < this.this$0.size(); i++) {
/*  856 */         if (this.filter.matches(this.this$0.get(i))) {
/*  857 */           if (i == this.cursor) {
/*  858 */             return count;
/*      */           }
/*  860 */           count++;
/*      */         } 
/*      */       } 
/*  863 */       this.expected = this.this$0.getModCount();
/*  864 */       return count;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int previousIndex() {
/*  873 */       checkConcurrentModification();
/*      */       
/*  875 */       if (hasPrevious()) {
/*  876 */         int count = 0;
/*  877 */         for (int i = 0; i < this.this$0.size(); i++) {
/*  878 */           if (this.filter.matches(this.this$0.get(i))) {
/*  879 */             if (i == this.cursor) {
/*  880 */               return count;
/*      */             }
/*  882 */             count++;
/*      */           } 
/*      */         } 
/*      */       } 
/*  886 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void add(Object obj) {
/*  893 */       checkConcurrentModification();
/*      */       
/*  895 */       if (this.filter.matches(obj)) {
/*  896 */         this.last = this.cursor + 1;
/*  897 */         this.this$0.add(this.last, obj);
/*      */       } else {
/*      */         
/*  900 */         throw new IllegalAddException("Filter won't allow add of " + 
/*  901 */             obj.getClass().getName());
/*      */       } 
/*  903 */       this.expected = this.this$0.getModCount();
/*  904 */       this.lastOperation = 5;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() {
/*  913 */       checkConcurrentModification();
/*      */       
/*  915 */       if (this.last < 0 || this.lastOperation == 6) {
/*  916 */         throw new IllegalStateException("no preceeding call to prev() or next()");
/*      */       }
/*      */ 
/*      */       
/*  920 */       if (this.lastOperation == 5) {
/*  921 */         throw new IllegalStateException("cannot call remove() after add()");
/*      */       }
/*      */ 
/*      */       
/*  925 */       Object old = this.this$0.get(this.last);
/*  926 */       if (this.filter.matches(old)) {
/*  927 */         this.this$0.remove(this.last);
/*      */       } else {
/*  929 */         throw new IllegalAddException("Filter won't allow " + 
/*  930 */             old.getClass().getName() + 
/*  931 */             " (index " + this.last + 
/*  932 */             ") to be removed");
/*  933 */       }  this.expected = this.this$0.getModCount();
/*  934 */       this.lastOperation = 6;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void set(Object obj) {
/*  942 */       checkConcurrentModification();
/*      */       
/*  944 */       if (this.lastOperation == 5 || this.lastOperation == 6) {
/*  945 */         throw new IllegalStateException("cannot call set() after add() or remove()");
/*      */       }
/*      */ 
/*      */       
/*  949 */       if (this.last < 0) {
/*  950 */         throw new IllegalStateException("no preceeding call to prev() or next()");
/*      */       }
/*      */ 
/*      */       
/*  954 */       if (this.filter.matches(obj)) {
/*  955 */         Object old = this.this$0.get(this.last);
/*  956 */         if (!this.filter.matches(old)) {
/*  957 */           throw new IllegalAddException("Filter won't allow " + 
/*  958 */               old.getClass().getName() + " (index " + 
/*  959 */               this.last + ") to be removed");
/*      */         }
/*  961 */         this.this$0.set(this.last, obj);
/*      */       } else {
/*      */         
/*  964 */         throw new IllegalAddException("Filter won't allow index " + 
/*  965 */             this.last + " to be set to " + 
/*  966 */             obj.getClass().getName());
/*      */       } 
/*      */       
/*  969 */       this.expected = this.this$0.getModCount();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int initializeCursor(int start) {
/*  978 */       if (start < 0) {
/*  979 */         throw new IndexOutOfBoundsException("Index: " + start);
/*      */       }
/*      */       
/*  982 */       int count = 0;
/*  983 */       for (int i = 0; i < this.this$0.size(); i++) {
/*  984 */         Object obj = this.this$0.get(i);
/*  985 */         if (this.filter.matches(obj)) {
/*  986 */           if (start == count) {
/*  987 */             return i;
/*      */           }
/*  989 */           count++;
/*      */         } 
/*      */       } 
/*      */       
/*  993 */       if (start > count) {
/*  994 */         throw new IndexOutOfBoundsException("Index: " + start + 
/*  995 */             " Size: " + count);
/*      */       }
/*      */       
/*  998 */       return this.this$0.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int moveForward(int start) {
/* 1006 */       if (start < 0) {
/* 1007 */         start = 0;
/*      */       }
/* 1009 */       for (int i = start; i < this.this$0.size(); i++) {
/* 1010 */         Object obj = this.this$0.get(i);
/* 1011 */         if (this.filter.matches(obj)) {
/* 1012 */           return i;
/*      */         }
/*      */       } 
/* 1015 */       return this.this$0.size();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int moveBackward(int start) {
/* 1023 */       if (start >= this.this$0.size()) {
/* 1024 */         start = this.this$0.size() - 1;
/*      */       }
/*      */       
/* 1027 */       for (int i = start; i >= 0; i--) {
/* 1028 */         Object obj = this.this$0.get(i);
/* 1029 */         if (this.filter.matches(obj)) {
/* 1030 */           return i;
/*      */         }
/*      */       } 
/* 1033 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void checkConcurrentModification() {
/* 1040 */       if (this.expected != this.this$0.getModCount())
/* 1041 */         throw new ConcurrentModificationException(); 
/*      */     }
/*      */   }
/*      */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\ContentList.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */