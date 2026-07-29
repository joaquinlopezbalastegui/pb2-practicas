package ar.edu.unlam.pb2.src;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Evento {
	
	protected String codigoAlfanumerico;
	protected String fecha;
	protected String nombre;
	protected Sala sala;
	protected Expositor expositor;
	protected Set<Persona> participantes;
	
	public Evento(String codigoAlfanumerico, String fecha, String nombre, Sala sala, Expositor expositor) {
		this.codigoAlfanumerico = codigoAlfanumerico;
		this.fecha = fecha;
		this.nombre = nombre;
		this.sala = sala;
		this.expositor = expositor;
		this.participantes = new HashSet<>();
	}
	
	public void agregarParticipante(Persona participante) throws TallerSinCupoException {
		participantes.add(participante);
	}
	
	public abstract Double calcularRecaudacion();
	
	@Override
	public int hashCode() {
		return Objects.hash(codigoAlfanumerico);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Evento))
			return false;
		Evento other = (Evento) obj;
		return Objects.equals(codigoAlfanumerico, other.codigoAlfanumerico);
	}


	public String getCodigoAlfanumerico() {
		return codigoAlfanumerico;
	}


	public String getFecha() {
		return fecha;
	}


	public String getNombre() {
		return nombre;
	}


	public Sala getSala() {
		return sala;
	}


	public Expositor getExpositor() {
		return expositor;
	}


	public Set<Persona> getParticipantes() {
		return participantes;
	}

	@Override
	public String toString() {
		return "Evento [codigoAlfanumerico=" + codigoAlfanumerico + ", fecha=" + fecha + ", nombre=" + nombre
				+ ", sala=" + sala + ", expositor=" + expositor + ", participantes=" + participantes + "]";
	}
	
	
}
