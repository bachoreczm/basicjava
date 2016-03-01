package matyi.trywithresources;

public class JavaTryWithResources implements TryWithResources {

  @Override
  public void runWithAutoClose(Initializer<AutoCloseable> resourceInitializer,
      Runnable operation) throws Exception {
    try (AutoCloseable resource = resourceInitializer.init()) {
      doSomethingWithResource(resource, operation);
    }
  }

  private void doSomethingWithResource(AutoCloseable resource,
      Runnable operation) {
    operation.run();
  }
}
