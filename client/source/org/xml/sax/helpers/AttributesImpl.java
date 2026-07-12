/*     */ package org.xml.sax.helpers;
/*     */ 
/*     */ import org.xml.sax.Attributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttributesImpl
/*     */   implements Attributes
/*     */ {
/*     */   int length;
/*     */   String[] data;
/*     */   
/*     */   public AttributesImpl() {
/*  60 */     this.length = 0;
/*  61 */     this.data = null;
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
/*     */   public AttributesImpl(Attributes paramAttributes) {
/*  75 */     setAttributes(paramAttributes);
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
/*     */   public int getLength() {
/*  93 */     return this.length;
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
/*     */   public String getURI(int paramInt) {
/* 107 */     if (paramInt >= 0 && paramInt < this.length) {
/* 108 */       return this.data[paramInt * 5];
/*     */     }
/* 110 */     return null;
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
/*     */   public String getLocalName(int paramInt) {
/* 125 */     if (paramInt >= 0 && paramInt < this.length) {
/* 126 */       return this.data[paramInt * 5 + 1];
/*     */     }
/* 128 */     return null;
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
/*     */   public String getQName(int paramInt) {
/* 143 */     if (paramInt >= 0 && paramInt < this.length) {
/* 144 */       return this.data[paramInt * 5 + 2];
/*     */     }
/* 146 */     return null;
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
/*     */   public String getType(int paramInt) {
/* 161 */     if (paramInt >= 0 && paramInt < this.length) {
/* 162 */       return this.data[paramInt * 5 + 3];
/*     */     }
/* 164 */     return null;
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
/*     */   public String getValue(int paramInt) {
/* 178 */     if (paramInt >= 0 && paramInt < this.length) {
/* 179 */       return this.data[paramInt * 5 + 4];
/*     */     }
/* 181 */     return null;
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
/*     */ 
/*     */   
/*     */   public int getIndex(String paramString1, String paramString2) {
/* 201 */     int i = this.length * 5;
/* 202 */     for (byte b = 0; b < i; b += 5) {
/* 203 */       if (this.data[b].equals(paramString1) && this.data[b + 1].equals(paramString2)) {
/* 204 */         return b / 5;
/*     */       }
/*     */     } 
/* 207 */     return -1;
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
/*     */   public int getIndex(String paramString) {
/* 220 */     int i = this.length * 5;
/* 221 */     for (byte b = 0; b < i; b += 5) {
/* 222 */       if (this.data[b + 2].equals(paramString)) {
/* 223 */         return b / 5;
/*     */       }
/*     */     } 
/* 226 */     return -1;
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
/*     */   public String getType(String paramString1, String paramString2) {
/* 242 */     int i = this.length * 5;
/* 243 */     for (byte b = 0; b < i; b += 5) {
/* 244 */       if (this.data[b].equals(paramString1) && this.data[b + 1].equals(paramString2)) {
/* 245 */         return this.data[b + 3];
/*     */       }
/*     */     } 
/* 248 */     return null;
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
/*     */   public String getType(String paramString) {
/* 262 */     int i = this.length * 5;
/* 263 */     for (byte b = 0; b < i; b += 5) {
/* 264 */       if (this.data[b + 2].equals(paramString)) {
/* 265 */         return this.data[b + 3];
/*     */       }
/*     */     } 
/* 268 */     return null;
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
/*     */   public String getValue(String paramString1, String paramString2) {
/* 284 */     int i = this.length * 5;
/* 285 */     for (byte b = 0; b < i; b += 5) {
/* 286 */       if (this.data[b].equals(paramString1) && this.data[b + 1].equals(paramString2)) {
/* 287 */         return this.data[b + 4];
/*     */       }
/*     */     } 
/* 290 */     return null;
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
/*     */   public String getValue(String paramString) {
/* 304 */     int i = this.length * 5;
/* 305 */     for (byte b = 0; b < i; b += 5) {
/* 306 */       if (this.data[b + 2].equals(paramString)) {
/* 307 */         return this.data[b + 4];
/*     */       }
/*     */     } 
/* 310 */     return null;
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
/*     */   
/*     */   public void clear() {
/* 329 */     if (this.data != null)
/* 330 */       for (byte b = 0; b < this.length * 5; b++) {
/* 331 */         this.data[b] = null;
/*     */       } 
/* 333 */     this.length = 0;
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
/*     */   public void setAttributes(Attributes paramAttributes) {
/* 347 */     clear();
/* 348 */     this.length = paramAttributes.getLength();
/* 349 */     if (this.length > 0) {
/* 350 */       this.data = new String[this.length * 5];
/* 351 */       for (byte b = 0; b < this.length; b++) {
/* 352 */         this.data[b * 5] = paramAttributes.getURI(b);
/* 353 */         this.data[b * 5 + 1] = paramAttributes.getLocalName(b);
/* 354 */         this.data[b * 5 + 2] = paramAttributes.getQName(b);
/* 355 */         this.data[b * 5 + 3] = paramAttributes.getType(b);
/* 356 */         this.data[b * 5 + 4] = paramAttributes.getValue(b);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAttribute(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 382 */     ensureCapacity(this.length + 1);
/* 383 */     this.data[this.length * 5] = paramString1;
/* 384 */     this.data[this.length * 5 + 1] = paramString2;
/* 385 */     this.data[this.length * 5 + 2] = paramString3;
/* 386 */     this.data[this.length * 5 + 3] = paramString4;
/* 387 */     this.data[this.length * 5 + 4] = paramString5;
/* 388 */     this.length++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 416 */     if (paramInt >= 0 && paramInt < this.length) {
/* 417 */       this.data[paramInt * 5] = paramString1;
/* 418 */       this.data[paramInt * 5 + 1] = paramString2;
/* 419 */       this.data[paramInt * 5 + 2] = paramString3;
/* 420 */       this.data[paramInt * 5 + 3] = paramString4;
/* 421 */       this.data[paramInt * 5 + 4] = paramString5;
/*     */     } else {
/* 423 */       badIndex(paramInt);
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
/*     */ 
/*     */   
/*     */   public void removeAttribute(int paramInt) {
/* 438 */     if (paramInt >= 0 && paramInt < this.length) {
/* 439 */       if (paramInt < this.length - 1) {
/* 440 */         System.arraycopy(this.data, (paramInt + 1) * 5, this.data, paramInt * 5, (this.length - paramInt - 1) * 5);
/*     */       }
/*     */       
/* 443 */       paramInt = (this.length - 1) * 5;
/* 444 */       this.data[paramInt++] = null;
/* 445 */       this.data[paramInt++] = null;
/* 446 */       this.data[paramInt++] = null;
/* 447 */       this.data[paramInt++] = null;
/* 448 */       this.data[paramInt] = null;
/* 449 */       this.length--;
/*     */     } else {
/* 451 */       badIndex(paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURI(int paramInt, String paramString) {
/* 468 */     if (paramInt >= 0 && paramInt < this.length) {
/* 469 */       this.data[paramInt * 5] = paramString;
/*     */     } else {
/* 471 */       badIndex(paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalName(int paramInt, String paramString) {
/* 488 */     if (paramInt >= 0 && paramInt < this.length) {
/* 489 */       this.data[paramInt * 5 + 1] = paramString;
/*     */     } else {
/* 491 */       badIndex(paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQName(int paramInt, String paramString) {
/* 508 */     if (paramInt >= 0 && paramInt < this.length) {
/* 509 */       this.data[paramInt * 5 + 2] = paramString;
/*     */     } else {
/* 511 */       badIndex(paramInt);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setType(int paramInt, String paramString) {
/* 527 */     if (paramInt >= 0 && paramInt < this.length) {
/* 528 */       this.data[paramInt * 5 + 3] = paramString;
/*     */     } else {
/* 530 */       badIndex(paramInt);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(int paramInt, String paramString) {
/* 546 */     if (paramInt >= 0 && paramInt < this.length) {
/* 547 */       this.data[paramInt * 5 + 4] = paramString;
/*     */     } else {
/* 549 */       badIndex(paramInt);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureCapacity(int paramInt) {
/*     */     int i;
/* 567 */     if (paramInt <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 571 */     if (this.data == null || this.data.length == 0) {
/* 572 */       i = 25;
/*     */     } else {
/* 574 */       if (this.data.length >= paramInt * 5) {
/*     */         return;
/*     */       }
/*     */       
/* 578 */       i = this.data.length;
/*     */     } 
/* 580 */     while (i < paramInt * 5) {
/* 581 */       i *= 2;
/*     */     }
/*     */     
/* 584 */     String[] arrayOfString = new String[i];
/* 585 */     if (this.length > 0) {
/* 586 */       System.arraycopy(this.data, 0, arrayOfString, 0, this.length * 5);
/*     */     }
/* 588 */     this.data = arrayOfString;
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
/*     */   private void badIndex(int paramInt) throws ArrayIndexOutOfBoundsException {
/* 601 */     String str = "Attempt to modify attribute at illegal index: " + paramInt;
/*     */     
/* 603 */     throw new ArrayIndexOutOfBoundsException(str);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\xml\sax\helpers\AttributesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */