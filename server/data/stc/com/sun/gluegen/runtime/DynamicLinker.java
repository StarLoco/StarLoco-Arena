package com.sun.gluegen.runtime;

abstract interface DynamicLinker
{
  public abstract long openLibrary(String paramString);
  
  public abstract long lookupSymbol(long paramLong, String paramString);
  
  public abstract void closeLibrary(long paramLong);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\sun\gluegen\runtime\DynamicLinker.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       0.7.1
 */