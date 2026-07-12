/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.AbstractDescriptorLibrary;
/*     */ import com.ankamagames.framework.graphics.animation.descriptors.library.ModifiableDescriptorLibrary;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.DisplayObjectViewer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDisplayObjectViewerAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.template.IDisplayObjectViewer;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeDisplayObjectViewerAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XDisplayObjectViewer
/*     */   extends XSceneCanvas
/*     */   implements IDisplayObjectViewer
/*     */ {
/*     */   public static final String TAG = "DisplayObjectViewer";
/*  32 */   private DisplayObjectViewer m_displayObjectViewer = null;
/*     */   
/*  34 */   private ModifiableDescriptorLibrary m_descriptorLibrary = null;
/*  35 */   private String m_linkage = null;
/*     */   private int m_xOffset;
/*     */   private int m_yOffset;
/*  38 */   private float m_scale = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {
/*  46 */     System.out.println("<displayObjectPreview>"); byte b; int i; IElement[] arrayOfIElement;
/*  47 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  48 */       c.buildXML(); b++; }
/*     */     
/*  50 */     System.out.println("</displayObjectPreview>");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  60 */     if (this.m_displayObjectViewer == null) {
/*  61 */       this.m_displayObjectViewer = new DisplayObjectViewer();
/*     */       
/*  63 */       applyAllAttributes();
/*     */       
/*  65 */       if (this.m_parent != null)
/*  66 */         this.m_parent.addWidget((IElement)this); 
/*  67 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_displayObjectViewer, (IElement)this);
/*     */     }  byte b; int i;
/*     */     IElement[] arrayOfIElement;
/*  70 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  71 */       c.buildGUI();
/*     */       b++; }
/*     */     
/*  74 */     applyTheme();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyAllAttributes() {
/*  84 */     if (this.m_displayObjectViewer == null) {
/*     */       return;
/*     */     }
/*  87 */     applyComponentAttributes();
/*  88 */     applySceneCanvasAttributes();
/*     */     
/*  90 */     this.m_displayObjectViewer.setDescriptorLibrary(this.m_descriptorLibrary);
/*  91 */     this.m_displayObjectViewer.setLinkage(this.m_linkage);
/*  92 */     this.m_displayObjectViewer.setXOffset(this.m_xOffset);
/*  93 */     this.m_displayObjectViewer.setYOffset(this.m_yOffset);
/*  94 */     this.m_displayObjectViewer.setScale(this.m_scale);
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  98 */     if (this.m_themeNeedToBeApplied) {
/*  99 */       this.m_themeNeedToBeApplied = false;
/* 100 */       applyDisplayObjectViewerTheme(this.m_displayObjectViewer, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 111 */     return (Widget)this.m_displayObjectViewer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractDescriptorLibrary getDescriptorLibrary() {
/* 120 */     if (this.m_displayObjectViewer != null) {
/* 121 */       return (AbstractDescriptorLibrary)this.m_displayObjectViewer.getDescriptorLibrary();
/*     */     }
/* 123 */     return (AbstractDescriptorLibrary)this.m_descriptorLibrary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDescriptorLibrary(ModifiableDescriptorLibrary descriptorLibrary) {
/* 132 */     if (this.m_displayObjectViewer != null) {
/* 133 */       this.m_displayObjectViewer.setDescriptorLibrary(descriptorLibrary);
/*     */     }
/* 135 */     this.m_descriptorLibrary = descriptorLibrary;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLinkage() {
/* 144 */     if (this.m_displayObjectViewer != null) {
/* 145 */       return this.m_displayObjectViewer.getLinkage();
/*     */     }
/* 147 */     return this.m_linkage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLinkage(String linkage) {
/* 156 */     if (this.m_displayObjectViewer != null) {
/* 157 */       this.m_displayObjectViewer.setLinkage(linkage);
/*     */     }
/* 159 */     this.m_linkage = linkage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getXOffset() {
/* 168 */     if (this.m_displayObjectViewer != null) {
/* 169 */       return this.m_displayObjectViewer.getXOffset();
/*     */     }
/* 171 */     return this.m_xOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXOffset(int offset) {
/* 180 */     if (this.m_displayObjectViewer != null) {
/* 181 */       this.m_displayObjectViewer.setXOffset(offset);
/*     */     }
/* 183 */     this.m_xOffset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYOffset() {
/* 192 */     if (this.m_displayObjectViewer != null) {
/* 193 */       return this.m_displayObjectViewer.getYOffset();
/*     */     }
/* 195 */     return this.m_yOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYOffset(int offset) {
/* 204 */     if (this.m_displayObjectViewer != null) {
/* 205 */       this.m_displayObjectViewer.setYOffset(offset);
/*     */     }
/* 207 */     this.m_yOffset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScale() {
/* 216 */     if (this.m_displayObjectViewer != null) {
/* 217 */       return this.m_displayObjectViewer.getScale();
/*     */     }
/* 219 */     return this.m_scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setScale(float scale) {
/* 228 */     if (this.m_displayObjectViewer != null) {
/* 229 */       this.m_displayObjectViewer.setScale(scale);
/*     */     }
/* 231 */     this.m_scale = scale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 240 */     XDisplayObjectViewer elem = (XDisplayObjectViewer)element;
/* 241 */     elem.m_descriptorLibrary = this.m_descriptorLibrary;
/* 242 */     elem.m_linkage = this.m_linkage;
/* 243 */     elem.m_xOffset = this.m_xOffset;
/* 244 */     elem.m_yOffset = this.m_yOffset;
/* 245 */     elem.m_scale = this.m_scale;
/* 246 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 255 */     XDisplayObjectViewer elem = new XDisplayObjectViewer();
/* 256 */     copyElementData((IElement)elem);
/* 257 */     return (IElement)elem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 266 */     return "DisplayObjectViewer";
/*     */   }
/*     */   
/*     */   public static void applyDisplayObjectViewerTheme(DisplayObjectViewer dov, ThemeElement element) {
/* 270 */     if (dov == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 274 */     dov.getAppearance().removeAll();
/* 275 */     XComponent.applyThemeAttributes((Widget)dov, element.getAttributes());
/* 276 */     XSpacingAppearance.setAppearance((StandardWidget)dov, element);
/* 277 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 278 */     for (ThemeAppearance app : appearances) {
/* 279 */       if (app != null) {
/* 280 */         XDecoratorAppearance.setAppearance((StandardWidget)dov, app);
/* 281 */         if (app instanceof ThemeDisplayObjectViewerAppearance)
/* 282 */           XDisplayObjectViewerAppearance.setAppearance((StandardWidget)dov, (ThemeDisplayObjectViewerAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XDisplayObjectViewer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */