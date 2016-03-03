package matyi.simplefinally;

public class JavaSimpleFinally implements SimpleFinally {

  @Override
  public void simpleFinally(Runnable operation, Runnable afterOperation) {
    try {
      operation.run();
    } finally {
      afterOperation.run();
    }
  }
}
