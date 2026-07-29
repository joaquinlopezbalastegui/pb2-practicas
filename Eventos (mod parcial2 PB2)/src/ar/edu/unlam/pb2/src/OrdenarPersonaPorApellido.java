package ar.edu.unlam.pb2.src;

import java.util.Comparator;

public class OrdenarPersonaPorApellido implements Comparator<Persona>{

	@Override
	public int compare(Persona p1, Persona p2) {
		int diferencia = p1.getApellido().compareTo(p2.getApellido());
		if(diferencia != 0) {
			return diferencia;
		}
		return p1.getNombre().compareTo(p2.getNombre());
	}

}
