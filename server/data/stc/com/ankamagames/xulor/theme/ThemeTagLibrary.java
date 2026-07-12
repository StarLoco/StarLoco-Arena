/*    */ package com.ankamagames.xulor.theme;
/*    */ 
/*    */ import com.ankamagames.xulor.core.DefaultFactory;
/*    */ import com.ankamagames.xulor.core.TagLibrary;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ThemeTagLibrary
/*    */   extends TagLibrary
/*    */ {
/* 17 */   private static ThemeTagLibrary INSTANCE = new ThemeTagLibrary();
/*    */   
/* 19 */   private static Logger m_logger = Logger.getLogger(TagLibrary.class);
/*    */   
/*    */ 
/*    */ 
/*    */   public static ThemeTagLibrary getInstance()
/*    */   {
/* 25 */     return INSTANCE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void registerTag(String name, Class template)
/*    */   {
/* 38 */     registerTag(name, new DefaultFactory(template, ThemeConverterLibrary.getInstance()));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   protected void registerTags()
/*    */   {
/* 45 */     registerTag("Appearance", ThemeAppearance.class);
/* 46 */     registerTag("Attributes", ThemeAttributes.class);
/* 47 */     registerTag("BevelBorder", ThemeBevelBorder.class);
/* 48 */     registerTag("Color", ThemeColor.class);
/* 49 */     registerTag("ComboBoxAppearance", ThemeComboBoxAppearance.class);
/* 50 */     registerTag("ComboListAppearance", ThemeComboListAppearance.class);
/* 51 */     registerTag("CompositeAppearance", ThemeCompositeAppearance.class);
/* 52 */     registerTag("DisplayObjectViewerAppearance", ThemeDisplayObjectViewerAppearance.class);
/* 53 */     registerTag("ThemeElement", ThemeElement.class);
/* 54 */     registerTag("GradientBackground", ThemeGradientBackground.class);
/* 55 */     registerTag("ImageAppearance", ThemeImageAppearance.class);
/* 56 */     registerTag("LabelAppearance", ThemeLabelAppearance.class);
/* 57 */     registerTag("ListAppearance", ThemeListAppearance.class);
/* 58 */     registerTag("Margin", ThemeMargin.class);
/* 59 */     registerTag("NamedColor", ThemeNamedColor.class);
/* 60 */     registerTag("Padding", ThemePadding.class);
/* 61 */     registerTag("Pixmap", ThemePixmap.class);
/* 62 */     registerTag("PixmapBackground", ThemePixmapBackground.class);
/* 63 */     registerTag("PixmapBorder", ThemePixmapBorder.class);
/* 64 */     registerTag("PixmapBorder16", ThemePixmapBorder16.class);
/* 65 */     registerTag("PlainBackground", ThemePlainBackground.class);
/* 66 */     registerTag("PlainBorder", ThemePlainBorder.class);
/* 67 */     registerTag("PopupMenuAppearance", ThemePopupMenuAppearance.class);
/* 68 */     registerTag("PositionableColor", ThemePositionableColor.class);
/* 69 */     registerTag("ProgressBarAppearance", ThemeProgressBarAppearance.class);
/* 70 */     registerTag("RoundedBorder", ThemeRoundedBorder.class);
/* 71 */     registerTag("ScrollBarAppearance", ThemeScrollBarAppearance.class);
/* 72 */     registerTag("ScrollContainerAppearance", ThemeScrollContainerAppearance.class);
/* 73 */     registerTag("SliderAppearance", ThemeSliderAppearance.class);
/* 74 */     registerTag("TabbedContainerAppearance", ThemeTabbedContainerAppearance.class);
/* 75 */     registerTag("TextEditorAppearance", ThemeTextEditorAppearance.class);
/* 76 */     registerTag("TextViewAppearance", ThemeTextViewAppearance.class);
/* 77 */     registerTag("TitledBorder", ThemeTitledBorder.class);
/* 78 */     registerTag("WindowAppearance", ThemeWindowAppearance.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\theme\ThemeTagLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */