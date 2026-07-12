/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Popup;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IPopup;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XPopup
/*     */   extends XContainer
/*     */   implements IPopup
/*     */ {
/*     */   public static final String TAG = "popup";
/*  22 */   private Popup m_popup = null;
/*     */   
/*  24 */   private Alignment m_hotSpotPosition = null;
/*  25 */   private Alignment m_align = null;
/*  26 */   private boolean m_horizontal = false;
/*  27 */   private boolean m_horizontalInit = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  35 */     if (this.m_popup == null) {
/*  36 */       this.m_popup = new Popup();
/*     */       
/*  38 */       applyAllAttributes();
/*     */       
/*  40 */       IElement elem = getParent();
/*  41 */       while ((elem != null) && (!(elem instanceof XComponent))) {
/*  42 */         elem = elem.getParent();
/*     */       }
/*  44 */       if (elem != null) {
/*  45 */         this.m_popup.setClientWidget(((XComponent)elem).getWidget());
/*     */       }
/*     */       
/*  48 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_popup, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  51 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  52 */       c.buildGUI();
/*     */     }
/*  54 */     applyTheme();
/*     */   }
/*     */   
/*     */   public void addWidget(IElement element) {
/*  58 */     super.addWidget(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  67 */     super.applyAllAttributes();
/*  68 */     if (this.m_popup != null) {
/*  69 */       if (this.m_hotSpotPosition != null) this.m_popup.setHotSpotPosition(this.m_hotSpotPosition);
/*  70 */       if (this.m_horizontalInit) this.m_popup.setHorizontal(this.m_horizontal);
/*  71 */       if (this.m_align != null) { this.m_popup.setAlign(this.m_align);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void show()
/*     */   {
/*  80 */     if (this.m_popup != null) {
/*  81 */       this.m_popup.show();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void hide()
/*     */   {
/*  90 */     if (this.m_popup != null) {
/*  91 */       this.m_popup.hide();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isShow() {
/*  96 */     return this.m_popup.isShow();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getAlign()
/*     */   {
/* 103 */     return this.m_align;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAlign(Alignment align)
/*     */   {
/* 110 */     this.m_align = align;
/* 111 */     if (this.m_popup != null) {
/* 112 */       this.m_popup.setAlign(this.m_align);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isHorizontal()
/*     */   {
/* 120 */     return this.m_horizontal;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setHorizontal(boolean horizontal)
/*     */   {
/* 127 */     this.m_horizontal = horizontal;
/* 128 */     this.m_horizontalInit = true;
/* 129 */     if (this.m_popup != null) {
/* 130 */       this.m_popup.setHorizontal(this.m_horizontal);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getHotSpotPosition()
/*     */   {
/* 138 */     return this.m_hotSpotPosition;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setHotSpotPosition(Alignment hotSpotPosition)
/*     */   {
/* 145 */     this.m_hotSpotPosition = hotSpotPosition;
/* 146 */     if (this.m_popup != null) {
/* 147 */       this.m_popup.setHotSpotPosition(this.m_hotSpotPosition);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 156 */     XPopup popup = new XPopup();
/* 157 */     copyElementData(popup);
/* 158 */     return popup;
/*     */   }
/*     */   
/*     */   protected void copyElementData(XPopup popup) {
/* 162 */     super.copyElementData(popup);
/* 163 */     if (this.m_align != null) popup.setAlign(this.m_align);
/* 164 */     if (this.m_hotSpotPosition != null) popup.setHotSpotPosition(this.m_hotSpotPosition);
/* 165 */     if (this.m_horizontalInit) { popup.setHorizontal(this.m_horizontal);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Popup getWidget()
/*     */   {
/* 173 */     return this.m_popup;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 180 */     return "popup";
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XPopup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */