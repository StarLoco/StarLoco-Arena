/*     */ package com.ankamagames.xulor.binding.fenggui.template.decorator;
/*     */ 
/*     */ import com.ankamagames.xulor.binding.fenggui.component.Window;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XButton;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XComponent;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XContainer;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.XLabel;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemeWindowAppearance;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.IAppearance;
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
/*     */ public class XWindowAppearance
/*     */   extends XDecoratorAppearance
/*     */ {
/*     */   public static final String TAG = "WindowAppearance";
/*  32 */   private ThemeAppearance m_titleBarAppearance = null;
/*  33 */   private ThemeAppearance m_labelAppearance = null;
/*  34 */   private ArrayList<ThemeAppearance> m_closeButtonAppearance = new ArrayList();
/*  35 */   private ArrayList<ThemeAppearance> m_maxButtonAppearance = new ArrayList();
/*  36 */   private ArrayList<ThemeAppearance> m_minButtonAppearance = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IAppearance getAppearance()
/*     */   {
/*  43 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes()
/*     */   {
/*  51 */     if ((this.m_parent instanceof XComponent)) {
/*  52 */       ThemeElement element = ((XComponent)this.m_parent).getThemeElement();
/*  53 */       if (element == null) {
/*  54 */         return;
/*     */       }
/*  56 */       ThemeAppearance theme = element.getThemeAppearance(this.m_state);
/*     */       
/*  58 */       if (theme == null) {
/*  59 */         theme = new ThemeWindowAppearance();
/*  60 */         theme.setState(this.m_state);
/*  61 */         element.addThemeAppearance(theme);
/*     */       }
/*     */       
/*  64 */       applySpacingAttributes();
/*  65 */       applyDecoratorAttributes();
/*     */       
/*  67 */       ((IComponent)this.m_parent).applyTheme();
/*     */     }
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
/*     */   public void buildGUI()
/*     */   {
/*  83 */     for (IElement c : this.m_children) {
/*  84 */       c.buildGUI();
/*     */     }
/*     */     
/*  87 */     applyAllAttributes();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ThemeAppearance getTitleBarAppearance()
/*     */   {
/* 100 */     return this.m_titleBarAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setTitleBarAppearance(ThemeAppearance titleBarAppearance)
/*     */   {
/* 107 */     this.m_titleBarAppearance = titleBarAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<ThemeAppearance> getCloseButtonAppearance()
/*     */   {
/* 114 */     return this.m_closeButtonAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCloseButtonAppearance(ArrayList<ThemeAppearance> closeButtonAppearance)
/*     */   {
/* 121 */     this.m_closeButtonAppearance = closeButtonAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ThemeAppearance getLabelAppearance()
/*     */   {
/* 128 */     return this.m_labelAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLabelAppearance(ThemeAppearance labelAppearance)
/*     */   {
/* 135 */     this.m_labelAppearance = labelAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<ThemeAppearance> getMaxButtonAppearance()
/*     */   {
/* 142 */     return this.m_maxButtonAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMaxButtonAppearance(ArrayList<ThemeAppearance> maxButtonAppearance)
/*     */   {
/* 149 */     this.m_maxButtonAppearance = maxButtonAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ArrayList<ThemeAppearance> getMinButtonAppearance()
/*     */   {
/* 156 */     return this.m_minButtonAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMinButtonAppearance(ArrayList<ThemeAppearance> minButtonAppearance)
/*     */   {
/* 163 */     this.m_minButtonAppearance = minButtonAppearance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 170 */     return "WindowAppearance";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void copyElementData(IElement element)
/*     */   {
/* 178 */     XWindowAppearance elem = (XWindowAppearance)element;
/* 179 */     elem.setTitleBarAppearance(this.m_titleBarAppearance);
/* 180 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 187 */     XWindowAppearance elem = new XWindowAppearance();
/* 188 */     copyElementData(elem);
/* 189 */     return elem;
/*     */   }
/*     */   
/*     */   protected void copyThemeAppearanceAttributes(ThemeWindowAppearance app) {
/* 193 */     super.copyThemeAppearanceAttributes(app);
/*     */   }
/*     */   
/*     */   public ThemeAppearance toThemeAppearance()
/*     */   {
/* 198 */     ThemeWindowAppearance app = new ThemeWindowAppearance();
/* 199 */     copyThemeAppearanceAttributes(app);
/* 200 */     return app;
/*     */   }
/*     */   
/*     */   public static void setAppearance(Window window, ThemeWindowAppearance theme) {
/* 204 */     if ((window == null) || (theme == null)) {
/* 205 */       return;
/*     */     }
/*     */     
/* 208 */     ThemeElement containerElement = theme.getThemeElement("titlebar");
/* 209 */     XContainer.applyContainerTheme(window.getTitleBar(), containerElement);
/*     */     
/* 211 */     ThemeElement labelElement = theme.getThemeElement("label");
/* 212 */     XLabel.applyLabelTheme(window.getTitleLabel(), labelElement);
/*     */     
/* 214 */     ThemeElement contentElement = theme.getThemeElement("content");
/* 215 */     XContainer.applyContainerTheme(window.getContentContainer(), contentElement);
/*     */     
/* 217 */     Button button = window.getCloseButton();
/* 218 */     if (button != null) {
/* 219 */       XButton.applyButtonTheme(button, theme.getThemeElement("closeButton"));
/* 220 */       button.setText(null);
/* 221 */       button.updateMinSize();
/* 222 */       button.setSizeToMinSize();
/* 223 */       button.setShrinkable(false);
/* 224 */       button.setExpandable(false);
/*     */     }
/*     */     
/* 227 */     button = window.getMinimizeButton();
/* 228 */     if (button != null) {
/* 229 */       XButton.applyButtonTheme(button, theme.getThemeElement("minButton"));
/* 230 */       button.setText(null);
/* 231 */       button.updateMinSize();
/* 232 */       button.setSizeToMinSize();
/* 233 */       button.setShrinkable(false);
/* 234 */       button.setExpandable(false);
/*     */     }
/*     */     
/* 237 */     button = window.getMaximizeButton();
/* 238 */     if (button != null) {
/* 239 */       XButton.applyButtonTheme(button, theme.getThemeElement("maxButton"));
/* 240 */       button.setText(null);
/* 241 */       button.updateMinSize();
/* 242 */       button.setSizeToMinSize();
/* 243 */       button.setShrinkable(false);
/* 244 */       button.setExpandable(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\decorator\XWindowAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */