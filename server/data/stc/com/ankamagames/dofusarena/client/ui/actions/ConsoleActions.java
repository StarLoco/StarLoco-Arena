/*    */ package com.ankamagames.dofusarena.client.ui.actions;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.core.form.Form;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.KeyPressedEvent;
/*    */ import com.ankamagames.xulor.property.PropertiesProvider;
/*    */ import com.ankamagames.xulor.property.Property;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConsoleActions
/*    */ {
/*    */   public static final String PACKAGE = "console";
/*    */   
/*    */   public static void processInputKeyEvent(KeyPressedEvent keyPressedEvent, Form form)
/*    */   {
/* 32 */     Property fieldedProperty = form.getProperty("debug.console");
/* 33 */     switch (keyPressedEvent.getKeyClass()) {
/*    */     case F1: 
/* 35 */       form.synchronizeProperties();
/* 36 */       String input = fieldedProperty.getFieldStringValue("input");
/* 37 */       ConsoleManager.getInstance().parseInput(input);
/* 38 */       fieldedProperty.setFieldValue("input", "");
/* 39 */       break;
/*    */     
/*    */     case DIGIT: 
/* 42 */       fieldedProperty.setFieldValue("input", ConsoleManager.getInstance().getHistoryUp());
/* 43 */       break;
/*    */     
/*    */     case DOWN: 
/* 46 */       fieldedProperty.setFieldValue("input", ConsoleManager.getInstance().getHistoryDown());
/*    */     }
/*    */     
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void clear(Event event)
/*    */   {
/* 57 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("debug.console", "logs", "");
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\ui\actions\ConsoleActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */