/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Image;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.WindowResizePoint;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IImage;
/*     */ import com.ankamagames.xulor.template.IPixmapable;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XWindowResizePoint
/*     */   extends XImage
/*     */   implements IImage, IPixmapable
/*     */ {
/*     */   public static final String TAG = "windowResizePoint";
/*  23 */   private WindowResizePoint m_wrp = null;
/*     */   
/*  25 */   private Alignment m_pointAlign = Alignment.NORTH_WEST;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  34 */     if (this.m_wrp == null) {
/*  35 */       this.m_wrp = new WindowResizePoint(this.m_pointAlign);
/*     */       
/*  37 */       applyAllAttributes();
/*     */       
/*  39 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  40 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_wrp, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  43 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement component = arrayOfIElement[b];
/*  44 */       component.buildGUI(); b++; }
/*     */     
/*  46 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WindowResizePoint getWidget() {
/*  56 */     return this.m_wrp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Alignment getPointAlign() {
/*  63 */     return this.m_pointAlign;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPointAlign(Alignment alignment) {
/*  72 */     this.m_pointAlign = alignment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  81 */     return "windowResizePoint";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/*  90 */     XWindowResizePoint elem = (XWindowResizePoint)element;
/*  91 */     elem.m_pointAlign = this.m_pointAlign;
/*  92 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 101 */     XWindowResizePoint elem = new XWindowResizePoint();
/* 102 */     copyElementData((IElement)elem);
/* 103 */     return (IElement)elem;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XWindowResizePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */