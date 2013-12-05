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

		assertTrue(ivete.vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.vagas(15));
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
		Assert.assertEquals(1, sessoes.size());
	}

	@Test
	public void espetaculoCriaSessoesComDataFinal7DiasDepoisDaInicialComPeriodicidadeSemanalRetorna2Sessoes() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual,
				dataAtual.plusDays(7), horaEspetaculo, Periodicidade.SEMANAL);
		Assert.assertEquals(2, sessoes.size());
	}

	@Test
	public void espetaculoCriaSessoesComDataFinal7DiasDepoisDaInicialComPeriodicidadeDiariaRetorna8Sessoes() {
		List<Sessao> sessoes = espetaculo.criaSessoes(dataAtual,
				dataAtual.plusDays(7), horaEspetaculo, Periodicidade.DIARIA);
		Assert.assertEquals(8, sessoes.size());
	}

	@Test
	public void espetaculoCriaSessoesComPeriodicidadeMensalCriaSessoesComDiaDoMesIgual() {
		LocalDate dataInicio = new LocalDate(2012,1,1);
		LocalDate dataFim = new LocalDate(2012,5,13);
		List<Sessao> sessoes = espetaculo.criaSessoes(dataInicio,
				dataFim, horaEspetaculo, Periodicidade.MENSAL);
		Assert.assertEquals(5, sessoes.size());
		Assert.assertEquals(new LocalDate(2012,1,1), sessoes.get(0).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,2,1), sessoes.get(1).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,3,1), sessoes.get(2).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,4,1), sessoes.get(3).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,5,1), sessoes.get(4).getInicio().toLocalDate());
		//fim do mês
		dataInicio = new LocalDate(2012,1,31);
		dataFim = new LocalDate(2012,5,31);
		sessoes = espetaculo.criaSessoes(dataInicio,
				dataFim, horaEspetaculo, Periodicidade.MENSAL);
		Assert.assertEquals(5, sessoes.size());
		Assert.assertEquals(new LocalDate(2012,1,31), sessoes.get(0).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,2,29), sessoes.get(1).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,3,31), sessoes.get(2).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,4,30), sessoes.get(3).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2012,5,30), sessoes.get(4).getInicio().toLocalDate());
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
