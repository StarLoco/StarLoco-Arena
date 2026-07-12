/*    */ package com.ankamagames.dofusarena.client.console.command.xulor.property;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.ConsoleManager;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.Command;
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.console.command.descriptors.CommandPattern;
/*    */ import com.ankamagames.xulor.Xulor;
/*    */ import com.ankamagames.xulor.core.Environment;
/*    */ import com.ankamagames.xulor.property.FieldProvider;
/*    */ import com.ankamagames.xulor.property.PropertiesProvider;
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
/*    */ public class ValueCommand
/*    */   implements Command
/*    */ {
/*    */   public void execute(ConsoleManager manager, CommandPattern pattern, ArrayList<String> args)
/*    */   {
/* 31 */     String propertyName = (String)args.get(2);
/* 32 */     StringBuilder builder = new StringBuilder(propertyName);
/* 33 */     builder.append(" = ");
/* 34 */     Property property = Xulor.getInstance().getEnvironment().getPropertiesProvider().getProperty(propertyName);
/* 35 */     if (property != null) {
/* 36 */       Object value = property.getValue();
/* 37 */       if ((value instanceof FieldProvider)) {
/* 38 */         FieldProvider fieldProvider = (FieldProvider)value;
/* 39 */         String[] fields = fieldProvider.getFields();
/* 40 */         String[] arrayOfString1; int j = (arrayOfString1 = fields).length; for (int i = 0; i < j; i++) { String field = arrayOfString1[i];
/* 41 */           builder.append('\n').append(field).append(" = ");
/* 42 */           appendValue(fieldProvider.getFieldValue(field), builder);
/*    */         }
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
/*    */   public boolean isPassThrough()
/*    */   {
/* 57 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private void appendValue(Object value, StringBuilder builder)
/*    */   {
/* 64 */     if ((value instanceof Object[])) { Object[] arrayOfObject;
/* 65 */       int j = (arrayOfObject = (Object[])value).length; for (int i = 0; i < j; i++) { Object subValue = arrayOfObject[i];
/* 66 */         builder.append(subValue).append(',');
/*    */       }
/*    */     } else {
/* 69 */       builder.append(value);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\console\command\xulor\property\ValueCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */