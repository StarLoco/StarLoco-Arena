package com.ankamagames.framework.fileFormat.document;

public interface DocumentContainerEventsHandler {
  void onLoadBegin(DocumentContainer paramDocumentContainer);
  
  void onLoadComplete(DocumentContainer paramDocumentContainer);
  
  void onLoadError(DocumentContainer paramDocumentContainer, String paramString);
  
  void onSaveBegin(DocumentContainer paramDocumentContainer);
  
  void onSaveComplete(DocumentContainer paramDocumentContainer);
  
  void onSaveError(DocumentContainer paramDocumentContainer, String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentContainerEventsHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */