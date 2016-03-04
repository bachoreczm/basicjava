package matyi.finallywithcatchblocks;

public interface FinallyWithCatchBlocks {

  /**
   * Try-catch-finally with 2 catch blocks.
   *
   * @param operation
   *          try-operation
   * @param catch1
   *          catch1-operation
   * @param catch2
   *          catch2-operation
   * @param after
   *          finally-operation
   * @throws Throwable
   *           if something error occurs.
   */
  void finallyWithCatchBlocks(Runnable operation, Runnable catch1,
      Runnable catch2, Runnable after) throws Throwable;
}
