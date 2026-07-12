/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Dimension;
/*     */ import org.fenggui.layout.ILayoutData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaticLayoutPlusData
/*     */   implements ILayoutData
/*     */ {
/*     */   private Dimension m_dimension;
/*     */   private Alignment m_alignment;
/*  21 */   private int m_x = 0; private int m_y = 0;
/*     */   
/*     */   private boolean m_xInit = false, m_yInit = false;
/*     */   
/*     */   private boolean m_resizeOnce = false;
/*     */   
/*     */   public int getX() {
/*  28 */     return this.m_x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(int x) {
/*  34 */     this.m_xInit = true;
/*  35 */     this.m_x = x;
/*     */   }
/*     */   
/*     */   public boolean isXInit() {
/*  39 */     return this.m_xInit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  46 */     return this.m_y;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(int y) {
/*  52 */     this.m_yInit = true;
/*  53 */     this.m_y = y;
/*     */   }
/*     */   
/*     */   public boolean isYInit() {
/*  57 */     return this.m_yInit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getDimension() {
/*  64 */     return this.m_dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDimension(Dimension dimension) {
/*  70 */     this.m_dimension = dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Alignment getAlignment() {
/*  76 */     return this.m_alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlignment(Alignment position) {
/*  82 */     this.m_alignment = position;
/*     */   }
/*     */   
/*     */   public boolean isResizeOnce() {
/*  86 */     return this.m_resizeOnce;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResizeOnce(boolean resizeOnce) {
/*  94 */     this.m_resizeOnce = resizeOnce;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resized() {
/* 103 */     if (this.m_resizeOnce) {
/* 104 */       this.m_resizeOnce = false;
/* 105 */       this.m_dimension = new Dimension(-1, -1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\StaticLayoutPlusData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */