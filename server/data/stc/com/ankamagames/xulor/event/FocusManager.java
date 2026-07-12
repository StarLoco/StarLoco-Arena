/*     */ package com.ankamagames.xulor.event;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiScene;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IFocusable;
/*     */ import java.util.Stack;
/*     */ import org.fenggui.Display;
/*     */ import org.fenggui.Widget;
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
/*     */ public class FocusManager
/*     */ {
/*  24 */   private static final FocusManager m_instance = new FocusManager();
/*     */   
/*  26 */   private final Stack<IElement> m_focusList = new Stack();
/*  27 */   private IElement m_currentlyFocusedElement = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private FocusManager()
/*     */   {
/*  33 */     GlobalEventManager.getInstance().addListener(new GlobalEventListener() {
/*     */       public void run(Event event) {
/*  35 */         if ((event instanceof FocusEvent)) {
/*  36 */           if (((FocusEvent)event).hasFocus()) {
/*  37 */             FocusManager.this.gainFocus(event.getElement());
/*     */           } else {
/*  39 */             FocusManager.this.loseFocus(event.getElement(), false);
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   public static FocusManager getInstance()
/*     */   {
/*  49 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void gainFocus(IElement element)
/*     */   {
/*  58 */     if ((element != null) && 
/*  59 */       ((element instanceof IFocusable))) {
/*  60 */       this.m_focusList.remove(element);
/*  61 */       this.m_focusList.push(element);
/*  62 */       this.m_currentlyFocusedElement = element;
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
/*     */   public void loseFocus(IElement element, boolean onRemove)
/*     */   {
/*  76 */     this.m_currentlyFocusedElement = null;
/*  77 */     if (element == null) {
/*  78 */       return;
/*     */     }
/*  80 */     if ((onRemove) && (!this.m_focusList.isEmpty()))
/*     */     {
/*  82 */       if (element == this.m_focusList.peek()) {
/*  83 */         this.m_focusList.pop();
/*     */       } else {
/*  85 */         this.m_focusList.remove(element);
/*     */       }
/*  87 */       focusLast(true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void focusLast(boolean atOnce)
/*     */   {
/*  95 */     if (!this.m_focusList.isEmpty()) {
/*  96 */       this.m_currentlyFocusedElement = ((IElement)this.m_focusList.peek());
/*     */     }
/*  98 */     if (atOnce)
/*     */     {
/* 100 */       focus();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void focus()
/*     */   {
/* 108 */     Widget w = this.m_currentlyFocusedElement != null ? (Widget)this.m_currentlyFocusedElement.getEncapsulatedObject() : null;
/*     */     
/* 110 */     Display display = ((FengguiScene)Xulor.getInstance().getScene()).getDisplay();
/* 111 */     if ((display != null) && (display.getFocusedWidget() != w)) {
/* 112 */       display.setFocusedWidget(w);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\FocusManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */