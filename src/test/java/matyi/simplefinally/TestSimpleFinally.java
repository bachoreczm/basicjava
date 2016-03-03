package matyi.simplefinally;

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
    assertEquals(1, operation.numberOfCall);
    assertEquals(1, afterOperation.numberOfCall);
    assertTrue(operation.lastEndTime <= afterOperation.lastBeginTime);
  }

  @Test
  public void operationThrowsExceptionButAfterMethodRunsBeforeException() {
    // given
    MockedRunnable operation = MockedRunnable
        .createRunnableWithException(OPERATION_EXCEPTION);
    MockedRunnable afterOperation = MockedRunnable.createRunnable();

    // when
    try {
      simpleFinally.simpleFinally(operation, afterOperation);
      fail();
    } catch (Throwable ex) {
      // then
      assertEquals(1, afterOperation.numberOfCall);
      assertEquals(OPERATION_EXCEPTION, ex.getMessage());
    }
  }

  @Test
  public void everythingThrowsException() {
    // given
    MockedRunnable operation = MockedRunnable
        .createRunnableWithException(OPERATION_EXCEPTION);
    MockedRunnable afterOperation = MockedRunnable
        .createRunnableWithException(AFTER_EXCEPTION);

    // when
    try {
      simpleFinally.simpleFinally(operation, afterOperation);
      fail();
    } catch (Throwable ex) {
      // then
      assertEquals(AFTER_EXCEPTION, ex.getMessage());
    }
  }

  private static final class MockedRunnable implements Runnable {

    private final boolean runThrowsException;
    private int numberOfCall = 0;
    private long lastBeginTime;
    private long lastEndTime;
    private String exceptionMessage;

    private MockedRunnable(boolean runThrowsException) {
      this.runThrowsException = runThrowsException;
    }

    private static MockedRunnable createRunnableWithException(String msg) {
      MockedRunnable mockedRunnable = new MockedRunnable(true);
      mockedRunnable.exceptionMessage = msg;
      return mockedRunnable;
    }

    private static MockedRunnable createRunnable() {
      return new MockedRunnable(false);
    }

    @Override
    public void run() {
      lastBeginTime = System.currentTimeMillis();
      ++numberOfCall;
      if (runThrowsException) {
        lastEndTime = System.currentTimeMillis();
        throw new IllegalStateException(exceptionMessage);
      }
      lastEndTime = System.currentTimeMillis();
    }
  }
}
