package ex1.Tests;

import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {


    @Test
    void copy() {
        weighted_graph graph=new WGraph_DS();
        for (int i = 0; i <=10 ; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i <10 ; i++) {
            graph.connect(i,i+1,1);
        }
        weighted_graph_algorithms graph_algo=new WGraph_Algo();
        graph_algo.init(graph);
        weighted_graph copy=graph_algo.copy();
        assertEquals(copy,graph);

    }

    @Test
    void isConnected() {
        weighted_graph graph = null;
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        graph_algo.init(graph);
        assertTrue(graph_algo.isConnected());
        for (int i=0;i<=50;i++){
            graph.addNode(i);
        }
        for (int i = 0; i < 50; i++) {
            graph.connect(i, i + 1, 2);
        }
        graph_algo.init(graph);
        assertTrue(graph_algo.isConnected());
        weighted_graph graph2=new WGraph_DS();
        graph2.addNode(2);
        graph2.addNode(3);
        graph2.addNode(4);
        graph2.connect(2,3,4);
        weighted_graph_algorithms graph_algo2=new WGraph_Algo();
        graph_algo2.init(graph2);
        assertFalse(graph_algo2.isConnected());

            
    }

    @Test
    void shortestPathDist() {
        weighted_graph graph = null;
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        graph_algo.init(graph);
        assertEquals(-1, graph_algo.shortestPathDist(1, 8));
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(1);
        graph.addNode(4);
        graph.connect(1,2,3);
        graph.connect(1,4,9);
        graph.connect(2,4,3);
        graph_algo.init(graph);
        assertEquals(6,graph_algo.shortestPathDist(1,4));
    }

    @Test
    void shortestPath() {
        weighted_graph graph=new WGraph_DS();
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(1);
        graph.addNode(4);
        graph.connect(1,2,3);
        graph.connect(1,4,9);
        graph.connect(2,4,3);
        graph_algo.init(graph);
        Collection<node_info> list=new LinkedList<node_info>();
        list.add(graph.getNode(1));
        list.add(graph.getNode(2));
        list.add(graph.getNode(4));
        assertEquals(list,graph_algo.shortestPath(1,4));
    }

    @Test
    void save_load() {
        weighted_graph graph = new WGraph_DS();
        for (int i = 0; i <= 10; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i < 10; i++) {
            graph.connect(i, i + 1, 2);
        }
        weighted_graph_algorithms graph_algo = new WGraph_Algo();
        graph_algo.init(graph);
        String str = "graph.obj";
        assertFalse(graph_algo.save(" "));
        assertTrue(graph_algo.save(str));
        weighted_graph_algorithms graph_algo2 = new WGraph_Algo();
        graph_algo2.load(str);
        assertEquals(graph,graph_algo2.getGraph());

    }


}