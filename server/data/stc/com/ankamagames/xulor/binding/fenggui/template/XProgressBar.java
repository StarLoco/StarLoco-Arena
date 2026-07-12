/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XProgressBarAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IProgressBar;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeProgressBarAppearance;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.ProgressBar;
/*     */ import org.fenggui.ProgressBarAppearance;
/*     */ import org.fenggui.Widget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XProgressBar
/*     */   extends XComponent
/*     */   implements IProgressBar
/*     */ {
/*     */   public static final String TAG = "ProgressBar";
/*  31 */   private ProgressBar m_progressBar = null;
/*     */   
/*  33 */   private String m_text = "";
/*  34 */   private double m_value = 0.0D;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML()
/*     */   {
/*  42 */     System.out.println("<progressbar>");
/*  43 */     IElement[] arrayOfIElement; int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  44 */       c.buildXML();
/*     */     }
/*  46 */     System.out.println("</progressbar>");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/*  56 */     if (this.m_progressBar == null) {
/*  57 */       this.m_progressBar = new ProgressBar();
/*     */       
/*  59 */       applyAllAttributes();
/*     */       
/*  61 */       if (this.m_parent != null) { this.m_parent.addWidget(this);
/*     */       }
/*  63 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_progressBar, this);
/*     */     }
/*     */     IElement[] arrayOfIElement;
/*  66 */     int j = (arrayOfIElement = getChildren()).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement[i];
/*  67 */       c.buildGUI();
/*     */     }
/*     */     
/*  70 */     applyTheme();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  80 */     if (this.m_progressBar == null)
/*  81 */       return;
/*  82 */     applyComponentAttributes();
/*     */   }
/*     */   
/*     */   public void applyTheme() {
/*  86 */     if (this.m_themeNeedToBeApplied) {
/*  87 */       this.m_themeNeedToBeApplied = false;
/*  88 */       applyProgressBarTheme(this.m_progressBar, this.m_themeElement);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getText()
/*     */   {
/*  98 */     if (this.m_progressBar != null) {
/*  99 */       return this.m_progressBar.getText();
/*     */     }
/* 101 */     return this.m_text;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getValue()
/*     */   {
/* 110 */     if (this.m_progressBar != null) {
/* 111 */       return this.m_progressBar.getValue();
/*     */     }
/* 113 */     return this.m_value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 122 */     if (this.m_progressBar != null) {
/* 123 */       this.m_progressBar.setText(text);
/*     */     } else {
/* 125 */       this.m_text = text;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValue(double value)
/*     */   {
/* 135 */     if (this.m_progressBar != null) {
/* 136 */       this.m_progressBar.setValue(value);
/*     */     } else {
/* 138 */       this.m_value = value;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Widget getWidget()
/*     */   {
/* 147 */     return this.m_progressBar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 155 */     return "ProgressBar";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 163 */     XProgressBar elem = (XProgressBar)element;
/* 164 */     elem.m_text = this.m_text;
/* 165 */     elem.m_value = this.m_value;
/* 166 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 173 */     XProgressBar elem = new XProgressBar();
/* 174 */     copyElementData(elem);
/* 175 */     return elem;
/*     */   }
/*     */   
/*     */   public static void applyProgressBarTheme(ProgressBar progressBar, ThemeElement element) {
/* 179 */     if ((progressBar == null) || (element == null)) {
/* 180 */       return;
/*     */     }
/*     */     
/* 183 */     progressBar.getAppearance().removeAll();
/* 184 */     XComponent.applyThemeAttributes(progressBar, element.getAttributes());
/* 185 */     XSpacingAppearance.setAppearance(progressBar, element);
/* 186 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 187 */     for (ThemeAppearance app : appearances) {
/* 188 */       if (app != null) {
/* 189 */         XDecoratorAppearance.setAppearance(progressBar, app);
/* 190 */         if ((app instanceof ThemeProgressBarAppearance)) {
/* 191 */           XProgressBarAppearance.setAppearance(progressBar, (ThemeProgressBarAppearance)app);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XProgressBar.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */