/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.event.ActivationEvent;
/*     */ import org.fenggui.event.ButtonPressedEvent;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.IActivationListener;
/*     */ import org.fenggui.event.IButtonPressedListener;
/*     */ import org.fenggui.event.IEventListener;
/*     */ import org.fenggui.event.IFocusListener;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.IKeyReleasedListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.KeyReleasedEvent;
/*     */ import org.fenggui.event.mouse.IMouseEnteredListener;
/*     */ import org.fenggui.event.mouse.IMouseExitedListener;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.IMouseReleasedListener;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Button
/*     */   extends ObservableLabelWidget
/*     */ {
/*  67 */   private ArrayList<IButtonPressedListener> buttonPressedHook = new ArrayList<IButtonPressedListener>();
/*     */   
/*     */   public static final String LABEL_DEFAULT = "default";
/*     */   
/*     */   public static final String LABEL_MOUSEHOVER = "mouseHover";
/*     */   public static final String LABEL_PRESSED = "pressed";
/*     */   public static final String LABEL_FOCUSED = "focused";
/*     */   public static final String LABEL_DISABLED = "disabled";
/*     */   private boolean pressed = false;
/*     */   private Button THIS;
/*  77 */   private IEventListener globalListener = null;
/*     */ 
/*     */   
/*     */   public Button() {
/*  81 */     this((String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Button(String text) {
/*  86 */     setText(text);
/*  87 */     setSize(10, 10);
/*  88 */     buildMouseBehavior();
/*  89 */     buildKeyboardBehavior();
/*  90 */     setupTheme(Button.class);
/*  91 */     getAppearance().setEnabled("mouseHover", false);
/*  92 */     getAppearance().setEnabled("pressed", false);
/*  93 */     getAppearance().setEnabled("disabled", false);
/*  94 */     getAppearance().setEnabled("focused", false);
/*  95 */     this.THIS = this;
/*  96 */     setTraversable(true);
/*  97 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Button(InputOnlyStream stream) throws IOException, IOStreamException {
/* 102 */     buildMouseBehavior();
/* 103 */     buildKeyboardBehavior();
/* 104 */     process((InputOutputStream)stream);
/* 105 */     getAppearance().setEnabled("mouseHover", false);
/* 106 */     getAppearance().setEnabled("pressed", false);
/* 107 */     getAppearance().setEnabled("disabled", false);
/* 108 */     getAppearance().setEnabled("focused", false);
/* 109 */     this.THIS = this;
/* 110 */     setTraversable(true);
/* 111 */     updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   void buildKeyboardBehavior() {
/* 116 */     addFocusListener(new IFocusListener()
/*     */         {
/*     */           public void focusChanged(FocusEvent focusChangedEvent)
/*     */           {
/* 120 */             if (focusChangedEvent.isFocusGained()) {
/*     */               
/* 122 */               Button.this.getAppearance().setEnabled("focused", true);
/*     */             }
/*     */             else {
/*     */               
/* 126 */               Button.this.getAppearance().setEnabled("focused", false);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 131 */     addKeyPressedListener(new IKeyPressedListener()
/*     */         {
/*     */           public void keyPressed(KeyPressedEvent e)
/*     */           {
/* 135 */             if (e.getKey() == ' ' || e.getKeyClass() == Key.ENTER)
/*     */             {
/* 137 */               Button.this.pressed();
/*     */             }
/*     */           }
/*     */         });
/* 141 */     addKeyReleasedListener(new IKeyReleasedListener()
/*     */         {
/*     */           public void keyReleased(KeyReleasedEvent e)
/*     */           {
/* 145 */             if (e.getKey() == ' ' || e.getKeyClass() == Key.ENTER)
/*     */             {
/* 147 */               Button.this.released();
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private final void pressed() {
/* 155 */     getAppearance().setEnabled("pressed", true);
/* 156 */     this.pressed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private final void released() {
/* 161 */     if (this.pressed) {
/*     */       
/* 163 */       this.pressed = false;
/* 164 */       fireButtonPressedEvent();
/* 165 */       getAppearance().setEnabled("pressed", false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void buildMouseBehavior() {
/* 171 */     addActivationListener(new IActivationListener()
/*     */         {
/*     */           public void widgetActivationChanged(ActivationEvent activationEvent)
/*     */           {
/* 175 */             Button.this.getAppearance().setEnabled("disabled", !activationEvent.isEnabled());
/*     */           }
/*     */         });
/* 178 */     addMouseEnteredListener(new IMouseEnteredListener()
/*     */         {
/*     */           public void mouseEntered(MouseEnteredEvent mouseEnteredEvent)
/*     */           {
/* 182 */             Button.this.getAppearance().setEnabled("mouseHover", true);
/* 183 */             Button.this.getAppearance().setEnabled("pressed", Button.this.pressed);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 188 */     addMouseExitedListener(new IMouseExitedListener()
/*     */         {
/*     */           public void mouseExited(MouseExitedEvent mouseExited)
/*     */           {
/* 192 */             Button.this.getAppearance().setEnabled("default", true);
/* 193 */             Button.this.getAppearance().setEnabled("mouseHover", false);
/* 194 */             Button.this.getAppearance().setEnabled("pressed", false);
/*     */           }
/*     */         });
/* 197 */     addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mousePressedEvent)
/*     */           {
/* 201 */             Button.this.pressed();
/*     */           }
/*     */         });
/* 204 */     addMouseReleasedListener(new IMouseReleasedListener()
/*     */         {
/*     */           public void mouseReleased(MouseReleasedEvent mouseReleasedEvent)
/*     */           {
/* 208 */             Button.this.released();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public boolean isPressed() {
/* 214 */     return this.pressed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addButtonPressedListener(IButtonPressedListener l) {
/* 219 */     if (!this.buttonPressedHook.contains(l))
/*     */     {
/* 221 */       this.buttonPressedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeButtonPressedListener(IButtonPressedListener l) {
/* 227 */     this.buttonPressedHook.remove(l);
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireButtonPressedEvent() {
/* 232 */     ButtonPressedEvent e = new ButtonPressedEvent(this);
/*     */     
/* 234 */     for (int i = 0; i < this.buttonPressedHook.size(); i++) {
/*     */       
/* 236 */       IButtonPressedListener l = this.buttonPressedHook.get(i);
/* 237 */       l.buttonPressed(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addedToWidgetTree() {
/* 246 */     super.addedToWidgetTree();
/* 247 */     if (getDisplay() != null) {
/* 248 */       this.globalListener = new IEventListener() {
/*     */           public void processEvent(Event event) {
/* 250 */             if (event instanceof MouseReleasedEvent) {
/* 251 */               MouseReleasedEvent mouseReleasedEvent = (MouseReleasedEvent)event;
/* 252 */               if (mouseReleasedEvent.getSource() != Button.this.THIS) {
/* 253 */                 Button.this.pressed = false;
/*     */               }
/*     */             } 
/*     */           }
/*     */         };
/* 258 */       getDisplay().addGlobalEventListener(this.globalListener);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removedFromWidgetTree() {
/* 267 */     super.removedFromWidgetTree();
/* 268 */     if (getDisplay() != null && 
/* 269 */       this.globalListener != null)
/* 270 */       getDisplay().removeGlobalEventListener(this.globalListener); 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\Button.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */