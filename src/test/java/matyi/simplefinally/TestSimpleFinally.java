package matyi.simplefinally;

import static matyi.simplefinally.MockedRunnable.createRunnableWithIllegalStateException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public abstract class TestSimpleFinally {

  private static final String OPERATION_EXCEPTION = "operationException";
  private static final String AFTER_EXCEPTION = "afterOperationException";

  private SimpleFinally simpleFinally;

  /**
   * Stores the current try-finally implementation.
   *
   * @param simpleFinally
   *          current try finally implementation.
   */
  public TestSimpleFinally(SimpleFinally simpleFinally) {
    this.simpleFinally = simpleFinally;
  }

  @Test
  public void everythingOk() throws Throwable {
    // given
    MockedRunnable operation = MockedRunnable.createRunnable();
    MockedRunnable afterOperation = MockedRunnable.createRunnable();

    // when
    simpleFinally.simpleFinally(operation, afterOperation);

    // then
    assertEquals(1, operation.getNumberOfCall());
    assertEquals(1, afterOperation.getNumberOfCall());
    assertTrue(operation.getLastEndTime() <= afterOperation.getLastBeginTime());
  }

  @Test
  public void operationThrowsExceptionButAfterMethodRunsBeforeException() {
    // given
    MockedRunnable operation = createRunnableWithIllegalStateException(
        OPERATION_EXCEPTION);
    MockedRunnable afterOperation = MockedRunnable.createRunnable();

    // when
    try {
      simpleFinally.simpleFinally(operation, afterOperation);
      fail();
    } catch (Throwable ex) {
      // then
      assertEquals(1, afterOperation.getNumberOfCall());
      assertEquals(OPERATION_EXCEPTION, ex.getMessage());
    }
  }

  @Test
  public void everythingThrowsException() {
    // given
    MockedRunnable operation = createRunnableWithIllegalStateException(
        OPERATION_EXCEPTION);
    MockedRunnable afterOperation = createRunnableWithIllegalStateException(
        AFTER_EXCEPTION);

    // when
    try {
      simpleFinally.simpleFinally(operation, afterOperation);
      fail();
    } catch (Throwable ex) {
      // then
      assertEquals(AFTER_EXCEPTION, ex.getMessage());
      assertEquals(0, ex.getSuppressed().length);
    }
  }
}
