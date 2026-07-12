/*    */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*    */ 
/*    */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*    */ import com.ankamagames.xulor.template.IElement;
/*    */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*    */ import com.ankamagames.xulor.theme.ThemeTextViewAppearance;
/*    */ import java.util.ArrayList;
/*    */ import org.fenggui.IAppearance;
/*    */ import org.fenggui.render.Font;
/*    */ import org.fenggui.text.TextStyle;
/*    */ import org.fenggui.text.TextView;
/*    */ import org.fenggui.util.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XTextViewAppearance
/*    */   extends XDecoratorAppearance
/*    */ {
/*    */   public static final String TAG = "TextViewAppearance";
/* 29 */   private ArrayList<ThemeAppearance> m_appearances = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public IAppearance getAppearance()
/*    */   {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void applyAllAttributes() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildGUI() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void buildXML() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IElement cloneElementStructure()
/*    */   {
/* 69 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getTag()
/*    */   {
/* 76 */     return "TextViewAppearance";
/*    */   }
/*    */   
/*    */   public static void setAppearance(TextView tv, ThemeTextViewAppearance theme) {
/* 80 */     if ((tv == null) || (theme == null)) {
/* 81 */       return;
/*    */     }
/*    */     
/* 84 */     Font font = FengguiConstant.toFengguiFont(theme.getFont());
/* 85 */     if (font == null) {
/* 86 */       font = Font.getDefaultFont();
/*    */     }
/*    */     
/* 89 */     Color color = FengguiConstant.toFengguiColor(theme.getTextColor());
/* 90 */     if (color == null) {
/* 91 */       color = Color.BLACK;
/*    */     }
/*    */     
/* 94 */     tv.setStyle(new TextStyle(font, color));
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XTextViewAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */