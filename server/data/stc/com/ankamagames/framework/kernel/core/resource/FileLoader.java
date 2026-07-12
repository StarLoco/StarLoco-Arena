package com.ankamagames.framework.kernel.core.resource;

public abstract interface FileLoader
{
  public abstract void addFileLoaderEventListener(FileLoaderEventListener paramFileLoaderEventListener);
  
  public abstract void removeFileLoaderEventLstener(FileLoaderEventListener paramFileLoaderEventListener);
  
  public abstract void fireOnLoadStartEvent(String paramString);
  
  public abstract void fireOnLoadCompleteEvent(String paramString);
  
  public abstract void fireOnLoadErrorEvent(String paramString1, String paramString2);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\FileLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */