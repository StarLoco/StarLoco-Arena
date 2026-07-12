package com.ankamagames.framework.fileFormat.document;

import java.util.ArrayList;

public interface DocumentContainer {
  DocumentEntry getEntryByName(String paramString);
  
  ArrayList<DocumentEntry> getEntriesByName(String paramString);
  
  void addEventsHandler(DocumentContainerEventsHandler paramDocumentContainerEventsHandler);
  
  void notifyOnLoadBegin();
  
  void notifyOnLoadComplete();
  
  void notifyOnLoadError(String paramString);
  
  void notifyOnSaveBegin();
  
  void notifyOnSaveComplete();
  
  void notifyOnSaveError(String paramString);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */