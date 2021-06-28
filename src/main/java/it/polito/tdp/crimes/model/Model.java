package it.polito.tdp.crimes.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private Graph<String,DefaultWeightedEdge> grafo;
	private EventsDao dao;
	
	public Model() {
		dao = new EventsDao();
	}
	public List<String> getCategory(){
		return dao.getCategory();
	}
	
	public List<Integer> getAnno(){
		return dao.getAnno();
	}
	
	public void creaGrafo(String categoria, int anno) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getVertici(categoria, anno));
		
		for (Adiacenze a: dao.getAdiacenze(categoria, anno)) {
			Graphs.addEdgeWithVertices(grafo, a.getE1(), a.getE2(), a.getPeso());
		}
		
		System.out.format("Grafo creato con %d vertici e %d archi\n",
 				this.grafo.vertexSet().size(), this.grafo.edgeSet().size()); 
		
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
}
