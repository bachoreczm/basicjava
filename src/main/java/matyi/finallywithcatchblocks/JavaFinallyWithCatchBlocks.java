package matyi.finallywithcatchblocks;

public class JavaFinallyWithCatchBlocks implements FinallyWithCatchBlocks {

  @Override
  public void finallyWithCatchBlocks(Runnable operation, Runnable catch1,
      Runnable catch2, Runnable after) throws Throwable {
    try {
      operation.run();
    } catch (IllegalStateException ex) {
      catch1.run();
    } catch (IllegalArgumentException ex) {
      catch2.run();
    } finally {
      after.run();
    }
  }
}
