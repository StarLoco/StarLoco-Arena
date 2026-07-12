/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
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
/*    */ public class CommandDescriptor
/*    */   extends CommandPattern
/*    */ {
/*    */   private Class m_instanceClass;
/*    */   
/*    */   public CommandDescriptor(String cmdRegex, String argsRegex, Class instanceClass) {
/* 25 */     super(cmdRegex, argsRegex);
/* 26 */     this.m_instanceClass = instanceClass;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Command createInstance() {
/*    */     try {
/* 37 */       return this.m_instanceClass.newInstance();
/* 38 */     } catch (InstantiationException e) {
/* 39 */       e.printStackTrace();
/* 40 */     } catch (IllegalAccessException e) {
/* 41 */       e.printStackTrace();
/*    */     } 
/* 43 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\console\command\descriptors\CommandDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */