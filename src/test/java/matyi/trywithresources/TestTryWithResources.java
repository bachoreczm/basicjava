package matyi.trywithresources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

public abstract class TestTryWithResources {

  private static final byte[] BYTES = new byte[0];
  protected static final String OPERATION_EX_MSG = "operationException";
  private static final String CLOSE_EXCEPTION_MSG = "closeException";
  protected static final String INIT_EX_MSG = "initializationException";

  private TryWithResources tryWithResourceLogic;

  /**
   * Initializes the business-logic.
   *
   * @param tryWithResourceLogic
   *          the logic of the control statement
   */
  public TestTryWithResources(TryWithResources tryWithResourceLogic) {
    this.tryWithResourceLogic = tryWithResourceLogic;
  }

  @Test
  public void ifEverythingOkThenAutoCloseResource() throws Throwable {
    // given
    final ByteArrayInputStreamStub is = new ByteArrayInputStreamStub(BYTES);
    Initializer<AutoCloseable> initializer = createInitializer(is);
    Runnable operation = createEmptyOperation();

    // when
    tryWithResourceLogic.runWithAutoClose(initializer, operation);

    // then
    assertEquals(1, is.numberOfCloseCalled());
  }

  @Test
  public void noNullpointerWhenResourceIsNull() throws Throwable {
    // given
    final ByteArrayInputStreamStub is = null;
    Initializer<AutoCloseable> initializer = createInitializer(is);
    Runnable operation = createEmptyOperation();

    // when
    tryWithResourceLogic.runWithAutoClose(initializer, operation);
  }

  @Test
  public void closeAfterOperationThrowsException() throws Throwable {
    // given
    final ByteArrayInputStreamStub is = new ByteArrayInputStreamStub(BYTES);
    Initializer<AutoCloseable> initializer = createInitializer(is);
    Runnable operation = createOperationWithException();

    // when
    try {
      tryWithResourceLogic.runWithAutoClose(initializer, operation);
      fail();
    } catch (IllegalStateException ex) {
      // then
      assertEquals(OPERATION_EX_MSG, ex.getMessage());
      assertEquals(1, is.numberOfCloseCalled());
    }
  }

  @Test
  public void closeAndOperationThrowsException() throws Throwable {
    // given
    final ByteArrayInputStreamStub is = new ByteArrayInputStreamStub(BYTES);
    is.enableCloseException();
    Initializer<AutoCloseable> initializer = createInitializer(is);
    Runnable operation = createOperationWithException();

    // when
    try {
      tryWithResourceLogic.runWithAutoClose(initializer, operation);
      fail();
    } catch (Throwable ex) {
      assertEquals(OPERATION_EX_MSG, ex.getMessage());
      assertEquals(1, ex.getSuppressed().length);
      assertEquals(CLOSE_EXCEPTION_MSG, ex.getSuppressed()[0].getMessage());
    }
  }

  @Test
  public void initializationThrowsException() throws Throwable {
    // given
    Initializer<AutoCloseable> initializer = createErrorInitializer();
    Runnable operation = createOperationWithException();

    // when
    try {
      tryWithResourceLogic.runWithAutoClose(initializer, operation);
      fail();
    } catch (Throwable ex) {
      assertEquals(INIT_EX_MSG, ex.getMessage());
      assertEquals(0, ex.getSuppressed().length);
    }
  }

  @Test
  public void closeThrowsException() {
    // given
    final ByteArrayInputStreamStub is = new ByteArrayInputStreamStub(BYTES);
    is.enableCloseException();
    Initializer<AutoCloseable> initializer = createInitializer(is);
    Runnable operation = createEmptyOperation();

    // when
    try {
      tryWithResourceLogic.runWithAutoClose(initializer, operation);
      fail();
    } catch (Throwable ex) {
      assertEquals(CLOSE_EXCEPTION_MSG, ex.getMessage());
      assertEquals(0, ex.getSuppressed().length);
    }
  }

  @Test
  public void nullResourceAndOperationThrowsException() throws Throwable {
    // given
    final ByteArrayInputStreamStub is = null;
    Initializer<AutoCloseable> initializer = createInitializer(is);
    Runnable operation = createOperationWithException();

    // when
    try {
      tryWithResourceLogic.runWithAutoClose(initializer, operation);
      fail();
    } catch (IllegalStateException ex) {
      assertEquals(OPERATION_EX_MSG, ex.getMessage());
      assertEquals(0, ex.getSuppressed().length);
    }
  }

  private Initializer<AutoCloseable>
      createInitializer(final ByteArrayInputStream is) {
    Initializer<AutoCloseable> initializer = new Initializer<AutoCloseable>() {

      @Override
      public AutoCloseable init() {
        return is;
      }
    };
    return initializer;
  }

  private Initializer<AutoCloseable> createErrorInitializer() {
    Initializer<AutoCloseable> initializer = new Initializer<AutoCloseable>() {

      @Override
      public AutoCloseable init() {
        throw new IllegalStateException(INIT_EX_MSG);
      }
    };
    return initializer;
  }

  private Runnable createEmptyOperation() {
    return new Runnable() {

      @Override
      public void run() {
      }
    };
  }

  private Runnable createOperationWithException() {
    return new Runnable() {

      @Override
      public void run() {
        throw new IllegalStateException(OPERATION_EX_MSG);
      }
    };
  }

  private static class ByteArrayInputStreamStub extends ByteArrayInputStream {

    private int closeCalled = 0;
    private boolean closeExceptionEnabled = false;

    public ByteArrayInputStreamStub(byte[] buf) {
      super(buf);
    }

    @Override
    public void close() throws IOException {
      if (closeExceptionEnabled) {
        throw new IOException(CLOSE_EXCEPTION_MSG);
      }
      super.close();
      ++closeCalled;
    }

    private void enableCloseException() {
      closeExceptionEnabled = true;
    }

    private int numberOfCloseCalled() {
      return closeCalled;
    }
  }
}
