package matyi.trywithresources;

public interface Initializer<T> {

  /**
   * Initializes a resource.
   *
   * @return the initialized resource.
   */
  T init();
}
