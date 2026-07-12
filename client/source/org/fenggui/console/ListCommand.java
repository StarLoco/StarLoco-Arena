/*    */ package org.fenggui.console;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
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
/*    */ 
/*    */ public class ListCommand
/*    */   implements ICommand
/*    */ {
/*    */   public String getCommand() {
/* 33 */     return "ls";
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(PrintStream out, Console source, String[] args) {
/* 38 */     File[] list = (new File(String.valueOf(source.getCurrentDir().getAbsolutePath()) + "/.")).listFiles(); byte b; int i;
/*    */     File[] arrayOfFile1;
/* 40 */     for (arrayOfFile1 = list, b = 0, i = arrayOfFile1.length; b < i; ) { File file = arrayOfFile1[b];
/*    */       
/* 42 */       if (file.canRead()) {
/* 43 */         out.print('R');
/*    */       } else {
/* 45 */         out.print('-');
/*    */       } 
/* 47 */       if (file.canWrite()) {
/* 48 */         out.print('W');
/*    */       } else {
/* 50 */         out.print('-');
/*    */       } 
/* 52 */       Calendar c = Calendar.getInstance();
/* 53 */       c.setTime(new Date(file.lastModified()));
/*    */       
/* 55 */       out.print(" " + 
/* 56 */           ensureLength(c.get(5), 2, '0') + "-" + 
/* 57 */           ensureLength(c.get(2), 2, '0') + "-" + 
/* 58 */           ensureLength(c.get(1), 2, '0') + " ");
/*    */       
/* 60 */       int size = (int)file.length();
/* 61 */       if (size < 1000) {
/* 62 */         out.print(String.valueOf(ensureLength(size, 3, ' ')) + " B ");
/*    */       
/*    */       }
/* 65 */       else if (size / Math.pow(2.0D, 10.0D) < 1000.0D) {
/* 66 */         out.print(String.valueOf(ensureLength((int)(size / Math.pow(2.0D, 10.0D)), 3, ' ')) + " KB");
/*    */       
/*    */       }
/* 69 */       else if (size / Math.pow(2.0D, 20.0D) < 1000.0D) {
/* 70 */         out.print(String.valueOf(ensureLength((int)(size / Math.pow(2.0D, 20.0D)), 3, ' ')) + " MB");
/*    */       
/*    */       }
/* 73 */       else if (size / Math.pow(2.0D, 30.0D) < 1000.0D) {
/* 74 */         out.print(String.valueOf(ensureLength((int)(size / Math.pow(2.0D, 30.0D)), 3, ' ')) + " GB");
/*    */       } 
/*    */ 
/*    */       
/* 78 */       out.println(" " + file.getName());
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private String ensureLength(int d, int length, char add) {
/* 86 */     int i = d;
/*    */     String str;
/* 88 */     for (; i.length() < length; str = String.valueOf(add) + i);
/*    */     
/* 90 */     return str;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\console\ListCommand.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */