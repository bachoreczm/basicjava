package matyi.finallywithcatchblocks;

import static matyi.simplefinally.MockedRunnable.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Test;

import matyi.simplefinally.MockedRunnable;

public abstract class TestFinallyWithCatchBlocks {

  private FinallyWithCatchBlocks statement;

  public TestFinallyWithCatchBlocks(FinallyWithCatchBlocks statement) {
    this.statement = statement;
  }

  @Test
  public void everythingOk() throws Throwable {
    // given
    MockedRunnable operation = createRunnable();
    MockedRunnable after = createRunnable();

    // when
    statement.finallyWithCatchBlocks(operation, null, null, after);

    // then
    assertEquals(1, operation.getNumberOfCall());
    assertEquals(1, after.getNumberOfCall());
  }

  @Test
  public void operationThrowsIllegalArgumentException() throws Throwable {
    // given
    MockedRunnable operation = createRunnableWithIllegalArgumentException("");
    MockedRunnable catch2 = createRunnable();
    MockedRunnable after = createRunnable();

    // when
    statement.finallyWithCatchBlocks(operation, null, catch2, after);

    // then
    assertEquals(1, operation.getNumberOfCall());
    assertEquals(1, catch2.getNumberOfCall());
    assertEquals(1, after.getNumberOfCall());
  }

  @Test
  public void operationThrowsIllegalStateException() throws Throwable {
    // given
    MockedRunnable operation = createRunnableWithIllegalStateException("");
    MockedRunnable catch1 = createRunnable();
    MockedRunnable after = createRunnable();

    // when
    statement.finallyWithCatchBlocks(operation, catch1, null, after);

    // then
    assertEquals(1, operation.getNumberOfCall());
    assertEquals(1, catch1.getNumberOfCall());
    assertEquals(1, after.getNumberOfCall());
  }

  @Test
  public void operationThrowsRuntimeException() throws Throwable {
    // given
    MockedRunnable operation = createRunnableWithRuntimeException("");
    MockedRunnable after = createRunnable();

    // when
    try {
      statement.finallyWithCatchBlocks(operation, null, null, after);
      fail();
    } catch (RuntimeException ex) {
      // then
      assertEquals(1, operation.getNumberOfCall());
      assertEquals(1, after.getNumberOfCall());
      assertFalse(ex instanceof IllegalArgumentException);
      assertFalse(ex instanceof IllegalStateException);
    }
  }
}
