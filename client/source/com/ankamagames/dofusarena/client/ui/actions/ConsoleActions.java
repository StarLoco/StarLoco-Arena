/*    */ package com.ankamagames.dofusarena.client.ui.actions;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.form.Form;
/*    */ import com.ankamagames.xulor.event.Event;
/*    */ import com.ankamagames.xulor.event.Key;
/*    */ import com.ankamagames.xulor.event.KeyPressedEvent;
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
/*    */ 
/*    */ public class ConsoleActions
/*    */ {
/*    */   public static final String PACKAGE = "console";
/*    */   
/*    */   public static void processInputKeyEvent(KeyPressedEvent keyPressedEvent, Form form) {
/*    */     String input;
/* 32 */     Property fieldedProperty = form.getProperty("debug.console");
/* 33 */     switch (keyPressedEvent.getKeyClass()) {
/*    */       case ENTER:
/* 35 */         form.synchronizeProperties();
/* 36 */         input = fieldedProperty.getFieldStringValue("input");
/* 37 */         ConsoleManager.getInstance().parseInput(input);
/* 38 */         fieldedProperty.setFieldValue("input", "");
/*    */         break;
/*    */       
/*    */       case UP:
/* 42 */         fieldedProperty.setFieldValue("input", ConsoleManager.getInstance().getHistoryUp());
/*    */         break;
/*    */       
/*    */       case DOWN:
/* 46 */         fieldedProperty.setFieldValue("input", ConsoleManager.getInstance().getHistoryDown());
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void clear(Event event) {
/* 57 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("debug.console", "logs", "");
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\clien\\ui\actions\ConsoleActions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */