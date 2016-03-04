package matyi.simplefinally;

public final class MockedRunnable implements Runnable {

  private final boolean throwsIllegalStateException;
  private final boolean throwsIllegalArgumentException;
  private final boolean throwsRuntimeException;

  private int numberOfCall = 0;
  private long lastBeginTime;
  private long lastEndTime;
  private String exceptionMessage;

  private MockedRunnable(boolean throwsIllegalStateException,
      boolean throwsIllegalArgumentException, boolean throwsRuntimeException) {
    this.throwsIllegalStateException = throwsIllegalStateException;
    this.throwsIllegalArgumentException = throwsIllegalArgumentException;
    this.throwsRuntimeException = throwsRuntimeException;
  }

  public static MockedRunnable
      createRunnableWithIllegalStateException(String msg) {
    MockedRunnable mockedRunnable = new MockedRunnable(true, false, false);
    mockedRunnable.exceptionMessage = msg;
    return mockedRunnable;
  }

  public static MockedRunnable
      createRunnableWithIllegalArgumentException(String msg) {
    MockedRunnable mockedRunnable = new MockedRunnable(false, true, false);
    mockedRunnable.exceptionMessage = msg;
    return mockedRunnable;
  }

  public static MockedRunnable createRunnableWithRuntimeException(String msg) {
    MockedRunnable mockedRunnable = new MockedRunnable(false, false, true);
    mockedRunnable.exceptionMessage = msg;
    return mockedRunnable;
  }

  public static MockedRunnable createRunnable() {
    return new MockedRunnable(false, false, false);
  }

  @Override
  public void run() {
    lastBeginTime = System.currentTimeMillis();
    ++numberOfCall;
    if (throwsIllegalStateException) {
      lastEndTime = System.currentTimeMillis();
      throw new IllegalStateException(exceptionMessage);
    }
    if (throwsIllegalArgumentException) {
      lastEndTime = System.currentTimeMillis();
      throw new IllegalArgumentException(exceptionMessage);
    }
    if (throwsRuntimeException) {
      lastEndTime = System.currentTimeMillis();
      throw new RuntimeException(exceptionMessage);
    }
    lastEndTime = System.currentTimeMillis();
  }

  public int getNumberOfCall() {
    return numberOfCall;
  }

  public long getLastBeginTime() {
    return lastBeginTime;
  }

  public long getLastEndTime() {
    return lastEndTime;
  }
}
