# Table of Contents

1. [Build]
2. [Options]
3. [Decisions]
4. [Details of Implementation]

##Build

Requires Java 8

##Options

###Adjacency Matrix (AM)
AM consumes too much memory and is expensive in terms of memory to add a new vortex.

###Adjacency List (AL)
AL is not as fast as AM from seardh perspective, nut uses much less memory.

###Depth-first search (DFS)
DFS is more complex to use for graph exploration till a certain depth as it was designed for a different goal.

###Breadth-first search (BFS)
As we need only explore a social graph till depth 4, BFS is the fastest option.

##Decisions
###Use AL as a graph representation.

###Use BFS to find a shortest path between two nodes with a depth constraint.

##Details of Implementation
###Transformer class was introduced to pack two integers in one long to improve performance.
###FastGraph class was introduced to represent AL as an array of sets to optimize search of vertexes.