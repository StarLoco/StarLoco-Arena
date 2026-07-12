/*     */ package org.jdom.input;
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
/*     */ class TextBuffer
/*     */ {
/*     */   private static final String CVS_ID = "@(#) $RCSfile: TextBuffer.java,v $ $Revision: 1.8 $ $Date: 2004/02/06 09:28:31 $ $Name: jdom_1_0 $";
/*     */   private String prefixString;
/*  95 */   private char[] array = new char[4096];
/*  96 */   private int arraySize = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   void append(char[] source, int start, int count) {
/* 101 */     if (this.prefixString == null) {
/*     */       
/* 103 */       this.prefixString = new String(source, start, count);
/*     */     }
/*     */     else {
/*     */       
/* 107 */       ensureCapacity(this.arraySize + count);
/* 108 */       System.arraycopy(source, start, this.array, this.arraySize, count);
/* 109 */       this.arraySize += count;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   int size() {
/* 115 */     if (this.prefixString == null) {
/* 116 */       return 0;
/*     */     }
/*     */     
/* 119 */     return this.prefixString.length() + this.arraySize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void clear() {
/* 125 */     this.arraySize = 0;
/* 126 */     this.prefixString = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     if (this.prefixString == null) {
/* 132 */       return "";
/*     */     }
/*     */     
/* 135 */     String str = "";
/* 136 */     if (this.arraySize == 0) {
/*     */       
/* 138 */       str = this.prefixString;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 143 */       str = (new StringBuffer(this.prefixString.length() + this.arraySize))
/* 144 */         .append(this.prefixString)
/* 145 */         .append(this.array, 0, this.arraySize)
/* 146 */         .toString();
/*     */     } 
/* 148 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensureCapacity(int csize) {
/* 153 */     int capacity = this.array.length;
/* 154 */     if (csize > capacity) {
/* 155 */       char[] old = this.array;
/* 156 */       int nsize = capacity;
/* 157 */       while (csize > nsize) {
/* 158 */         nsize += capacity / 2;
/*     */       }
/* 160 */       this.array = new char[nsize];
/* 161 */       System.arraycopy(old, 0, this.array, 0, this.arraySize);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\jdom\input\TextBuffer.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */