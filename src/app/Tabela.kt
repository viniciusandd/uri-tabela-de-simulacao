package app

class Tabela {
    fun criar(tempoDeSimulacao: Int, tempoEntreChegadas: ArrayList<Int>, tempoDeServico: ArrayList<Int>) : ArrayList<Simulacao> {
        val alSimulacao = ArrayList<Simulacao>()
        var i = 0
        while (true) {
            val simulacao = Simulacao()
            simulacao.cliente = i + 1
            simulacao.tempoDesdeUltimaChegada = this.sortearTEC(tempoEntreChegadas, i)
            simulacao.tempoServico = this.sortearTS(tempoDeServico, i)
            when (i) {
                0 -> {
                    simulacao.tempoChegadaRelogioSimulacao = simulacao.tempoDesdeUltimaChegada
                    simulacao.tempoInicioServicoRelogioSimulacao = simulacao.tempoDesdeUltimaChegada
                    simulacao.tempoClienteNaFila = 0
                    simulacao.tempoLivreOperador = simulacao.tempoDesdeUltimaChegada
                }
                else -> {
                    val simulacaoAnterior = alSimulacao[i - 1]
                    simulacao.tempoChegadaRelogioSimulacao = simulacaoAnterior.tempoChegadaRelogioSimulacao + simulacao.tempoDesdeUltimaChegada
                    simulacao.tempoClienteNaFila =
                            if (simulacaoAnterior.tempoFinalServicoRelogioSimulacao >= simulacao.tempoChegadaRelogioSimulacao) simulacaoAnterior.tempoFinalServicoRelogioSimulacao - simulacao.tempoChegadaRelogioSimulacao else 0
                    simulacao.tempoInicioServicoRelogioSimulacao = simulacao.tempoChegadaRelogioSimulacao + simulacao.tempoClienteNaFila
                    simulacao.tempoLivreOperador =
                            if (simulacaoAnterior.tempoFinalServicoRelogioSimulacao < simulacao.tempoChegadaRelogioSimulacao) simulacao.tempoChegadaRelogioSimulacao - simulacaoAnterior.tempoFinalServicoRelogioSimulacao else 0
                }
            }
            simulacao.tempoFinalServicoRelogioSimulacao = simulacao.tempoServico + simulacao.tempoInicioServicoRelogioSimulacao
            simulacao.tempoClienteNoSistema = simulacao.tempoServico + simulacao.tempoClienteNaFila
            alSimulacao.add(simulacao)

            println(simulacao.tempoFinalServicoRelogioSimulacao)

            if (simulacao.tempoFinalServicoRelogioSimulacao >= tempoDeSimulacao)
                break

            i++
        }
        return alSimulacao
    }

    fun sortearTEC(tempoEntreChegadas: ArrayList<Int>, index: Int? = null) : Int {
        if (index != null) {
            when (index) {
                0 -> return 15
                1 -> return 12
                2 -> return 10
                3 -> return 10
                4 -> return 12
                5 -> return 15
                6 -> return 10
                7 -> return 12
                8 -> return 10
                9 -> return 10
                10 -> return 10
                11 -> return 12
                12 -> return 15
                13 -> return 12
                14 -> return 12
            }
        }
        return tempoEntreChegadas.shuffled().take(1)[0]
    }

    fun sortearTS(tempoDeServico: ArrayList<Int>, index: Int? = null) : Int {
        if (index != null) {
            when (index) {
                0 -> return 11
                1 -> return 10
                2 -> return 9
                3 -> return 10
                4 -> return 9
                5 -> return 10
                6 -> return 11
                7 -> return 9
                8 -> return 11
                9 -> return 10
                10 -> return 11
                11 -> return 9
                12 -> return 10
                13 -> return 9
                14 -> return 11
            }
        }
        return tempoDeServico.shuffled().take(1)[0]
    }
}