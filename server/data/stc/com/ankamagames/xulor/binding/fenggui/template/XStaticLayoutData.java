/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.component.StaticLayoutPlusData;
/*     */ import com.ankamagames.xulor.template.IElement;
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
/*     */ 
/*     */ 
/*     */ public class XStaticLayoutData
/*     */   extends XLayoutData
/*     */ {
/*     */   public static final String TAG = "StaticLayoutData";
/*     */   public static final String SHORT_TAG = "sld";
/*  25 */   private StaticLayoutPlusData m_sld = null;
/*     */   
/*     */ 
/*  28 */   private Alignment m_align = null;
/*     */   
/*     */   private int m_x;
/*     */   
/*     */   private int m_y;
/*     */   
/*  34 */   private Dimension m_size = null;
/*  35 */   private boolean m_resizeOnce = false;
/*  36 */   private boolean m_xInit = false; private boolean m_yInit = false;
/*     */   
/*  38 */   private boolean m_alreadyBuilt = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ILayoutData getLayoutData()
/*     */   {
/*  45 */     return this.m_sld;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  52 */     if (!this.m_alreadyBuilt) {
/*  53 */       this.m_sld = new StaticLayoutPlusData();
/*     */       
/*  55 */       applyAllAttributes();
/*     */       
/*  57 */       this.m_alreadyBuilt = true;
/*  58 */       if (this.m_parent != null) ((XComponent)this.m_parent).setLayoutData(this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  61 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  62 */       c.buildGUI();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*     */     IElement[] arrayOfIElement;
/*  70 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  71 */       c.buildXML();
/*     */     }
/*     */   }
/*     */   
/*     */   public void applyAllAttributes()
/*     */   {
/*  77 */     if (this.m_sld != null) {
/*  78 */       if (this.m_xInit) this.m_sld.setX(this.m_x);
/*  79 */       if (this.m_yInit) this.m_sld.setY(this.m_y);
/*  80 */       if (this.m_align != null) this.m_sld.setAlignment(this.m_align);
/*  81 */       if (this.m_size != null) this.m_sld.setDimension(this.m_size);
/*  82 */       this.m_sld.setResizeOnce(this.m_resizeOnce);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Dimension getSize()
/*     */   {
/*  91 */     return this.m_size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSize(Dimension dimension)
/*     */   {
/*  98 */     this.m_size = dimension;
/*  99 */     if (this.m_sld != null) {
/* 100 */       this.m_sld.setDimension(this.m_size);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getX()
/*     */   {
/* 108 */     return this.m_x;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setX(int x)
/*     */   {
/* 115 */     this.m_x = x;
/* 116 */     this.m_xInit = true;
/* 117 */     if (this.m_sld != null) {
/* 118 */       this.m_sld.setX(x);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getY()
/*     */   {
/* 126 */     return this.m_y;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setY(int y)
/*     */   {
/* 133 */     this.m_y = y;
/* 134 */     this.m_yInit = true;
/* 135 */     if (this.m_sld != null) {
/* 136 */       this.m_sld.setY(y);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Alignment getAlign()
/*     */   {
/* 144 */     return this.m_align;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setAlign(Alignment align)
/*     */   {
/* 151 */     this.m_align = align;
/* 152 */     if (this.m_sld != null) {
/* 153 */       this.m_sld.setAlignment(this.m_align);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isResizeOnce() {
/* 158 */     return this.m_resizeOnce;
/*     */   }
/*     */   
/*     */   public void setResizeOnce(boolean resizeOnce) {
/* 162 */     this.m_resizeOnce = resizeOnce;
/* 163 */     if (this.m_sld != null) {
/* 164 */       this.m_sld.setResizeOnce(resizeOnce);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 173 */     return "StaticLayoutData";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 181 */     XStaticLayoutData elem = (XStaticLayoutData)element;
/* 182 */     elem.m_x = this.m_x;
/* 183 */     elem.m_y = this.m_y;
/* 184 */     elem.m_xInit = this.m_xInit;
/* 185 */     elem.m_yInit = this.m_yInit;
/* 186 */     elem.setAlign(this.m_align);
/* 187 */     elem.setSize(this.m_size);
/* 188 */     elem.setResizeOnce(this.m_resizeOnce);
/* 189 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 196 */     XStaticLayoutData elem = new XStaticLayoutData();
/* 197 */     copyElementData(elem);
/* 198 */     return elem;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XStaticLayoutData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */