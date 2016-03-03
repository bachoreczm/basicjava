package matyi.simplefinally;

public interface SimpleFinally {

  /**
   * Implements the try finally block.
   *
   * @param operation
   *          running in try block.
   * @param afterOperation
   *          running in finally block.
   * @throws Throwable
   *           thrown by try or finally
   */
  void simpleFinally(Runnable operation, Runnable afterOperation)
      throws Throwable;
}
