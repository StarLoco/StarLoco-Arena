package com.ankamagames.framework.fileFormat.document;

public abstract interface DocumentAccessor
{
  public abstract void open(String paramString)
    throws Exception;
  
  public abstract boolean create(String paramString)
    throws Exception;
  
  public abstract void close()
    throws Exception;
  
  public abstract void read(DocumentContainer paramDocumentContainer);
  
  public abstract void write(DocumentContainer paramDocumentContainer);
  
  public abstract DocumentContainer getNewDocumentContainer();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentAccessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */