# an5 V0.2

- a nETWORK LANGUAGE WITH 5 CLASSES
- an5 - is a network modelling language designed to allow description and
processing and simulation of networks
- The language syntax is derived from java but without any methods.
So it is data definition only, with class & interface used for schema definition
- The 5 Classes are: {network, element, interface, link, path}

Updates:
- 0.2 - remove use of "abstract" and replace with "goal", "handler" and "constraint" to make goal definition simpler/clearer

# Build Tools

- Parser: is written with ANTLR
- Project:  build with Eclipse with ANTLR XText Facit and maven Antlr plugin
- Git: where Eclipse Workspace is in Git Workspace

# Building with Eclipse

1. Clone git repository
2. Install Eclipse
3. Open repository Eclipse project
4. Run the compiler to generate sample classes (see: src/main/models/dc-bb.an5) 
5. Build the Sample Network builder (see: src/test/java/BuildMiniNetwork.java)
6. Run Network Builder via Eclipse

# Authors and contributors

- John Hartley - Graphica Software/Dokmai Pty Ltd

# References & Links

- [ANTLR](http://www.antlr.org) - Compiler Generation Tool
- Modern Compiler Implementation in Java - 2nd Ed - [MiniJava Project](https://www.cambridge.org/resources/052182060X/) as an5 is also a based on Java language syntax, this has useful examples and an5 symbol table handling is based on this
- [an5 - Intelligent Network Design](https://just.graphica.com.au/intelligent-network-design/) - design considerations and architecture of initial implementation  



