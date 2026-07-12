/*    */ package com.ankamagames.xulor.event;
/*    */ 
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*    */ import com.ankamagames.xulor.binding.fenggui.FengguiScene;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import java.util.ArrayList;
/*    */ import org.fenggui.Display;
/*    */ import org.fenggui.event.ActivationEvent;
/*    */ import org.fenggui.event.Event;
/*    */ import org.fenggui.event.FocusEvent;
/*    */ import org.fenggui.event.IEventListener;
/*    */ import org.fenggui.event.KeyPressedEvent;
/*    */ import org.fenggui.event.KeyReleasedEvent;
/*    */ import org.fenggui.event.mouse.MouseDraggedEvent;
/*    */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*    */ import org.fenggui.event.mouse.MouseExitedEvent;
/*    */ import org.fenggui.event.mouse.MouseMovedEvent;
/*    */ import org.fenggui.event.mouse.MousePressedEvent;
/*    */ import org.fenggui.event.mouse.MouseReleasedEvent;
/*    */ import org.fenggui.event.mouse.MouseWheelEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GlobalEventManager
/*    */   implements IEventListener
/*    */ {
/*    */   private static GlobalEventManager m_listener;
/*    */   private Environment m_environment;
/* 30 */   private ArrayList<GlobalEventListener> m_listenerList = new ArrayList<GlobalEventListener>();
/*    */   
/*    */   private GlobalEventManager() {
/* 33 */     Display display = ((FengguiScene)Xulor.getInstance().getScene()).getDisplay();
/* 34 */     if (display != null)
/* 35 */       display.addGlobalEventListener(this); 
/* 36 */     this.m_environment = Xulor.getInstance().getEnvironment();
/*    */   }
/*    */   
/*    */   public static GlobalEventManager getInstance() {
/* 40 */     if (m_listener == null)
/* 41 */       m_listener = new GlobalEventManager(); 
/* 42 */     return m_listener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processEvent(Event event) {
/* 51 */     Event xulorEvent = null;
/* 52 */     if (event instanceof ActivationEvent) {
/* 53 */       xulorEvent = FengguiConstant.toXulorActivationEvent((ActivationEvent)event);
/* 54 */     } else if (event instanceof FocusEvent) {
/* 55 */       xulorEvent = FengguiConstant.toXulorFocusEvent((FocusEvent)event);
/* 56 */     } else if (event instanceof KeyPressedEvent) {
/* 57 */       xulorEvent = FengguiConstant.toXulorKeyPressedEvent((KeyPressedEvent)event);
/* 58 */     } else if (event instanceof KeyReleasedEvent) {
/* 59 */       xulorEvent = FengguiConstant.toXulorKeyReleasedEvent((KeyReleasedEvent)event);
/* 60 */     } else if (event instanceof MouseDraggedEvent) {
/* 61 */       xulorEvent = FengguiConstant.toXulorMouseDraggedEvent((MouseDraggedEvent)event);
/* 62 */     } else if (event instanceof MouseEnteredEvent) {
/* 63 */       xulorEvent = FengguiConstant.toXulorMouseEnteredEvent((MouseEnteredEvent)event);
/* 64 */     } else if (event instanceof MouseExitedEvent) {
/* 65 */       xulorEvent = FengguiConstant.toXulorMouseExitedEvent((MouseExitedEvent)event);
/* 66 */     } else if (event instanceof MouseMovedEvent) {
/* 67 */       xulorEvent = FengguiConstant.toXulorMouseMovedEvent((MouseMovedEvent)event);
/* 68 */     } else if (event instanceof MousePressedEvent) {
/* 69 */       xulorEvent = FengguiConstant.toXulorMousePressedEvent((MousePressedEvent)event);
/* 70 */     } else if (event instanceof MouseReleasedEvent) {
/* 71 */       xulorEvent = FengguiConstant.toXulorMouseReleasedEvent((MouseReleasedEvent)event);
/* 72 */     } else if (event instanceof MouseWheelEvent) {
/* 73 */       xulorEvent = FengguiConstant.toXulorMouseWheelEvent((MouseWheelEvent)event);
/*    */     } 
/*    */     
/* 76 */     for (GlobalEventListener l : this.m_listenerList) {
/* 77 */       l.run(xulorEvent);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addListener(GlobalEventListener listener) {
/* 86 */     this.m_listenerList.add(listener);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\event\GlobalEventManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */