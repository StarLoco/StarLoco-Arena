/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.XulorScene;
/*     */ import com.ankamagames.xulor.binding.fenggui.component.PopupMenu;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XDecoratorAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XPopupMenuAppearance;
/*     */ import com.ankamagames.xulor.binding.fenggui.template.decorator.XSpacingAppearance;
/*     */ import com.ankamagames.xulor.event.IMouseClickListener;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.listener.MouseClickListener;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IPopupMenu;
/*     */ import com.ankamagames.xulor.theme.ThemeAppearance;
/*     */ import com.ankamagames.xulor.theme.ThemeElement;
/*     */ import com.ankamagames.xulor.theme.ThemePopupMenuAppearance;
/*     */ import com.ankamagames.xulor.util.Alignment;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.Button;
/*     */ import org.fenggui.Label;
/*     */ import org.fenggui.StandardWidget;
/*     */ import org.fenggui.Widget;
/*     */ import org.fenggui.event.IMenuClosedListener;
/*     */ import org.fenggui.event.MenuClosedEvent;
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
/*     */ public class XPopupMenu
/*     */   extends XComponent
/*     */   implements IPopupMenu
/*     */ {
/*     */   public static final String TAG = "PopupMenu";
/*  42 */   private PopupMenu m_popupMenu = null;
/*     */   
/*  44 */   private XButton m_buttonTemplate = null;
/*  45 */   private XLabel m_labelTemplate = null;
/*  46 */   private XSeparator m_separatorTemplate = null;
/*  47 */   private ArrayList<XComponent> m_components = new ArrayList<XComponent>();
/*     */   private boolean m_show = false;
/*  49 */   private int m_x = -1; private int m_y = -1;
/*  50 */   private Alignment m_hotSpotPosition = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  57 */     if (this.m_popupMenu == null) {
/*  58 */       this.m_popupMenu = new PopupMenu();
/*     */       
/*  60 */       for (XComponent component : this.m_components) {
/*  61 */         component.buildGUI();
/*     */       }
/*     */       
/*  64 */       applyAllAttributes();
/*     */       
/*  66 */       if (this.m_parent != null) this.m_parent.addWidget((IElement)this); 
/*  67 */       Xulor.getInstance().getEnvironment().putElementByWidget(this.m_popupMenu, (IElement)this);
/*     */     } 
/*     */     
/*  70 */     applyTheme();
/*     */   }
/*     */   
/*     */   public void applyAllAttributes() {
/*  74 */     if (this.m_popupMenu != null) {
/*  75 */       for (XComponent component : this.m_components) {
/*  76 */         if (component instanceof XButton) {
/*  77 */           this.m_popupMenu.addButton((Button)component.getWidget()); continue;
/*  78 */         }  if (component instanceof XLabel) {
/*  79 */           this.m_popupMenu.addLabel((Label)component.getWidget()); continue;
/*  80 */         }  if (component instanceof XSeparator) {
/*  81 */           this.m_popupMenu.addWidget((StandardWidget)component.getWidget());
/*     */         }
/*     */       } 
/*     */       
/*  85 */       this.m_popupMenu.addMenuClosedListener(new IMenuClosedListener() {
/*     */             public void menuClosed(MenuClosedEvent menuClosedEvent) {
/*  87 */               Xulor.getInstance().unload(XPopupMenu.this.m_id);
/*     */             }
/*     */           });
/*     */       
/*  91 */       if (this.m_hotSpotPosition != null) {
/*  92 */         this.m_popupMenu.setHotSpotPosition(this.m_hotSpotPosition);
/*     */       }
/*     */ 
/*     */       
/*  96 */       if (this.m_show) {
/*  97 */         this.m_popupMenu.show(this.m_x, this.m_y);
/*     */       }
/*  99 */       applyComponentAttributes();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyTheme() {
/* 108 */     if (this.m_themeNeedToBeApplied) {
/* 109 */       this.m_themeNeedToBeApplied = false;
/* 110 */       applyPopupMenuTheme(this.m_popupMenu, this.m_themeElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Widget getWidget() {
/* 119 */     return (Widget)this.m_popupMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 134 */     return "PopupMenu";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addButton(String label, Pixmap pixmap, IMouseClickListener listener, boolean enabled) {
/*     */     XButton button;
/* 142 */     if (this.m_buttonTemplate != null) {
/* 143 */       button = (XButton)this.m_buttonTemplate.cloneElementStructure();
/*     */     } else {
/* 145 */       button = new XButton();
/*     */     } 
/*     */     
/* 148 */     button.setText(label);
/* 149 */     button.setOnClick(listener);
/* 150 */     button.setOnClick((IMouseClickListener)new MouseClickListener() {
/*     */           public void run(MouseClickEvent event) {
/* 152 */             Xulor.getInstance().unload(XPopupMenu.this.m_id);
/*     */           }
/*     */         });
/* 155 */     button.setEnabled(enabled);
/* 156 */     this.m_components.add(button);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLabel(String text, Pixmap pixmap) {
/*     */     XLabel label;
/* 164 */     if (this.m_labelTemplate != null) {
/* 165 */       label = (XLabel)this.m_labelTemplate.cloneElementStructure();
/*     */     } else {
/* 167 */       label = new XLabel();
/*     */     } 
/* 169 */     label.setText(text);
/* 170 */     label.setPixmap(pixmap);
/* 171 */     this.m_components.add(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSeparator() {
/*     */     XSeparator separator;
/* 180 */     if (this.m_separatorTemplate != null) {
/* 181 */       separator = (XSeparator)this.m_separatorTemplate.cloneElementStructure();
/*     */     } else {
/* 183 */       separator = new XSeparator();
/*     */     } 
/* 185 */     this.m_components.add(separator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Alignment getHotSpotPosition() {
/* 192 */     return this.m_hotSpotPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHotSpotPosition(Alignment align) {
/* 199 */     this.m_hotSpotPosition = align;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show(int x, int y) {
/* 206 */     this.m_show = true;
/* 207 */     this.m_x = x;
/* 208 */     this.m_y = y;
/* 209 */     if (this.m_popupMenu != null) {
/* 210 */       this.m_popupMenu.show(x, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/* 218 */     this.m_show = true;
/* 219 */     XulorScene scene = Xulor.getInstance().getScene();
/* 220 */     if (scene != null) {
/* 221 */       this.m_x = scene.getMouseX();
/* 222 */       this.m_y = (int)scene.getFrustumHeight() - scene.getMouseY();
/*     */     } 
/* 224 */     if (this.m_popupMenu != null) {
/* 225 */       this.m_popupMenu.show(this.m_x, this.m_y);
/*     */     }
/*     */   }
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
/*     */   public void add(IElement childElement) {
/* 242 */     if (childElement instanceof XButton) {
/* 243 */       this.m_buttonTemplate = (XButton)childElement;
/* 244 */     } else if (childElement instanceof XLabel) {
/* 245 */       this.m_labelTemplate = (XLabel)childElement;
/* 246 */     } else if (childElement instanceof XSeparator) {
/* 247 */       this.m_separatorTemplate = (XSeparator)childElement;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void applyPopupMenuTheme(PopupMenu popupMenu, ThemeElement element) {
/* 252 */     if (popupMenu == null || element == null) {
/*     */       return;
/*     */     }
/*     */     
/* 256 */     popupMenu.getAppearance().removeAll();
/* 257 */     XComponent.applyThemeAttributes((Widget)popupMenu, element.getAttributes());
/* 258 */     XSpacingAppearance.setAppearance((StandardWidget)popupMenu, element);
/* 259 */     ArrayList<ThemeAppearance> appearances = element.getAppearances();
/* 260 */     for (ThemeAppearance app : appearances) {
/* 261 */       if (app != null) {
/* 262 */         XDecoratorAppearance.setAppearance((StandardWidget)popupMenu, app);
/* 263 */         if (app instanceof ThemePopupMenuAppearance)
/* 264 */           XPopupMenuAppearance.setAppearance(popupMenu, (ThemePopupMenuAppearance)app); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XPopupMenu.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */