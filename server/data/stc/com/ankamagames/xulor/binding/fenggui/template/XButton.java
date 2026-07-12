/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XLabelAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IButton;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeLabelAppearance;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.LabelAppearance;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.IButtonPressedListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XButton
/*     */   extends XObservableLabelComponent
/*     */   implements IButton
/*     */ {
/*     */   public static final String TAG = "Button";
/*  27 */   private Button m_button = null;
/*     */   
/*  29 */   private IButtonPressedListener m_buttonPressedListener = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  37 */     IElement[] components = getChildren();
/*  38 */     System.out.println("<button text=\"" + this.m_text + "\" image=\"" + this.m_pixmap + "\">");
/*  39 */     IElement[] arrayOfIElement1; int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/*  40 */       c.buildXML();
/*     */     }
/*  42 */     System.out.println("</button>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  52 */     if (this.m_button == null) {
/*  53 */       this.m_button = new Button();
/*     */       
/*  55 */       applyAllAttributes();
/*     */       
/*  57 */       addObservableComponentListeners();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  67 */       if (this.m_parent != null) { this.m_parent.addWidget(this);
/*     */       }
/*  69 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_button, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  72 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  73 */       c.buildGUI();
/*     */     }
/*     */     
/*  76 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  86 */     if (this.m_button == null)
/*  87 */       return;
/*  88 */     applyComponentAttributes();
/*  89 */     applyObservableComponentAttributes();
/*  90 */     applyObservableLabelComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  94 */     if (this.m_themeNeedToBeApplied) {
/*  95 */       this.m_themeNeedToBeApplied = false;
/*  96 */       applyButtonTheme(this.m_button, this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeSelfFromParent()
/*     */   {
/* 102 */     if (this.m_button != null) {
/* 103 */       this.m_button.removeButtonPressedListener(this.m_buttonPressedListener);
/*     */     }
/* 105 */     super.removeSelfFromParent();
/*     */   }
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
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 125 */     return this.m_button;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 133 */     return "Button";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 142 */     XButton elem = (XButton)element;
/*     */     
/*     */ 
/*     */ 
/* 146 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 153 */     XButton elem = new XButton();
/* 154 */     copyElementData(elem);
/* 155 */     return elem;
/*     */   }
/*     */   
/*     */   public static void applyButtonTheme(Button button, ThemeElement element) {
/* 159 */     if ((button == null) || (element == null)) {
/* 160 */       return;
/*     */     }
/*     */     
/* 163 */     button.getAppearance().removeAll();
/* 164 */     XComponent.applyThemeAttributes(button, element.getAttributes());
/* 165 */     XSpacingAppearance.setAppearance(button, element);
/* 166 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 167 */     for (ThemeAppearance app : appearances) {
/* 168 */       if (app != null) {
/* 169 */         XDecoratorAppearance.setAppearance(button, app);
/* 170 */         if ((app instanceof ThemeLabelAppearance)) {
/* 171 */           XLabelAppearance.setAppearance(button, (ThemeLabelAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/* 175 */     XObservableComponent.setAppearance(button);
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */