package matyi.trywithresources;

public interface TryWithResources {

  public void runWithAutoClose(Initializer<AutoCloseable> initializer,
      Runnable operation) throws Throwable;
}
