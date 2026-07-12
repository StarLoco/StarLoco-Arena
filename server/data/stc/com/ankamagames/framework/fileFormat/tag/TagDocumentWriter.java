/*    */ package com.ankamagames.framework.fileFormat.tag;
/*    */ 
/*    */ import com.ankamagames.framework.fileFormat.io.OutputBitStream;
/*    */ import com.ankamagames.framework.fileFormat.tag.records.TagDocumentHeader;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public class TagDocumentWriter
/*    */ {
/*    */   private OutputBitStream m_bitStream;
/*    */   private TagDocument m_document;
/*    */   
/*    */   public TagDocumentWriter(TagDocument document, OutputStream stream)
/*    */   {
/* 34 */     this.m_bitStream = new OutputBitStream(stream);
/* 35 */     this.m_document = document;
/*    */   }
/*    */   
/*    */   /* Error */
/*    */   public void write()
/*    */     throws IOException
/*    */   {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield 68	com/ankamagames/framework/fileFormat/tag/TagDocumentWriter:m_document	Lcom/ankamagames/framework/fileFormat/tag/TagDocument;
/*    */     //   4: invokevirtual 75	com/ankamagames/framework/fileFormat/tag/TagDocument:getTags	()Ljava/util/ArrayList;
/*    */     //   7: invokestatic 79	com/ankamagames/framework/fileFormat/tag/records/tags/TagWriter:writeTags	(Ljava/util/ArrayList;)[B
/*    */     //   10: astore_1
/*    */     //   11: bipush 8
/*    */     //   13: aload_1
/*    */     //   14: arraylength
/*    */     //   15: iadd
/*    */     //   16: i2l
/*    */     //   17: lstore_2
/*    */     //   18: aload_0
/*    */     //   19: lload_2
/*    */     //   20: invokespecial 76	com/ankamagames/framework/fileFormat/tag/TagDocumentWriter:writeHeader	(J)V
/*    */     //   23: aload_0
/*    */     //   24: getfield 68	com/ankamagames/framework/fileFormat/tag/TagDocumentWriter:m_document	Lcom/ankamagames/framework/fileFormat/tag/TagDocument;
/*    */     //   27: invokevirtual 73	com/ankamagames/framework/fileFormat/tag/TagDocument:isCompressed	()Z
/*    */     //   30: ifeq +10 -> 40
/*    */     //   33: aload_0
/*    */     //   34: getfield 67	com/ankamagames/framework/fileFormat/tag/TagDocumentWriter:m_bitStream	Lcom/ankamagames/framework/fileFormat/io/OutputBitStream;
/*    */     //   37: invokevirtual 70	com/ankamagames/framework/fileFormat/io/OutputBitStream:enableCompression	()V
/*    */     //   40: aload_0
/*    */     //   41: getfield 67	com/ankamagames/framework/fileFormat/tag/TagDocumentWriter:m_bitStream	Lcom/ankamagames/framework/fileFormat/io/OutputBitStream;
/*    */     //   44: aload_1
/*    */     //   45: invokevirtual 71	com/ankamagames/framework/fileFormat/io/OutputBitStream:writeBytes	([B)V
/*    */     //   48: goto +20 -> 68
/*    */     //   51: astore 4
/*    */     //   53: aload_0
/*    */     //   54: getfield 67	com/ankamagames/framework/fileFormat/tag/TagDocumentWriter:m_bitStream	Lcom/ankamagames/framework/fileFormat/io/OutputBitStream;
/*    */     //   57: invokevirtual 69	com/ankamagames/framework/fileFormat/io/OutputBitStream:close	()V
/*    */     //   60: goto +5 -> 65
/*    */     //   63: astore 5
/*    */     //   65: aload 4
/*    */     //   67: athrow
/*    */     //   68: aload_0
/*    */     //   69: getfield 67	com/ankamagames/framework/fileFormat/tag/TagDocumentWriter:m_bitStream	Lcom/ankamagames/framework/fileFormat/io/OutputBitStream;
/*    */     //   72: invokevirtual 69	com/ankamagames/framework/fileFormat/io/OutputBitStream:close	()V
/*    */     //   75: goto +5 -> 80
/*    */     //   78: astore 5
/*    */     //   80: return
/*    */     // Line number table:
/*    */     //   Java source line #45	-> byte code offset #0
/*    */     //   Java source line #46	-> byte code offset #11
/*    */     //   Java source line #49	-> byte code offset #18
/*    */     //   Java source line #50	-> byte code offset #23
/*    */     //   Java source line #51	-> byte code offset #33
/*    */     //   Java source line #55	-> byte code offset #40
/*    */     //   Java source line #57	-> byte code offset #51
/*    */     //   Java source line #59	-> byte code offset #53
/*    */     //   Java source line #60	-> byte code offset #63
/*    */     //   Java source line #62	-> byte code offset #65
/*    */     //   Java source line #59	-> byte code offset #68
/*    */     //   Java source line #60	-> byte code offset #78
/*    */     //   Java source line #63	-> byte code offset #80
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	81	0	this	TagDocumentWriter
/*    */     //   10	35	1	tagsBuffer	byte[]
/*    */     //   17	3	2	fileLength	long
/*    */     //   51	15	4	localObject	Object
/*    */     //   63	1	5	localException	Exception
/*    */     //   78	1	5	localException1	Exception
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   0	51	51	finally
/*    */     //   53	60	63	java/lang/Exception
/*    */     //   68	75	78	java/lang/Exception
/*    */   }
/*    */   
/*    */   private void writeHeader(long fileLength)
/*    */     throws IOException
/*    */   {
/* 66 */     TagDocumentHeader header = this.m_document.getHeader();
/* 67 */     header.setFileLength(fileLength);
/* 68 */     header.write(this.m_bitStream);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\tag\TagDocumentWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */