package matyi.simplefinally;

public class ExplanationOfSimpleFinally implements SimpleFinally {

  @Override
  public void simpleFinally(Runnable operation, Runnable afterOperation)
      throws Throwable {
    Throwable operationException = null;
    try {
      operation.run();
    } catch (Throwable t) {
      operationException = t;
    }
    if (operationException != null) {
      afterOperation.run();
      throw operationException;
    }
    afterOperation.run();
  }

}
