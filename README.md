# Java utilities and some language element behavior

## Try-with-resources control statement

In the ["trywithresources" package](https://github.com/bachoreczm/basicjava/tree/master/src/main/java/matyi/trywithresources)
I implemented a class, `ExplanationOfTryWithResources`, that has a very similar  
responsibility than the try-with-resources statement has.
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
2. Everything is ok (without exception), but our resource is null, so nothing happens after `doSomething`.
3. `initResource` throws an exception, so `doSomething` and `resource.close()` aren't called by the JVM.
4. `doSomething` throws an exception, but before we get the exception, the JVM closes our resource.
5. Everything is ok during opening and `doSomething`, but `resource.close()` throws an exception.
6. `doSomething` and `resource.close()` both throw exceptions. We only get the exception of `doSomething` directly, and the other exception, thrown by 
the close operation will be suppressed. We can retrieve the suppressed exception by calling the `Throwable.getSuppressed` method of the exception thrown by the `doSomething` call.

## Try-finally control statement

In the ["simplefinally" package](https://github.com/bachoreczm/basicjava/tree/master/src/main/java/matyi/simplefinally)
I implemented a class, `ExplanationOfSimpleFinally`, that has a very similar  
responsibility than the try-finally statement has.
[What is try-finally?](https://docs.oracle.com/javase/tutorial/essential/exceptions/finally.html).

The motivation is the same as in the previous section.
Eclemma ([the Eclipse code coverage tool](http://eclemma.org/))
showed that it has 2 branches (instead of one, what we thought (maybe) after the first look).

```java
try {
  doSomething();
} finally {
  after();
}
```

1. Everything is ok (without exception), JVM call `doSomething` and after that `after` method.
2. `doSomething` throws exception, but before we get it, JVM call `after` method.
