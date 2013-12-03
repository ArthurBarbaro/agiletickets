package br.com.caelum.agiletickets;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;

import br.com.caelum.agiletickets.models.Espetaculo;
import br.com.caelum.agiletickets.models.Estabelecimento;
import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;
import br.com.caelum.vraptor.util.jpa.EntityManagerCreator;
import br.com.caelum.vraptor.util.jpa.EntityManagerFactoryCreator;

public class PreencheBanco {

	// ALUNO: Não apague essa classe
	public static void main(String[] args) {
		EntityManagerFactoryCreator creator = new EntityManagerFactoryCreator();
		creator.create();
		EntityManagerCreator managerCreator = new EntityManagerCreator(
				creator.getInstance());
		managerCreator.create();
		EntityManager manager = managerCreator.getInstance();

		manager.getTransaction().begin();

		executaQuery("delete from Sessao", manager);
		executaQuery("delete from Espetaculo", manager);
		executaQuery("delete from Estabelecimento", manager);
		Estabelecimento estabelecimento = criaEstabelecimento("Casa de shows",
				"Rua dos Silveiras, 12345");

		Espetaculo espetaculo = criaEspetaculo(estabelecimento, "Depeche Mode",
				TipoDeEspetaculo.SHOW);

		manager.persist(estabelecimento);
		manager.persist(espetaculo);

		for (int i = 0; i < 10; i++) {
			Sessao sessao = criaSessao(espetaculo,
					new DateTime().plusDays(7 + i), 60 * 3, 10, 10 - i);
			manager.persist(sessao);
		}

		manager.getTransaction().commit();
		manager.close();
	}

	private static Sessao criaSessao(Espetaculo espetaculo, DateTime inicio,
			Integer duracao, Integer totalIngressos, Integer ingressosReservados) {
		Sessao sessao = new Sessao();
		sessao.setEspetaculo(espetaculo);
		sessao.setInicio(inicio);
		sessao.setDuracaoEmMinutos(duracao);
		sessao.setTotalIngressos(totalIngressos);
		sessao.setIngressosReservados(ingressosReservados);
		return sessao;
	}

	private static Espetaculo criaEspetaculo(Estabelecimento estabelecimento,
			String nome, TipoDeEspetaculo tipo) {
		Espetaculo espetaculo = new Espetaculo();
		espetaculo.setEstabelecimento(estabelecimento);
		espetaculo.setNome(nome);
		espetaculo.setTipo(tipo);
		return espetaculo;
	}

	private static Estabelecimento criaEstabelecimento(String nome,
			String endereco) {
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setNome(nome);
		estabelecimento.setEndereco(endereco);
		return estabelecimento;
	}

	private static int executaQuery(String sql, EntityManager manager) {
		return manager.createQuery(sql).executeUpdate();
	}
}
