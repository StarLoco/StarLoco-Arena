package com.sun.gluegen.runtime;

interface DynamicLinker {
  long openLibrary(String paramString);
  
  long lookupSymbol(long paramLong, String paramString);
  
  void closeLibrary(long paramLong);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\DynamicLinker.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */