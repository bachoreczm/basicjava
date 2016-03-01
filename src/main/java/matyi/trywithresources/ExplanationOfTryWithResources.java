package matyi.trywithresources;

import java.io.IOException;

public class ExplanationOfTryWithResources implements TryWithResources {

  @Override
  public void runWithAutoClose(Initializer<AutoCloseable> initializer,
      Runnable operation) throws Throwable {
    AutoCloseable resource = null;
    Throwable operationException = null;
    Throwable closeException = null;
    Throwable initException = null;
    try {
      resource = initializer.init();
    } catch (Throwable t) {
      initException = t;
    }
    if (initException == null) {
      try {
        operation.run();
      } catch (Throwable t) {
        operationException = t;
      }
      if (resource != null) {
        try {
          resource.close();
        } catch (IOException ex) {
          closeException = ex;
        }
      }
    }
    if (initException != null) {
      throw initException;
    }
    if (operationException == null && closeException != null) {
      throw closeException;
    }
    if (operationException != null && closeException == null) {
      throw operationException;
    }
    if (closeException != null) {
      operationException.addSuppressed(closeException);
      throw operationException;
    }
  }
}
