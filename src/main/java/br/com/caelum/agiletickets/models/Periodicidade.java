package br.com.caelum.agiletickets.models;

import org.joda.time.LocalDate;

public enum Periodicidade {
	DIARIA {
		@Override
		public LocalDate calculaDataN(LocalDate dataInicio, int iteracao) {
			return dataInicio.plusDays(iteracao);
		}
	},
	SEMANAL {
		@Override
		public LocalDate calculaDataN(LocalDate dataInicio, int iteracao) {
			return dataInicio.plusDays(iteracao * 7);
		}
	},
	MENSAL {
		@Override
		public LocalDate calculaDataN(LocalDate dataInicio, int iteracao) {
			return dataInicio.plusMonths(iteracao);
		}
	};

	public abstract LocalDate calculaDataN(LocalDate dataInicio, int iteracao);
}
