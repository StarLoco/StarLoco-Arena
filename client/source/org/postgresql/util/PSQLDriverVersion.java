/*    */ package org.postgresql.util;
/*    */ 
/*    */ import java.net.URL;
/*    */ import org.postgresql.Driver;
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
/*    */ public class PSQLDriverVersion
/*    */ {
/* 24 */   public static int buildNumber = 405;
/*    */   
/*    */   public static void main(String[] args) {
/* 27 */     URL url = Driver.class.getResource("/org/postgresql/Driver.class");
/* 28 */     System.out.println(Driver.getVersion());
/* 29 */     System.out.println("Found in: " + url);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresq\\util\PSQLDriverVersion.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */