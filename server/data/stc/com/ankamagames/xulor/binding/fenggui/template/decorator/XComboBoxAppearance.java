/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.RenderableContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XButton;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XFengguiList;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XLabel;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XRenderableContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XScrollContainer;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeComboBoxAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.Label;
/*     */ import org.fenggui.ScrollContainer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XComboBoxAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "ComboBoxAppearance";
/*  38 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IAppearance getAppearance()
/*     */   {
/*  45 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */ 
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
/*     */ 
/*     */ 
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/*  78 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  85 */     return "ComboBoxAppearance";
/*     */   }
/*     */   
/*     */   public static void setAppearance(org.fenggui.ComboBox comboBox, ThemeComboBoxAppearance theme) {
/*  89 */     if ((comboBox == null) || (theme == null)) {
/*  90 */       return;
/*     */     }
/*     */     
/*  93 */     Pixmap pixmap = theme.getPixmap();
/*  94 */     if (pixmap != null) { comboBox.setPixmap(FengguiConstant.toFengguiPixmap(pixmap));
/*     */     }
/*  96 */     ThemeElement labelElem = theme.getThemeElement("label");
/*  97 */     Label label = comboBox.getLabel();
/*  98 */     XLabel.applyLabelTheme(label, labelElem);
/*     */     
/* 100 */     ThemeElement scrollContainerElem = theme.getThemeElement("scrollContainer");
/* 101 */     ScrollContainer scrollContainer = comboBox.getPopupContainer();
/* 102 */     XScrollContainer.applyScrollContainerTheme(scrollContainer, scrollContainerElem);
/*     */     
/* 104 */     ThemeElement comboListElem = theme.getThemeElement("list");
/* 105 */     org.fenggui.List list = comboBox.getList();
/* 106 */     XComboListAppearance.applyComboListTheme(list, comboListElem);
/*     */   }
/*     */   
/*     */   public static void setAppearance(com.ankamagames.xulor.binding.fenggui.component.ComboBox comboBox, ThemeComboBoxAppearance theme)
/*     */   {
/* 111 */     if ((comboBox == null) || (theme == null)) {
/* 112 */       return;
/*     */     }
/*     */     
/* 115 */     ThemeElement buttonElem = theme.getThemeElement("button");
/* 116 */     if (buttonElem != null) {
/* 117 */       Button button = comboBox.getButton();
/* 118 */       XButton.applyButtonTheme(button, buttonElem);
/*     */     }
/*     */     
/* 121 */     ThemeElement listElem = theme.getThemeElement("list");
/* 122 */     if (listElem != null) {
/* 123 */       com.ankamagames.xulor.binding.fenggui.component.List list = comboBox.getList();
/* 124 */       XFengguiList.applyListTheme(list, listElem);
/*     */     }
/*     */     
/* 127 */     ThemeElement renderableElem = theme.getThemeElement("renderable");
/* 128 */     if (renderableElem != null) {
/* 129 */       RenderableContainer renderable = comboBox.getRenderable();
/* 130 */       XRenderableContainer.applyContainerTheme(renderable, renderableElem);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XComboBoxAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */