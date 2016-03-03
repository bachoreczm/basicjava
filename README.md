# Java utilities and some language element behavior

## Try-with-resources control statement

In the `trywithresources` package I implemented a class 
(`ExplanationOfTryWithResources`), that has a very similar  
responsibility than the try-with-resources statement has 
[What is try-with-resources?](https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html).

The motivation comes from testing, and from striving for 100% test 
coverage. It would be nice to be able to cover all branches of 
this control statement with [jacoco](http://eclemma.org/jacoco/). 
Eclemma ([the Eclipse code coverage tool](http://eclemma.org/))
showed that it has 8 branches. Actually, it has only 6.

```java
try (AutoCloseable resource = initResource()) {
  doSomething(resource);
}
```

1. Everything is ok (without exception), after `doSomething` in an invisible finally block the JVM closes our resource.
2. EVerything is ok (without exception), but our resource is null, so nothing happens afte `doSomething`.
3. `initResource` throws exception, so we get an exception (`doSomething` and `resource.close()` aren't called by JVM).
4. `doSomething` throws exception, but before we get the exception, JVM closes our resource.
5. Everthing is ok, but `resource.close();`throws exception.
6. `doSomething` and `resource.close();` throw exception. We get the exception of `doSomething` and its suppressed exception is what we get from the close operation.
