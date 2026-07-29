package ar.edu.unlam.pb2.src;

public class Expositor extends Persona{

	public Expositor(Integer dni, String apellido, String nombre) {
		super(dni, apellido, nombre);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(Persona otra) {
		return this.getDni().compareTo(otra.getDni());
	}

}
