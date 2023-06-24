package it.polito.tdp.baseball.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.baseball.db.BaseballDAO;
import javafx.scene.Node;

public class Model {
	
	private Graph<People, DefaultEdge> grafo ;
	private List<People> giocatori ;
	private BaseballDAO dao;
	private Map<String,People> idMap;
	
	public Model() {
		dao = new BaseballDAO();
		idMap = new HashMap<String,People>();
		dao.readAllPlayers(idMap);
	}
	
	public void creaGrafo(int anno, int salario) {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		this.giocatori = dao.getVertici(anno,salario,idMap);
		Graphs.addAllVertices(grafo, giocatori);
		
		for(Adiacenza a : dao.getAdiacenze(anno,salario,idMap)) {
			if(this.grafo.containsVertex(a.getV1()) && 
					this.grafo.containsVertex(a.getV2())) {
				DefaultEdge e = this.grafo.getEdge(a.getV1(), a.getV2());
				if(e == null) 
					Graphs.addEdgeWithVertices(grafo, a.getV1(), a.getV2());
			}
		}
		
		System.out.println("VERTICI: " + this.grafo.vertexSet().size());
		System.out.println("ARCHI: " + this.grafo.edgeSet().size());
	}
	
	public String gradoMax() {
		int max =0;
		int grado;
		People scelto = null;
		for(People p: this.grafo.vertexSet()) {
			grado = this.grafo.degreeOf(p);
			if(grado > max) {
				max = grado;
				scelto = p;
			}
		}
		System.out.println("Il giocatore è: " + scelto + " GRADO: " + max);
		return("Il giocatore è: " + scelto + " GRADO: " + max);
	}
	
	public int getNVertici() {
		if(grafo != null)
			return grafo.vertexSet().size();
		
		return 0;
	}
	
	public int getNArchi() {
		if(grafo != null)
			return grafo.edgeSet().size();
		
		return 0;
	}
	

	public int componentiConnesse() {
		ConnectivityInspector<People, DefaultEdge> inspector = new ConnectivityInspector<People, DefaultEdge>(this.grafo);
		return inspector.connectedSets().size();
	}
	
	
}