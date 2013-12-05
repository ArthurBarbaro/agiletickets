package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EspetaculoTest {

	private Espetaculo espetaculo;
	private LocalDate dataAtual;
	private LocalTime horaEspetaculo;

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.Vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.Vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}

	@Test
	public void espetaculoCriaSessoesComDataInicioMaiorQueDataFimRetornaZeroSessoes() {
		Assert.assertNull(espetaculo.criaSessoes(dataAtual.plusDays(1),
				dataAtual, horaEspetaculo, Periodicidade.DIARIA));
		Assert.assertNull(espetaculo.criaSessoes(dataAtual.plusDays(1),
				dataAtual, horaEspetaculo, Periodicidade.SEMANAL));
	}

	@Test
	public void espetaculoCriaSessoesComDataInicioIgualADataFimRetorna1Sessao() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual, dataAtual,
				horaEspetaculo, Periodicidade.DIARIA);
		Assert.assertEquals(sessoes.size(), 1);
		sessoes = espetaculo.criaSessoes(dataAtual, dataAtual, horaEspetaculo,
				Periodicidade.SEMANAL);
		Assert.assertEquals(sessoes.size(), 1);
	}

	@Test
	public void espetaculoCriaSessoesComDataFinal7DiasDepoisDaInicialComPeriodicidadeSemanalRetorna2Sessoes() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual,
				dataAtual.plusDays(7), horaEspetaculo, Periodicidade.SEMANAL);
		Assert.assertEquals(sessoes.size(), 2);
	}

	@Test
	public void espetaculoCriaSessoesComDataFinal7DiasDepoisDaInicialComPeriodicidadeDiariaRetorna8Sessoes() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual,
				dataAtual.plusDays(7), horaEspetaculo, Periodicidade.DIARIA);
		Assert.assertEquals(sessoes.size(), 8);
	}

	@Test
	public void espetaculoCriaSessoesComAlgumaEntradaNullRetornaNull() {

		Assert.assertNull(espetaculo.criaSessoes(null, dataAtual,
				horaEspetaculo, Periodicidade.DIARIA));
		Assert.assertNull(espetaculo.criaSessoes(dataAtual, null,
				horaEspetaculo, Periodicidade.DIARIA));
		Assert.assertNull(espetaculo.criaSessoes(dataAtual, dataAtual, null,
				Periodicidade.DIARIA));
		Assert.assertNull(espetaculo.criaSessoes(dataAtual, dataAtual,
				horaEspetaculo, null));
	}


	@Test
	public void espetaculoCriaSessoesDeveCriarSessoesConsecutivasEmOrdemComPeriodicidadeDiaria() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual, dataAtual.plusDays(1), horaEspetaculo, Periodicidade.DIARIA);
		Assert.assertEquals(dataAtual,sessoes.get(0).getInicio().toLocalDate());
		Assert.assertEquals(dataAtual.plusDays(1),sessoes.get(1).getInicio().toLocalDate());
	}
	
	@Test
	public void espetaculoCriaSessoesDeveCriarSessoesConsecutivasEmOrdemComPeriodicidadeSemanal() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual, dataAtual.plusDays(8), horaEspetaculo, Periodicidade.SEMANAL);
		Assert.assertEquals(dataAtual,sessoes.get(0).getInicio().toLocalDate());
		Assert.assertEquals(dataAtual.plusDays(7),sessoes.get(1).getInicio().toLocalDate());
	}
	
	@Test
	public void espetaculoCriaSessoesDeveCriarSessoesComMesmoHorario() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual, dataAtual.plusDays(1), horaEspetaculo, Periodicidade.DIARIA);
		Assert.assertEquals(horaEspetaculo,sessoes.get(0).getInicio().toLocalTime());
		Assert.assertEquals(horaEspetaculo,sessoes.get(1).getInicio().toLocalTime());
	}
	
	@Before
	public void setUp() {
		espetaculo = new Espetaculo();
		dataAtual = new LocalDate();
		horaEspetaculo = new LocalTime();
	}
}
