package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	
	private Graph<String,DefaultWeightedEdge> grafo;
	private EventsDao dao;
	
	private List<String> percorsoMigliore;
	private int min;
	
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
	
	public List<Adiacenze> getPesoMax(){
		List<Adiacenze> archiMax = new ArrayList<>();
		int max=0;
		
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)>max) {
				max=(int) grafo.getEdgeWeight(e); 
			}
		}
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e)==max) {
			archiMax.add(new Adiacenze(grafo.getEdgeSource(e),grafo.getEdgeTarget(e),max));
			}
		}
		return archiMax;
	}
	
	public List<Adiacenze> getArchi(){
		List<Adiacenze> archi = new ArrayList<>();
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			archi.add(new Adiacenze(grafo.getEdgeSource(e),grafo.getEdgeTarget(e),(int)grafo.getEdgeWeight(e)));
		}
		return archi;
	}
	
	public List<String> trovaPercorso(String sorgente, String dest) {
		this.percorsoMigliore =new LinkedList<>();
		List<String> parziale =new LinkedList<>();
		parziale.add(sorgente);
		min=calcolaPeso(parziale);
		cerca(dest, parziale);
		
		return this.percorsoMigliore;
	}
	private void cerca(String destinazione, List<String> parziale) {
		
		String ultimo= parziale.get(parziale.size()-1);
		if (ultimo.equals(destinazione)) {
			if(calcolaPeso(parziale)<min) {
				percorsoMigliore= new ArrayList<>(parziale);
				min=calcolaPeso(parziale);
			}
		}
		
		for (String s: Graphs.neighborListOf(grafo, ultimo)) {
			if (!parziale.contains(s)) {
				parziale.add(s);
				cerca(destinazione,parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	private int calcolaPeso(List<String> parziale) {
		int pesoMin=0;
		for (int i=1; i<parziale.size();i++) {
			String s1 = parziale.get(i-1);
			String s2 = parziale.get(i);
			pesoMin+=(int) grafo.getEdgeWeight(grafo.getEdge(s1, s2));
		}
		return pesoMin;
	}
	
	
}
