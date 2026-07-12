/*    */ package org.postgresql.core;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import java.util.Properties;
/*    */ import org.postgresql.core.v2.ConnectionFactoryImpl;
/*    */ import org.postgresql.core.v3.ConnectionFactoryImpl;
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
/*    */ public abstract class ConnectionFactory
/*    */ {
/* 32 */   private static final Object[][] versions = new Object[][] { { "3", new ConnectionFactoryImpl() }, { "2", new ConnectionFactoryImpl() } };
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
/*    */   public static ProtocolConnection openConnection(String host, int port, String user, String database, Properties info) throws SQLException {
/* 56 */     String protoName = info.getProperty("protocolVersion");
/*    */     
/* 58 */     for (int i = 0; i < versions.length; i++) {
/*    */       
/* 60 */       String versionProtoName = (String)versions[i][0];
/* 61 */       if (protoName == null || protoName.equals(versionProtoName)) {
/*    */ 
/*    */         
/* 64 */         ConnectionFactory factory = (ConnectionFactory)versions[i][1];
/* 65 */         ProtocolConnection connection = factory.openConnectionImpl(host, port, user, database, info);
/* 66 */         if (connection != null)
/* 67 */           return connection; 
/*    */       } 
/*    */     } 
/* 70 */     throw new PSQLException(GT.tr("A connection could not be made using the requested protocol {0}.", protoName), PSQLState.CONNECTION_UNABLE_TO_CONNECT);
/*    */   }
/*    */   
/*    */   public abstract ProtocolConnection openConnectionImpl(String paramString1, int paramInt, String paramString2, String paramString3, Properties paramProperties) throws SQLException;
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\ConnectionFactory.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */