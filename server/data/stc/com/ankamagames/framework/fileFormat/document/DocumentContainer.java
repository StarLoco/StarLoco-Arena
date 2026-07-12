package com.ankamagames.framework.fileFormat.document;

import java.util.ArrayList;

public abstract interface DocumentContainer
{
  public abstract DocumentEntry getEntryByName(String paramString);
  
  public abstract ArrayList<DocumentEntry> getEntriesByName(String paramString);
  
  public abstract void addEventsHandler(DocumentContainerEventsHandler paramDocumentContainerEventsHandler);
  
  public abstract void notifyOnLoadBegin();
  
  public abstract void notifyOnLoadComplete();
  
  public abstract void notifyOnLoadError(String paramString);
  
  public abstract void notifyOnSaveBegin();
  
  public abstract void notifyOnSaveComplete();
  
  public abstract void notifyOnSaveError(String paramString);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\fileFormat\document\DocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */