package com.ankamagames.framework.fileFormat.document;

public abstract interface DocumentContainerEventsHandler
{
  public abstract void onLoadBegin(DocumentContainer paramDocumentContainer);
  
  public abstract void onLoadComplete(DocumentContainer paramDocumentContainer);
  
  public abstract void onLoadError(DocumentContainer paramDocumentContainer, String paramString);
  
  public abstract void onSaveBegin(DocumentContainer paramDocumentContainer);
  
  public abstract void onSaveComplete(DocumentContainer paramDocumentContainer);
  
  public abstract void onSaveError(DocumentContainer paramDocumentContainer, String paramString);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentContainerEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */