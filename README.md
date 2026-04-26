# Programming Languages Project

This repository holds my solution for the Programming Languages project at Instituto Superior Técnico, whose main objetive was to implement an interpreter based on big-step environment-based semantics using Java for the X++ language, which is a custom language taught in class. The X++ language features:

- Common arithmetic and boolean operations;
- While loops, if-else and match constructs;
- Variable definition and the inclusion of scopes;
- Function definitions and its application with lambda calculus;
- Mutable state with the concept of references
- Static and lazy lists;
- Recursive types

My solution also includes a static type checker, which support recursive typing, to allow a program to be analysed for type errors before beginning its execution, which could lead to runtime errors. The file [SasyLFSafe0.slf][./SasyLFSafe0.slf] contains a complementary SasyLF type preservation proof that considers a "pairs" type. Further details about the implementation can be found in the [report](./report.pdf).

## How to run

In the root of the repository run the executable "./x++". You can also provide the name of a file containing a snippet of X++ code. You can find some examples to test the tool in the [tests folder](./tests).
