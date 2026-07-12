/*    */ package org.fenggui.console;
/*    */ 
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
/*    */ public class PwdCommand
/*    */   implements ICommand
/*    */ {
/*    */   public void execute(PrintStream out, Console source, String[] args) {
/* 29 */     out.println(source.getCurrentDir().getAbsolutePath());
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCommand() {
/* 34 */     return "pwd";
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\console\PwdCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */