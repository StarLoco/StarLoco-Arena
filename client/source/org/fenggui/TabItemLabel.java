/*     */ package org.fenggui;
/*     */ 
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.IFocusListener;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.mouse.IMouseEnteredListener;
/*     */ import org.fenggui.event.mouse.IMouseExitedListener;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
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
/*     */ public class TabItemLabel
/*     */   extends ObservableLabelWidget
/*     */ {
/*  43 */   private TabContainer tabContainer = null;
/*     */   
/*     */   public static final String LABEL_DEFAULT = "default";
/*     */   
/*     */   public static final String LABEL_MOUSE_HOVER = "mouseHover";
/*     */   public static final String LABEL_FOCUSED = "active";
/*     */   
/*     */   public TabItemLabel(TabContainer tabContainer) {
/*  51 */     this.tabContainer = tabContainer;
/*     */     
/*  53 */     setupTheme(TabItemLabel.class);
/*  54 */     buildBehavior();
/*  55 */     setTraversable(true);
/*     */     
/*  57 */     getAppearance().setEnabled("default", true);
/*  58 */     getAppearance().setEnabled("active", false);
/*  59 */     getAppearance().setEnabled("mouseHover", false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void buildBehavior() {
/*  65 */     final TabItemLabel THIZZ = this;
/*     */     
/*  67 */     addMouseEnteredListener(new IMouseEnteredListener()
/*     */         {
/*     */           
/*     */           public void mouseEntered(MouseEnteredEvent mouseEnteredEvent)
/*     */           {
/*  72 */             TabItemLabel.this.getAppearance().setEnabled("mouseHover", true);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  77 */     addMouseExitedListener(new IMouseExitedListener()
/*     */         {
/*     */           public void mouseExited(MouseExitedEvent mouseExited)
/*     */           {
/*  81 */             TabItemLabel.this.getAppearance().setEnabled("mouseHover", false);
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mousePressedEvent) {
/*  93 */             TabItemLabel.this.tabContainer.selectTab(THIZZ);
/*     */           }
/*     */         });
/*     */     
/*  97 */     addKeyPressedListener(new IKeyPressedListener()
/*     */         {
/*     */           public void keyPressed(KeyPressedEvent k)
/*     */           {
/* 101 */             if (k.getKey() == ' ' || k.getKeyClass() == Key.ENTER) {
/*     */               
/* 103 */               TabItemLabel.this.tabContainer.selectTab(THIZZ);
/* 104 */               TabItemLabel.this.getDisplay().setFocusedWidget(TabItemLabel.this.tabContainer.getSelectedTabWidget());
/*     */             } 
/*     */           }
/*     */         });
/* 108 */     addFocusListener(new IFocusListener()
/*     */         {
/*     */           public void focusChanged(FocusEvent f)
/*     */           {
/* 112 */             if (f.isFocusGained()) {
/*     */               
/* 114 */               TabItemLabel.this.getAppearance().setEnabled("active", true);
/*     */ 
/*     */             
/*     */             }
/* 118 */             else if (!THIZZ.equals(TabItemLabel.this.tabContainer.getSelectedTabLabel())) {
/* 119 */               TabItemLabel.this.getAppearance().setEnabled("active", false);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\TabItemLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */