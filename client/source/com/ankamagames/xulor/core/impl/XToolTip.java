/*     */ package com.ankamagames.xulor.core.impl;
/*     */ 
/*     */ import com.ankamagames.graphics.isometric.text.BackgroundedText;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Color;
/*     */ import com.ankamagames.xulor.util.ToolTipAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XToolTip
/*     */   extends XElement
/*     */ {
/*     */   public static final String TAG = "ToolTip";
/*  22 */   private ToolTipAttributes m_attributes = new ToolTipAttributes();
/*     */   
/*     */   public ToolTipAttributes getToolTipAttributes() {
/*  25 */     return this.m_attributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getEncapsulatedObject() {
/*  33 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/*  56 */     this.m_attributes.TEXT = text;
/*     */   }
/*     */   
/*     */   public void setXOffset(int x) {
/*  60 */     this.m_attributes.X_OFFSET = Integer.valueOf(x);
/*     */   }
/*     */   
/*     */   public void setYOffset(int y) {
/*  64 */     this.m_attributes.Y_OFFSET = Integer.valueOf(y);
/*     */   }
/*     */   
/*     */   public void setMaxWidth(int max) {
/*  68 */     this.m_attributes.MAX_WIDTH = Integer.valueOf(max);
/*     */   }
/*     */   
/*     */   public void setDuration(float duration) {
/*  72 */     this.m_attributes.DURATION = Integer.valueOf((int)duration * 1000);
/*     */   }
/*     */   
/*     */   public void setTextColor(Color color) {
/*  76 */     this.m_attributes.TEXT_COLOR = color;
/*     */   }
/*     */   
/*     */   public void setBackgroundColor(Color color) {
/*  80 */     this.m_attributes.BACKGROUND_COLOR = color;
/*     */   }
/*     */   
/*     */   public void setBorderColor(Color color) {
/*  84 */     this.m_attributes.BORDER_COLOR = color;
/*     */   }
/*     */   
/*     */   public void setPosition(Alignment alignment) {
/*  88 */     this.m_attributes.POSITION = alignment;
/*     */   }
/*     */   
/*     */   public void setHotPointPosition(BackgroundedText.BackgroundedTextHotPointPosition position) {
/*  92 */     this.m_attributes.HOT_POINT_POSITION = position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 100 */     XToolTip tt = new XToolTip();
/* 101 */     tt.setBackgroundColor(this.m_attributes.BACKGROUND_COLOR);
/* 102 */     tt.setBorderColor(this.m_attributes.BORDER_COLOR);
/* 103 */     tt.setTextColor(this.m_attributes.TEXT_COLOR);
/* 104 */     if (this.m_attributes.DURATION != null) tt.setDuration(this.m_attributes.DURATION.intValue()); 
/* 105 */     tt.setText(this.m_attributes.TEXT);
/* 106 */     tt.setPosition(this.m_attributes.POSITION);
/* 107 */     tt.setHotPointPosition(this.m_attributes.HOT_POINT_POSITION);
/* 108 */     if (this.m_attributes.X_OFFSET != null) tt.setXOffset(this.m_attributes.X_OFFSET.intValue()); 
/* 109 */     if (this.m_attributes.Y_OFFSET != null) tt.setYOffset(this.m_attributes.Y_OFFSET.intValue()); 
/* 110 */     if (this.m_attributes.MAX_WIDTH != null) tt.setMaxWidth(this.m_attributes.MAX_WIDTH.intValue()); 
/* 111 */     copyElementData(tt);
/* 112 */     return tt;
/*     */   }
/*     */   
/*     */   public String getTag() {
/* 116 */     return "ToolTip";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\impl\XToolTip.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */