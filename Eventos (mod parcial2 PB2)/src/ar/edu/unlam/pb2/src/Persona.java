package ar.edu.unlam.pb2.src;

import java.util.Objects;

public abstract class Persona implements Comparable<Persona>{
	
	protected Integer dni;
	protected String apellido;
	protected String nombre;
	
	public Persona(Integer dni, String apellido, String nombre) {
		this.dni = dni;
		this.apellido = apellido;
		this.nombre = nombre;
	}
		
	public Integer getDni() {
		return dni;
	}

	public String getApellido() {
		return apellido;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Persona))
			return false;
		Persona other = (Persona) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return "Persona [dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + "]";
	}
	
	
}
