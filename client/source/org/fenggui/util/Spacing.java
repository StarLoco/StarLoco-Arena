/*     */ package org.fenggui.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.DefaultElementName;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DefaultElementName("Spacing")
/*     */ public class Spacing
/*     */   implements IOStreamSaveable
/*     */ {
/*     */   private int top;
/*     */   private int left;
/*     */   private int right;
/*     */   private int bottom;
/*     */   
/*     */   public Spacing(InputOnlyStream stream) throws IOException, IOStreamException {
/*  59 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spacing() {
/*  68 */     this(0, 0, 0, 0);
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
/*     */   public Spacing(int topbottom, int leftright) {
/*  83 */     this(topbottom, leftright, leftright, topbottom);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkIntegrity(int value) {
/*  88 */     if (value < 0) throw new IllegalArgumentException("spacing parameter < 0");
/*     */   
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
/*     */   public Spacing(int top, int left, int right, int bottom) {
/* 105 */     checkIntegrity(top);
/* 106 */     checkIntegrity(left);
/* 107 */     checkIntegrity(right);
/* 108 */     checkIntegrity(bottom);
/*     */     
/* 110 */     this.top = top;
/* 111 */     this.left = left;
/* 112 */     this.right = right;
/* 113 */     this.bottom = bottom;
/*     */   }
/*     */ 
/*     */   
/*     */   public Spacing(Spacing c) {
/* 118 */     setSpacing(c);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTop() {
/* 128 */     return this.top;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeft() {
/* 138 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRight() {
/* 148 */     return this.right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBottom() {
/* 158 */     return this.bottom;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLeftPlusRight() {
/* 163 */     return this.left + this.right;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBottomPlusTop() {
/* 168 */     return this.bottom + this.top;
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
/*     */   protected void setSpacing(int top, int left, int right, int bottom) {
/* 181 */     this.top = top;
/* 182 */     this.left = left;
/* 183 */     this.right = right;
/* 184 */     this.bottom = bottom;
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
/*     */   protected void setSpacing(int topbottom, int leftright) {
/* 203 */     this.left = this.right = leftright;
/* 204 */     this.bottom = this.top = topbottom;
/*     */   }
/*     */   
/*     */   protected void setSpacing(Spacing s) {
/* 208 */     this.left = s.left;
/* 209 */     this.right = s.right;
/* 210 */     this.top = s.top;
/* 211 */     this.bottom = s.bottom;
/*     */   }
/*     */   
/* 214 */   public static final Spacing ZERO_SPACING = new Spacing(0, 0, 0, 0);
/*     */   
/*     */   public String toString() {
/* 217 */     return "(l: " + this.left + ", r: " + this.right + ", t: " + this.top + ", b: " + this.bottom + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 225 */     if (!stream.isInputStream() && this.top == this.left && this.left == this.right && this.right == this.bottom) {
/*     */       
/* 227 */       stream.processAttribute("all", this.left, 0);
/*     */       
/*     */       return;
/*     */     } 
/* 231 */     this.top = this.left = this.right = this.bottom = stream.processAttribute("all", this.left, 0);
/*     */ 
/*     */     
/* 234 */     if (this.top != 0)
/*     */       return; 
/* 236 */     this.top = stream.processAttribute("top", this.top, 0);
/* 237 */     this.bottom = stream.processAttribute("bottom", this.bottom, 0);
/* 238 */     this.left = stream.processAttribute("left", this.left, 0);
/* 239 */     this.right = stream.processAttribute("right", this.right, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUniqueName() {
/* 246 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggu\\util\Spacing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */