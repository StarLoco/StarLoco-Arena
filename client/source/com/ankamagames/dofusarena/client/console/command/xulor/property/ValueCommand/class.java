/*    */ package com.ankamagames.dofusarena.client.console.command.xulor.property.ValueCommand;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.property.FieldProvider;
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
/*    */ public class ValueCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args) {
/* 31 */     String propertyName = args.get(2);
/* 32 */     StringBuilder builder = new StringBuilder(propertyName);
/* 33 */     builder.append(" = ");
/* 34 */     Property property = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty(propertyName);
/* 35 */     if (property != null) {
/* 36 */       Object value = property.getValue();
/* 37 */       if (value instanceof FieldProvider) {
/* 38 */         FieldProvider fieldProvider = (FieldProvider)value;
/* 39 */         String[] fields = fieldProvider.getFields(); byte b; int i; String[] arrayOfString1;
/* 40 */         for (i = (arrayOfString1 = fields).length, b = 0; b < i; ) { String field = arrayOfString1[b];
/* 41 */           builder.append('\n').append(field).append(" = ");
/* 42 */           appendValue(fieldProvider.getFieldValue(field), builder); b++; }
/*    */       
/*    */       } else {
/* 45 */         appendValue(value, builder);
/*    */       } 
/*    */     } 
/* 48 */     manager.trace(builder.toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPassThrough() {
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void appendValue(Object value, StringBuilder builder) {
/* 64 */     if (value instanceof Object[]) {
/* 65 */       byte b; int i; Object[] arrayOfObject; for (i = (arrayOfObject = (Object[])value).length, b = 0; b < i; ) { Object subValue = arrayOfObject[b];
/* 66 */         builder.append(subValue).append(','); b++; }
/*    */     
/*    */     } else {
/* 69 */       builder.append(value);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\xulor\property\ValueCommand\class.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */