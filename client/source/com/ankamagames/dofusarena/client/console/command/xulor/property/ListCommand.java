/*    */ package com.ankamagames.dofusarena.client.console.command.xulor.property;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.property.Property;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ public class ListCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 31 */     StringBuilder builder = new StringBuilder("# Liste des propriétés #");
/* 32 */     Iterable<Property> properties = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperties();
/* 33 */     for (Property property : properties) {
/* 34 */       builder.append('\n').append(" - ").append(property.getName());
/* 35 */       if (property.getValue() instanceof com.ankamagames.xulor.property.FieldProvider) {
/* 36 */         builder.append(" (F)");
/*    */       }
/*    */     } 
/* 39 */     manager.trace(builder.toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\xulor\property\ListCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */