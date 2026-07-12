/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.decorator.IDecorator;
/*     */ import com.ankamagames.xulor.theme.IThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeBackground;
/*     */ import com.ankamagames.xulor.util.Span;
/*     */ import org.fenggui.background.Background;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XBackground
/*     */   extends XElement
/*     */   implements IDecorator
/*     */ {
/*  25 */   protected String m_state = "default";
/*     */ 
/*     */   
/*     */   protected boolean m_enabled = true;
/*     */   
/*     */   protected Span m_span;
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  34 */     return this.m_enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/*  41 */     this.m_enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getState() {
/*  48 */     return this.m_state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(String label) {
/*  55 */     this.m_state = label;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Span getSpan() {
/*  63 */     return this.m_span;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpan(Span span) {
/*  71 */     this.m_span = span;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyBackgroundAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Background getBackground();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/*  93 */     return getBackground();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 101 */     XBackground elem = (XBackground)element;
/* 102 */     elem.setSpan(this.m_span);
/* 103 */     elem.setEnabled(this.m_enabled);
/* 104 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */   public abstract ThemeBackground toThemeBackground();
/*     */   
/*     */   public IThemeElement toThemeElement() {
/* 110 */     return (IThemeElement)toThemeBackground();
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */