package ar.edu.unlam.pb2.src;

import java.util.List;

public class Conferencias extends Evento{
	
	private List<String> temas;
	
	public Conferencias(String codigoAlfanumerico, String fecha, String nombre, Sala sala, Expositor expositor, List<String> temas) {
		super(codigoAlfanumerico, fecha, nombre, sala, expositor);
		this.temas = temas;
	}

	@Override
	public Double calcularRecaudacion() {
		return 15000d * getParticipantes().size();
	}

	@Override
	public String toString() {
		return "Conferencias [temas=" + temas + "]";
	}

}
