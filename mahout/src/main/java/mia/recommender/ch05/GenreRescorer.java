/*
 * Source code for listing 5.3
 * 
 */
package mia.recommender.ch05;

import org.apache.mahout.cf.taste.recommender.IDRescorer;

/**
 * 实现对某一类资源进行优先级调整.
 * @author plz
 */
public class GenreRescorer implements IDRescorer {

  private final Genre currentGenre;

  public GenreRescorer(Genre currentGenre) {
    this.currentGenre = currentGenre;
  }

  
@Override
  public double rescore(long itemID, double originalScore) {
    Book book = BookManager.lookupBook(itemID);
    // 提高符合条件的 entity 优先级
    if (book.getGenre().equals(currentGenre)) {
      return originalScore * 1.2;
    }
    return originalScore;
  }
  
  @Override
  public boolean isFiltered(long itemID) {
    Book book = BookManager.lookupBook(itemID);
    return book.isOutOfStock();
  }
}
