package org.postgresql.core;

public interface Query {
  ParameterList createParameterList();
  
  String toString(ParameterList paramParameterList);
  
  void close();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\postgresql\core\Query.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */