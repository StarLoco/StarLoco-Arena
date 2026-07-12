package com.ankamagames.framework.fileFormat.document;

public interface DocumentAccessor {
  void open(String paramString) throws Exception;
  
  boolean create(String paramString) throws Exception;
  
  void close() throws Exception;
  
  void read(DocumentContainer paramDocumentContainer);
  
  void write(DocumentContainer paramDocumentContainer);
  
  DocumentContainer getNewDocumentContainer();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentAccessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */