package com.ankamagames.framework.kernel.core.resource;

public interface FileLoader {
  void addFileLoaderEventListener(FileLoaderEventListener paramFileLoaderEventListener);
  
  void removeFileLoaderEventLstener(FileLoaderEventListener paramFileLoaderEventListener);
  
  void fireOnLoadStartEvent(String paramString);
  
  void fireOnLoadCompleteEvent(String paramString);
  
  void fireOnLoadErrorEvent(String paramString1, String paramString2);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\FileLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */