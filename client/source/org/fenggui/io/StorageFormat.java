package org.fenggui.io;

public interface StorageFormat<T, S> {
  S encode(T paramT) throws EncodingException;
  
  T decode(S paramS) throws EncodingException;
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\StorageFormat.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */