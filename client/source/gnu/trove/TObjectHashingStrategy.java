package gnu.trove;

import java.io.Serializable;

public interface TObjectHashingStrategy<T> extends Serializable {
  int computeHashCode(T paramT);
  
  boolean equals(T paramT1, T paramT2);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\gnu\trove\TObjectHashingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */