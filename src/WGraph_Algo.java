package ex1.src;

import java.util.*;
import java.io.*;
import java.io.Serializable;

public class WGraph_Algo implements weighted_graph_algorithms,Serializable {
    private weighted_graph g;
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {

        this.g=g;
    }
    /**
     * @return  the graph of which this class works on.
     */
    @Override
    public weighted_graph getGraph() {
        return g;
    }
    /**
     * Compute a deep copy of this weighted graph.
     */
    @Override
    public weighted_graph copy() {
        if (g == null) return null;
        else {
            weighted_graph copy_graph = new WGraph_DS();
            for (node_info x : g.getV())// copy all the node to the copy graph
            {
                WGraph_DS.NodeData copy_node = new WGraph_DS.NodeData(x);
                copy_graph.addNode(copy_node.getKey());
            }
            for (node_info x : g.getV())// coping all the edges
            {
                for (node_info neighbor : g.getV(x.getKey()))// for every node in the graph copy is edges
                {
                    copy_graph.connect(x.getKey(), neighbor.getKey(), g.getEdge(x.getKey(), neighbor.getKey()));
                }
            }
            return copy_graph;
        }
    }
    /**
     * the methode check if there is a valid path from every node to each other node
     * @return true if there is path and false if not
     */
    @Override
    public boolean isConnected() {

        if((g.nodeSize()==1)||(g.nodeSize()==0) || (g==null)) return true;
        if (g.nodeSize() - 1 > g.edgeSize()) return false; //if the number of edge smaller then the number of Vertices -1
        // then the graph is not connected
        node_info start= g.getV().iterator().next();
        return (bfs(start).size() >= g.nodeSize()-1);  //if the number of verticals in the father hashmap
        // is at least the num of verticals minus 1 then all the Vertices are connected
    }
    /**
     * the methode compute the shortest path between two nodes
     * @param src - start node
     * @param dest - end (target) node
     * @return the length of the shortest path  if no such path returns -1
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        if (g == null)
            return -1;
        if (src == dest)
            return -1;
        if (g.getNode(src) != null && g.getNode(dest) != null) // check if the nodes exciting
        {
            dijkstras(g.getNode(src));
            node_info dest_node = g.getNode(dest);
            if (dest_node.getTag() == Double.POSITIVE_INFINITY)  // tag equals to infinity means there isn't a path to the node
                return -1;
            return dest_node.getTag();  // the tag of the node represent the short distance to the node
        }
        return -1;
    }
    /**
     *the methode compute the shortest path between two nodes as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * @param src - start node
     * @param dest - end (target) node
     * @return List of the nodes in the path between the two nodes, if no such path returns null;
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
       if ((g==null)|| (src==dest))
           return null;
        if (g.getNode(src) != null && g.getNode(dest) != null) {
            HashMap<Integer, node_info> routes = dijkstras(g.getNode(src));
            // the hashmap routes contains all the routes in the graph
            Stack<node_info> reversePath = new Stack();
            reversePath.add(g.getNode(dest));
            node_info temp_node = new WGraph_DS.NodeData(g.getNode(dest));
            while (temp_node.getKey() != src) {
                temp_node = routes.get(temp_node.getKey());// now the temp node is the route of temp node
                reversePath.add(temp_node);
            }
            List<node_info> path = new LinkedList<node_info>();
            while (!reversePath.isEmpty())
                path.add(reversePath.pop());
            return path;
        }
        return null;
    }

    /**
     * Saves this weighted  graph to the given file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved,false if not.
     */
    @Override
    public boolean save(String file) {
        try{
            FileOutputStream file1=new FileOutputStream(file);
            ObjectOutputStream out= new ObjectOutputStream(file1);
            out.writeObject(getGraph());
            out.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - if the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream file1 = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(file1);
            g = (weighted_graph) in.readObject();
            in.close();
            return true;
        } catch (Exception e) {

            return false;
        }
    }
    /**
     * this private method is an implementation of BFS algorithm
     * @param start  node which the algorithm starts from
     * @return a HashMap with the routes in the graph.
     */
    private HashMap bfs(node_info start)
    {
        Queue<node_info> queue= new LinkedList<>();
        HashMap<node_info,Integer> routes= new HashMap< >();
        for(node_info x:g.getV()) // set all the nodes in the graph to unseen- white nodes represent by -1
            x.setTag(-1); // tag represent the distance from the father
        start.setTag(0);
        queue.add(start);

        while (!queue.isEmpty())
        {
            node_info current=queue.poll();
            for (node_info x:g.getV(current.getKey()))
            {
                if (x.getTag()==-1) // if tag equals to -1 its means is un visit by now
                {
                    queue.add(x);
                    x.setTag(current.getTag() + 1);
                    routes.put(current,x.getKey());
                }
            }

        }
        return routes;
    }
    /**
     * this private method is an implementation of Dijkstra's Algorithm
     * @param source the source node
     * @return Hashmap of the routes in the graph
     */
    private HashMap<Integer, node_info> dijkstras(node_info source) {
        PriorityQueue<node_info> pqueue = new PriorityQueue<node_info>(new vertexCompare());
        HashMap<Integer, Boolean> nodes = new HashMap<Integer, Boolean>();
        HashMap<Integer, node_info> routes = new HashMap<Integer, node_info>();
        for (node_info node : g.getV()) {
            nodes.put(node.getKey(), false);
            node.setTag(Double.POSITIVE_INFINITY);
        }
        source.setTag(0);
        pqueue.add(source);
        while (!pqueue.isEmpty()) {
            node_info temp_node = pqueue.poll();
            if (!nodes.get(temp_node.getKey()))// check if the vertex not yet visited
            {
                for (node_info neighbor : g.getV(temp_node.getKey())) {

                    double distance = temp_node.getTag() + g.getEdge(temp_node.getKey(), neighbor.getKey());
                    if (neighbor.getTag() > distance) {
                        neighbor.setTag(distance);
                        pqueue.add(neighbor);
                        routes.put(neighbor.getKey(), temp_node);
                    }


                }
                nodes.put(temp_node.getKey(), true);//marking the vertex as visited
            }

        }
        return routes;
    }



    /**
     * this comparator is needed to set the priority of the priority queue in the dijkstra's algorithm
     */
    private class vertexCompare implements Comparator {
        @Override
        public int compare(Object obj1, Object obj2) {
            node_info vertex1= (node_info) obj1;
            node_info vertex2= (node_info) obj2;
            if (vertex1.getTag() == vertex2.getTag())
            if (vertex1.getTag() < vertex2.getTag()) {
                return -1;
            }
            if (vertex1.getTag() > vertex2.getTag()) {
                return 1;
            }
            return 0;
        }
    }
}

