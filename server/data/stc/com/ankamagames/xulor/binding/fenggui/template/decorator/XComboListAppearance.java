/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeComboListAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.IAppearance;
/*     */ import org.fenggui.List;
/*     */ import org.fenggui.ListAppearance;
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
/*     */ public class XComboListAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "ComboListAppearance";
/*  29 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IAppearance getAppearance()
/*     */   {
/*  36 */     return null;
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
/*  69 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  76 */     return "ComboListAppearance";
/*     */   }
/*     */   
/*     */   public static void setAppearance(List list, ThemeComboListAppearance theme) {
/*  80 */     if ((theme == null) || (list == null)) {
/*  81 */       return;
/*     */     }
/*     */     
/*  84 */     ListAppearance app = list.getAppearance();
/*  85 */     if (theme.getFont() != null) app.setFont(FengguiConstant.toFengguiFont(theme.getFont()));
/*  86 */     if (theme.getColor() != null) app.setTextColor(FengguiConstant.toFengguiColor(theme.getColor()));
/*  87 */     if (theme.getAlignment() != null) app.setAlignment(FengguiConstant.toFengguiAlignment(theme.getAlignment()));
/*     */   }
/*     */   
/*     */   public static void applyComboListTheme(List list, ThemeElement element)
/*     */   {
/*  92 */     if ((list == null) || (element == null)) {
/*  93 */       return;
/*     */     }
/*     */     
/*  96 */     list.getAppearance().removeAll();
/*  97 */     XComponent.applyThemeAttributes(list, element.getAttributes());
/*  98 */     XSpacingAppearance.setAppearance(list, element);
/*  99 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 100 */     for (ThemeAppearance app : appearances) {
/* 101 */       if (app != null) {
/* 102 */         XDecoratorAppearance.setAppearance(list, app);
/* 103 */         if ((app instanceof ThemeComboListAppearance)) {
/* 104 */           setAppearance(list, (ThemeComboListAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XComboListAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */