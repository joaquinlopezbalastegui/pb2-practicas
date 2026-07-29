package ar.edu.unlam.pb2.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unlam.pb2.src.Cliente;
import ar.edu.unlam.pb2.src.ClienteExistenteEnEventoException;
import ar.edu.unlam.pb2.src.Conferencias;
import ar.edu.unlam.pb2.src.Empresa;
import ar.edu.unlam.pb2.src.Evento;
import ar.edu.unlam.pb2.src.EventoDuplicadoException;
import ar.edu.unlam.pb2.src.EventoInexistenteException;
import ar.edu.unlam.pb2.src.Expositor;
import ar.edu.unlam.pb2.src.ParticipanteDeEvento;
import ar.edu.unlam.pb2.src.Persona;
import ar.edu.unlam.pb2.src.Sala;
import ar.edu.unlam.pb2.src.TallerSinCupoException;
import ar.edu.unlam.pb2.src.Talleres;

public class EventosTest {
	
	Empresa empresa;
	Evento evento;
	Cliente cliente;
	Expositor expositor;
	ParticipanteDeEvento participanteDeEvento;
	private List<String> temas = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		empresa = new Empresa();
		this.temas.add("Politica");
		expositor = new Expositor(40311300, "Ressia", "Sol");
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaCuandoAgregoUnClienteObtengoUnResultadoExitoso() {
		cliente = new Cliente(47031400, "Lopez", "Joaquin");
		empresa.agregarCliente(cliente);
		
		assertEquals(1, empresa.getClientes().size());
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaCuandoAgregoUnEventoExistenteObtengoUnaEventoDuplicadoException() throws EventoDuplicadoException {
		evento = new Talleres("123ABC", "12/3", "Taller de pintura", Sala.CHICA, expositor, 10, 60);
		Evento evento2 = new Conferencias("123ABC", "11/9", "Conferencia politica", Sala.GRANDE, expositor, temas);
		empresa.agregarEvento(evento);
		
		assertThrows(EventoDuplicadoException.class,() -> empresa.agregarEvento(evento2));
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoBuscoUnEventoExistentePorSuCodigoObtengoElEvento() throws EventoDuplicadoException, EventoInexistenteException {
		evento = new Talleres("123ABC", "12/3", "Taller de pintura", Sala.CHICA, expositor, 10, 60);
		empresa.agregarEvento(evento);
		
		assertEquals(evento, empresa.buscarEvento("123ABC"));
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoVerificoSiUnClienteSeEncuentraEntreLosParticipantesDeUnEventoPorClienteYExisteObtengoUnResultadoPositivo() throws EventoDuplicadoException, ClienteExistenteEnEventoException, TallerSinCupoException {
		cliente = new Cliente(47031400, "Lopez", "Joaquin");
		evento = new Talleres("123ABC", "12/3", "Taller de pintura", Sala.CHICA, expositor, 10, 60);
		
		empresa.agregarCliente(cliente);
		empresa.agregarEvento(evento);
		empresa.agregarClienteAEvento(cliente, evento);
		
		assertTrue(empresa.estaClienteEnEvento(cliente, evento));
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoAgregoUnClienteAUnEventoDondeExisteElClienteObtengoUnaClienteExistenteEnEventoException() throws EventoDuplicadoException, ClienteExistenteEnEventoException, TallerSinCupoException {
		cliente = new Cliente(47031400, "Lopez", "Joaquin");
		evento = new Talleres("123ABC", "12/3", "Taller de pintura", Sala.CHICA, expositor, 10, 60);
		
		empresa.agregarCliente(cliente);
		empresa.agregarEvento(evento); 
		empresa.agregarClienteAEvento(cliente, evento);
		
		assertThrows(ClienteExistenteEnEventoException.class, () -> empresa.agregarClienteAEvento(cliente, evento));
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoAgregoUnClienteAUnTallerSinCupoDondeNoExisteElClienteObtengoUnResultadoFallido() throws EventoDuplicadoException, ClienteExistenteEnEventoException, TallerSinCupoException {
		evento = new Talleres("123ABC", "12/3", "Taller de pintura", Sala.CHICA, expositor, 1, 60);
		empresa.agregarEvento(evento);
		
		cliente = new Cliente(47031400, "Lopez", "Joaquin");
		Cliente cliente2 = new Cliente(40111222, "Perez", "Ana");
		
		empresa.agregarCliente(cliente);
		empresa.agregarCliente(cliente2);
		empresa.agregarClienteAEvento(cliente, evento); //OCUPA EL UNICO CUPO
		
		assertThrows(TallerSinCupoException.class, () -> empresa.agregarClienteAEvento(cliente2, evento));
		
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoConsultoLaRecaudacionTodalDeUnEventoTallerCon10ParticipantesRecibo250000() throws EventoDuplicadoException, ClienteExistenteEnEventoException, TallerSinCupoException {
		evento = new Talleres("123ABC", "12/3", "Taller de pintura", Sala.CHICA, expositor, 10, 60);
		empresa.agregarEvento(evento);
		
		for(int i=0; i<10; i++) { //ESTO AGREGA UN CLIENTE 10 VECES
			cliente = new Cliente(40000000 + i, "Apellido" + i, "Nombre" + i);
			empresa.agregarCliente(cliente);
			empresa.agregarClienteAEvento(cliente, evento);
		}
		
		assertEquals(250000d, evento.calcularRecaudacion());

	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventos3ConferenciasObtengoUnaListaCon3Conferencias() throws EventoDuplicadoException {
		Evento conferencia1 = new Conferencias("123ABC", "12/3", "Conferencia 1", Sala.CHICA, expositor, temas);
		Evento conferencia2 = new Conferencias("456DEF", "13/3", "Conferencia 2", Sala.MEDIANA, expositor, temas);
		Evento conferencia3 = new Conferencias("789GHI", "14/3", "Conferencia 3", Sala.GRANDE, expositor, temas);
		
		empresa.agregarEvento(conferencia1);
		empresa.agregarEvento(conferencia2);
		empresa.agregarEvento(conferencia3);
		
		assertEquals(3, empresa.listaDeConferencias().size());
	}
	
	@Test
	public void dadoQueExisteUnaEmpresaConEventosCuandoConsultoLosParticipantesDeConferenciasObtengoUnMapaConLasConferenciasComoClaveYUnaColeccionDeParticipantesPorConferenciaOrdenadaPorApellido() throws EventoDuplicadoException, ClienteExistenteEnEventoException, TallerSinCupoException {
		Evento conferencia = new Conferencias("123ABC", "12/3", "Conferencia 1", Sala.CHICA, expositor, temas);
		empresa.agregarEvento(conferencia);
		
		Cliente cliente1 = new Cliente(40000001, "Zapata", "Ana");
	    Cliente cliente2 = new Cliente(40000002, "Alvarez", "Luis");
	    Cliente cliente3 = new Cliente(40000003, "Martinez", "Sol");
	    
	    empresa.agregarCliente(cliente1);
	    empresa.agregarCliente(cliente2);
	    empresa.agregarCliente(cliente3);

	    empresa.agregarClienteAEvento(cliente1, conferencia);
	    empresa.agregarClienteAEvento(cliente2, conferencia);
	    empresa.agregarClienteAEvento(cliente3, conferencia);
	    
	    Map<Evento, Set<Persona>> mapa = empresa.obtenerParticipantesPorConferenciaOrdenadosPorApellido();
	    Iterator<Persona> it = mapa.get(conferencia).iterator();
	    
	    assertEquals("Alvarez", it.next().getApellido());
	    assertEquals("Martinez", it.next().getApellido());
	    assertEquals("Zapata", it.next().getApellido());
	}
}
