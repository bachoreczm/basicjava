package matyi.finallywithcatchblocks;

public class ExplanationOfFinallyWithCatchBlocks
    implements FinallyWithCatchBlocks {

  @Override
  public void finallyWithCatchBlocks(Runnable operation, Runnable catch1,
      Runnable catch2, Runnable after) throws Throwable {
    Throwable throwable = null;
    try {
      operation.run();
    } catch (IllegalStateException ex) {
      catch1.run();
    } catch (IllegalArgumentException ex) {
      catch2.run();
    } catch (Throwable t) {
      throwable = t;
    }
    after.run();
    if (throwable != null) {
      throw throwable;
    }
  }

}
