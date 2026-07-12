/*    */ package org.fenggui.console;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChangeDirectoryCommand
/*    */   implements ICommand
/*    */ {
/*    */   public void execute(PrintStream out, Console source, String[] args) {
/* 31 */     if (args.length <= 1) {
/*    */       
/* 33 */       out.println("Usage: cd [directory]");
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     String f = String.valueOf(source.getCurrentDir().getAbsolutePath()) + "/" + args[1];
/* 38 */     File file = new File(f);
/*    */     
/*    */     try {
/* 41 */       file = new File(file.getCanonicalPath());
/*    */     }
/* 43 */     catch (IOException e) {
/*    */       
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 48 */     if (!file.exists()) {
/*    */       
/* 50 */       out.println(String.valueOf(f) + " does not exist");
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 55 */     if (!file.isDirectory()) {
/*    */       
/* 57 */       out.println(String.valueOf(f) + " is not a directory!");
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     source.setCurrentDir(file);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 66 */     return "cd";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\console\ChangeDirectoryCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */