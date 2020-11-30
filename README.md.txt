EX1-The project is building undirectional weighted graph. The project include two classes implemntes the interface of node_info,weighted_graph and wegihted_graph_algo.
 The classes are WGraph_DS and WGraph_Algo.
WGraph_DS implemntes the  weighted_graph interface and the node_info interface as a inner  class.
The classes including the following methodes:
1) getNode- Return the Nodeinfo by its id
2) hasEdge-Check if there is an edge between two nodes
3) getEdge- Return the weight of the edge between the two nodes
4) addNode-Add a new node to the graph with a given key
5) connect-Create an edge between two nodes
6) getV-Return a pointer for a collection representing all the nodes in the graph
7) getV-Returns a Collection containing all the neighbors of the node id
8) removeNode-Delete the node (with the given ID) from the graph
 .and removes all edges connecting to the node
9) removeEdge-Delete an edge connecting  two nodes  from the graph.
The class includes two HashMaps, the first HashMap contains all the nodes in the graph.The seconde HashMap is a HashMap inside HashMap contains all the edges in the graph. 
The inner HashMap containse all the node neighbor ans the weight of the edge.

WGraph_Algo implemntes the weighted_graph_algorithms interface.
The classes including the folowing methodes:
1) init-Init the graph on which this set of algorithms operates on.
2) getGraph-Return the underlying graph of which the class works
3) copy-Compute a deep copy of the weighted graph.
4) isConnected-Check  true if  there is a valid path from every node to each other node.
The methode using in a private methode imlemntes the Breadth-first search Algorithem. The Algorithem make a traversal in every node if there is path leades to him.
5) shortestPathDist-Returns the length of the shortest path between the sourch node to the destination node.
The methode using a private methode implemntes the Dijkstra's algorithm. The methode traversal the graph from the source node to all the nodes in the graph. 
each nodes get a tag represent the distance from the node to the source node.
6) shortestPath-Returns the the shortest path between source node  to destination node as an ordered List of nodes.
This methode using private methode make a traversal with the Dijkstra's algorithm method from the sourch node .The methode  returning a HashMap of parents by the node id.
Because the HashMap start with the desrination node and the path shoulde start with the source node,every node insert to stack  until the source node. 
After finishing insert the noodes to the stack,each nodes insert to a list,because in stack the last node insert is the first  node to get out, the path in the list is in the right order.
7) save-Saves the weighted  graph into a file.
8) load-This method load a graph from a file