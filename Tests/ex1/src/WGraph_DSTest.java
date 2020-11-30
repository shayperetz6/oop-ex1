package ex1.Tests;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    @Test
    void getKey(){
        node_info node=new WGraph_DS.NodeData(2);
        assertEquals(2,node.getKey());

    }
    @Test
    void getInfo(){
        node_info node=new WGraph_DS.NodeData(3);
        node.setInfo("hello");
        String str=node.getInfo();
        assertEquals("hello",str);


    }
    @Test
    void setInfo(){
        weighted_graph graph=new WGraph_DS();
        graph.addNode(3);
        node_info node=graph.getNode(3);
        assertNull(node.getInfo());
        node.setInfo("hello");
        assertEquals("hello", node.getInfo());


    }
    @Test
    void getTag(){
        weighted_graph graph=new WGraph_DS();
        graph.addNode(3);
        node_info node=graph.getNode(3);
        node.setTag(3);
        assertEquals(3,node.getTag());

    }
    @Test
    void setTag(){
        weighted_graph graph=new WGraph_DS();
        graph.addNode(3);
        node_info node=graph.getNode(3);
        node.setTag(4);
        assertEquals(4,node.getTag());
        node.setTag(5);
        assertNotEquals(4,node.getTag());

    }

    @Test
    void addNode(){
        weighted_graph graph = new WGraph_DS();
        graph.addNode(1);
        assertNotEquals(null,graph.getNode(1));
    }


    @Test
    void getNode() {
        weighted_graph graph= new WGraph_DS();
        graph.addNode(1);
        node_info node=graph.getNode(1);
        assertEquals(1,node.getKey());
        node_info node2= graph.getNode(5);
        assertEquals(null,node2);
    }

    @Test
    void hasEdge() {
        weighted_graph graph= new WGraph_DS();
        graph.addNode(1);
        graph.addNode(0);
        graph.connect(1,0,3);
        assertTrue(graph.hasEdge(1,0));
        assertTrue(graph.hasEdge(0,1));
        assertFalse(graph.hasEdge(2,3));

    }

    @Test
    void getEdge() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(2);
        graph.addNode(4);
        graph.connect(2,4,5);
        assertEquals(5,graph.getEdge(2,4));
        assertEquals(5,graph.getEdge(4,2));
        assertEquals(-1,graph.getEdge(8,9));

    }

    @Test
    void connect() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(2);
        graph.addNode(4);
        graph.connect(2,4,3);
        graph.connect(2,4,3);
        assertEquals(1,graph.edgeSize());
    }

    @Test
    void getV() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(5);
        graph.addNode(8);
        graph.addNode(7);
        graph.addNode(3);
        graph.addNode(2);
        graph.addNode(1);
        Collection<node_info> nodes=graph.getV();
        assertEquals(6,nodes.size());

    }

    @Test
    void testGetV() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(5);
        graph.addNode(8);
        graph.addNode(7);
        graph.addNode(3);
        graph.addNode(2);
        graph.addNode(1);
        graph.connect(1,5,3);
        graph.connect(1,8,2);
        graph.connect(1,7,4);
        Collection<node_info> nodes=graph.getV(1);
        assertEquals(3,nodes.size());
        assertFalse(nodes.contains(graph.getNode(2)));
    }

    @Test
    void removeNode() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(5);
        graph.addNode(8);
        graph.addNode(7);
        graph.addNode(3);
        graph.addNode(2);
        graph.addNode(1);
        graph.connect(1,5,3);
        graph.connect(1,8,2);
        graph.connect(1,7,4);
        graph.removeNode(1);
        Collection<node_info> nodes=graph.getV();
        assertFalse(nodes.contains(graph.getNode(1)));
        assertFalse(graph.hasEdge(1,5));
        assertTrue(graph.edgeSize()==0);

    }

    @Test
    void removeEdge() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(3,4,2);
        graph.removeEdge(3,4);
        assertEquals(2,graph.getMC());
    }

    @Test
    void nodeSize() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.addNode(8);
        graph.removeNode(6);
        assertEquals(3,graph.nodeSize());
    }

    @Test
    void edgeSize() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(3,4,2);
        graph.addNode(5);
        graph.addNode(6);
        graph.addNode(7);
        graph.addNode(8);
        graph.connect(5,7,4);
        graph.connect(3,8,2);
        assertEquals(3,graph.edgeSize());
    }

    @Test
    void getMC() {
        weighted_graph graph=new WGraph_DS();
        graph.addNode(3);
        graph.addNode(4);
        graph.connect(3,4,2);
        assertEquals(3,graph.getMC());
    }


}