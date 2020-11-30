/**
 * This class represent an undirected weighted graph
 * This class implements weighted_graph interface
 * This class  build an undirected graph, add and remove edges and nodes
 *
 * @author shay peretz
 */
package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;


public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> GraphMap;//hashmap saving all the nodes ;
    private HashMap<Integer, HashMap<Integer, Double>> edges; //hashmap saving all the edges in the graph and their weight
    private int mc_counter;//counting the number of changing in the graph
    private int edge_counter;

    /*** constuctore***/
    public WGraph_DS() {
        mc_counter = 0;
        edge_counter = 0;
        GraphMap = new HashMap<Integer, node_info>();
        edges = new HashMap<Integer, HashMap<Integer, Double>>();
    }

    /**inner class NodeDate ***/
    protected static class NodeData implements node_info, Serializable {
        int key;
        double tag = 0;
        String info;

        /**
         * Constructor
         * @param key is the id of the new node
         */
        NodeData(int key) {
            tag = 0;
            this.key = key;

        }

        /**
         * Copy Constructor
         * @param node is the node the Constructor going to deep copy
         */
        NodeData(node_info node) {
            this.tag = node.getTag();
            this.info = node.getInfo();
            this.key = node.getKey();

        }

        /***** getters and setters****/
        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NodeData)) return false;
            NodeData nodeData = (NodeData) o;
            return getKey() == nodeData.getKey() &&
                    Double.compare(nodeData.getTag(), getTag()) == 0 &&
                    getInfo().equals(nodeData.getInfo());

        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getTag(), getInfo());
        }
    }

    /*** WGraph methods ****/
    @Override
    /**
     * This method return the Nodeinfo by its id
     * @param key - the node id
     * @return the NodeData by its id, null if none
     */
    public node_info getNode(int key) {
        if (GraphMap.containsKey(key)) return GraphMap.get(key);
        else
            return null;
    }

    /**
     * check if there is an edge between node1 and node2
     * @param node1
     * @param node2
     * @return return true if there is a edge and false if not
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if ((!GraphMap.containsKey(node1)) || (!GraphMap.containsKey(node2))) {
            return false;
        }
        if(edges.containsKey(node1)) {
            return (edges.get(node1).containsKey(node2));
        }
        return false;
    }

    /**
     * return the weight of the edge between the two nodes
     * @param node1
     * @param node2
     * @return return the weight of the edge if there isn't a edge return -1
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!hasEdge(node1, node2)) return -1.;
        else
            return edges.get(node1).get(node2);
    }

    /**
     * add a new node to the graph with the given key.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!GraphMap.containsKey(key)) {

            GraphMap.put(key, new NodeData(key));
            mc_counter++;
        }
    }

    /**
     * create an edge between node1 and node2, with an edge with weight >=0.
     * Note: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (!GraphMap.containsKey(node1) || !GraphMap.containsKey(node2))
            return;
        if (node1==node2)
            return;
        if (hasEdge(node1, node2)) {
            edges.get(node1).replace(node2, w);
            edges.get(node2).replace(node1, w);
        }
        else {
            if(edges.get(node1) == null)
                edges.put(node1,new HashMap<Integer,Double>());
            if(edges.get(node2) == null)
                edges.put(node2, new HashMap<Integer,Double>());
            edges.get(node1).put(node2, w);
            edges.get(node2).put(node1, w);
            edge_counter++;
            mc_counter++;
        }

    }

    /**
     * This method return a pointer for a collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return GraphMap.values();
    }

    /**
     * This method returns a Collection containing all the neighbors of the node id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        LinkedList<node_info> list = new LinkedList<>();
        if (edges.containsKey(node_id))// check if the graph contains the node
        {
            for (int neighbor_data : edges.get(node_id).keySet()) {
                list.add(getNode(neighbor_data));
            }
            return list;
        } else
            return null;
    }

    /**
     * Delete the node (with the given ID) from the graph
     * and removes all edges connecting to the node.
     * @return the data of the removed node null if none.
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        if (!GraphMap.containsKey(key))
            return null;
        else {
            for (int neighbor : edges.get(key).keySet()) {
                removeEdge(key, neighbor);
                edge_counter--;
                mc_counter++;
            }
            node_info erase_node = getNode(key);
            GraphMap.remove(key);
            mc_counter++;
            return erase_node;
        }
    }

    /**
     * Delete the edge connecting the two nodes  from the graph,
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if ((GraphMap.containsKey(node1) && GraphMap.containsKey(node2)) && (hasEdge(node1, node2))) {
            edges.get(node1).remove(node2);
            edges.get(node2).remove(node1);
            edge_counter--;
            mc_counter++;
        }
    }

    /**
     * @return the number of vertices (nodes) in the graph
     */
    @Override
    public int nodeSize() {
        return GraphMap.size();
    }

    /**
     * @return the number of edges in the graph
     */
    @Override
    public int edgeSize() {
        return edge_counter;
    }

    /**
     * return  - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return the Mode Count of the graph
     */
    @Override
    public int getMC() {
        return mc_counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_DS)) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return mc_counter == wGraph_ds.mc_counter &&
                edge_counter == wGraph_ds.edge_counter &&
                GraphMap.equals(wGraph_ds.GraphMap) &&
                edges.equals(wGraph_ds.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(GraphMap, edges, mc_counter, edge_counter);
    }


}
