[![Build Status](https://travis-ci.org/vovak/astminer.svg?branch=master)](https://travis-ci.org/vovak/astminer)

# AstMiner (JS support)
A tool/library for mining of [path-based representations of code](https://arxiv.org/pdf/1803.09544.pdf).
*Work in progress.*

### Version history

#### This version
* Supporting <a href="https://github.com/Jacarte/jastgen" target="_blank">generic AST json structure</a> for js files (parsed using babel-parser)


#### 0.2

* Mining of ASTs

#### 0.1
* AstMiner is available via Maven Central
* Support of Java and Python
* Mining of [path-based representations of code](https://arxiv.org/pdf/1803.09544.pdf)

## About
This is an offspring of an internal utility from our ongoing research project.

Currently it supports extraction of path-based representations from code in Java and Python, but it is designed to be very easily extensible.

The default output format is inspired by [code2vec](https://github.com/tech-srl/code2vec).

## Usage

### Import

Library is available via Maven Central repository. You can add the dependency in your `build.gradle` file:

```
dependencies {
    compile "io.github.vovak:astminer:0.1"
}
```

### Examples

A few [simple usage examples](src/main/kotlin/astminer/examples) can be run with `./gradlew run`.

A somewhat more verbose [example of usage in Java](src/main/kotlin/astminer/examples/AllJavaFiles.kt) is available as well.

## Extend to other languages

A new programming language can be supported in a few simple steps:
1. Add the corresponding [ANTLR4 grammar file](https://github.com/antlr/grammars-v4) to the `antlr` directory;
2. Run the `antlr4` Gradle task to generate the parser;
3. Implement a very minimal wrapper around the generated parser.
See [JavaParser](src/main/kotlin/astminer/parse/antlr/java/JavaParser.kt) or [PythonParser](src/main/kotlin/astminer/parse/antlr/python/PythonParser.kt) for reference.

## Contribution
We believe that, thanks to extensibility, AstMiner could be valuable for many other researchers.
However, our vision of potential applications is tunneled by our own work.

Please help make AstMiner easier to use by sharing your potential use cases.
We would also appreciate pull requests with code improvements, more usage examples, documentation, etc.
