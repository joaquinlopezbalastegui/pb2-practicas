package ar.edu.unlam.pb2.src;

public class Talleres extends Evento{
	
	private Integer cupoMaximo;
	private Integer duracion;
	
	public Talleres(String codigoAlfanumerico, String fecha, String nombre, Sala sala, Expositor expositor, Integer cupoMaximo, Integer duracion) {
		super(codigoAlfanumerico, fecha, nombre, sala, expositor);
		this.cupoMaximo = cupoMaximo;
		this.duracion = duracion;
	}

	@Override
	public Double calcularRecaudacion() {
		return 25000d * getParticipantes().size();
	}

	@Override
	public void agregarParticipante(Persona participante) throws TallerSinCupoException{
		if(getParticipantes().size() >= this.cupoMaximo) {
			throw new TallerSinCupoException("No hay cupo disponible en el taller");
		}
		participantes.add(participante);
		
	}

	@Override
	public String toString() {
		return "Talleres [cupoMaximo=" + cupoMaximo + ", duracion=" + duracion + "]";
	}
	
}
