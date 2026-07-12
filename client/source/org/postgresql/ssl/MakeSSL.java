/*    */ package org.postgresql.ssl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.net.Socket;
/*    */ import java.util.Properties;
/*    */ import javax.net.ssl.SSLSocketFactory;
/*    */ import org.postgresql.Driver;
/*    */ import org.postgresql.core.PGStream;
/*    */ import org.postgresql.util.GT;
/*    */ import org.postgresql.util.PSQLException;
/*    */ import org.postgresql.util.PSQLState;
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
/*    */ public class MakeSSL
/*    */ {
/*    */   public static void convert(PGStream stream, Properties info) throws IOException, PSQLException {
/*    */     SSLSocketFactory sSLSocketFactory;
/* 27 */     if (Driver.logDebug) {
/* 28 */       Driver.debug("converting regular socket connection to ssl");
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 34 */     String classname = info.getProperty("sslfactory");
/* 35 */     if (classname == null) {
/*    */       
/* 37 */       sSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
/*    */     }
/*    */     else {
/*    */       
/* 41 */       Object[] args = { info.getProperty("sslfactoryarg") };
/*    */ 
/*    */       
/*    */       try {
/*    */         Constructor constructor;
/*    */         
/* 47 */         Class factoryClass = Class.forName(classname);
/*    */         
/*    */         try {
/* 50 */           constructor = factoryClass.getConstructor(new Class[] { String.class });
/*    */         }
/*    */         catch (NoSuchMethodException nsme) {
/*    */           
/* 54 */           constructor = factoryClass.getConstructor((Class[])null);
/* 55 */           args = null;
/*    */         } 
/* 57 */         sSLSocketFactory = (SSLSocketFactory)constructor.newInstance(args);
/*    */       }
/*    */       catch (Exception e) {
/*    */         
/* 61 */         throw new PSQLException(GT.tr("The SSLSocketFactory class provided {0} could not be instantiated.", classname), PSQLState.CONNECTION_FAILURE, e);
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     Socket newConnection = sSLSocketFactory.createSocket(stream.getSocket(), stream.getHost(), stream.getPort(), true);
/* 66 */     stream.changeSocket(newConnection);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\ssl\MakeSSL.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */