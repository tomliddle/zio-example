A basic zio app to explain the basics of ZIO


1. ZIO is a functional effect system (like monix, cats effect etc.)
2. ZIO values are passed to the ZIO runtime for execution and are run on fast lightweight green threads called fibers 
3. Console.printLine(“Hello, ZIO!”)  is a functional effect. Description of an operation, not a command, passed to zio runtime to execute [example1]
4. ZIO types three type parameters - Requirements / Error / Result
5. ZIO aliases e.g. IO[E, A] === ZIO[Any, E, A]
6. Writing two ZIO effects must be flatMapped together or only the last one will be executed (different ways to code this) [example2]
7. There are different ways to flatMap, e.g. <* or for comprehensions [example3, example4, example5]
8. A basic rule of thumb is: a method should never accept a ZIO value parameter, it should only return it. 
9. Error types - In a for comprehension, Error types must match, or you will get a compile error. mapError can help [example6]
10. Succeed / attempt can wrap expressions. ZIO.succeed for pure code, no exceptions, ZIO.attempt for exceptions [example7]
11. Layers (requires services and provides services). ZIO's dependency injection. [example8, example9, example10, example11]


References
* https://zio.dev/overview/getting-started
* https://www.baeldung.com/scala/zio-intro
* https://medium.com/wix-engineering/5-pitfalls-to-avoid-when-starting-to-work-with-zio-adefdc7d2d5c
