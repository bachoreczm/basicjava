package matyi.trywithresources;

public interface TryWithResources {

  /**
   * Implementation of try-with-resources control statement.
   *
   * @param initializer
   *          auto-closable resource initializer.
   * @param operation
   *          inner operation.
   * @throws Throwable
   *           if some error occurs.
   */
  void runWithAutoClose(Initializer<AutoCloseable> initializer,
      Runnable operation) throws Throwable;
}
