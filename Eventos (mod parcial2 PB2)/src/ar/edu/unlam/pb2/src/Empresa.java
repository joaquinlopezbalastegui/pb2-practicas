package ar.edu.unlam.pb2.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Empresa {
	
	private Set<Cliente> clientes;
	private Set<Evento> eventos;
	
	public Empresa() {
		this.clientes = new HashSet<>();
		this.eventos = new HashSet<>();
	}
	
	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
	}
	
	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void agregarEvento(Evento evento) throws EventoDuplicadoException{
		if(!eventos.add(evento)) {
			throw new EventoDuplicadoException("Ese evento ya existe");
		}
	}
	
	public Evento buscarEvento(String codigoAlfanumerico) throws EventoInexistenteException {
		for (Evento evento : eventos) {
			if(evento.getCodigoAlfanumerico().equals(codigoAlfanumerico)) {
				return evento;
			}
		}
		throw new EventoInexistenteException("No existe un evento con ese codigo alfanumerico");
	}
	
	public void agregarClienteAEvento(Cliente cliente, Evento evento) throws ClienteExistenteEnEventoException, TallerSinCupoException{
		if(estaClienteEnEvento(cliente, evento)) {
			throw new ClienteExistenteEnEventoException("Ya existe ese cliente en ese evento");
		}
		evento.agregarParticipante(cliente);

	}
	
	public boolean estaClienteEnEvento(Cliente cliente, Evento evento) {
		return evento.getParticipantes().contains(cliente);
	}

	public Set<Evento> getEventos() {
		return eventos;
	}
	
	public List<Evento> listaDeConferencias(){
		List<Evento> conferencias = new ArrayList<>();
		for (Evento evento : eventos) {
			if(evento instanceof Conferencias) {
				conferencias.add(evento);
			}
		}
		return conferencias;
	}
	
	public Map<Evento, Set<Persona>> obtenerParticipantesPorConferenciaOrdenadosPorApellido(){
		Map<Evento, Set<Persona>> resultado = new HashMap<>();
		for (Evento evento : eventos) {
			if(evento instanceof Conferencias) {
				Set<Persona> participantesOrdenados = new TreeSet<>(new OrdenarPersonaPorApellido());
				participantesOrdenados.addAll(evento.getParticipantes());
				resultado.put(evento, participantesOrdenados);
			}
		}
		return resultado;
	}
}
