package app

object Tabela {
    var tempoDeSimulacao: Int = 0
    var tempoEntreChegadas: ArrayList<Int> = arrayListOf()
    var tempoDeServico: ArrayList<Int> = arrayListOf()

    fun mostrarValores() {
        println("Tempo de Simulação: ${this.tempoDeSimulacao}\n\n")
        for (tec in this.tempoEntreChegadas) {
            println("TEC: ${tec}\n")
        }
        println("\n")
        for (ts in this.tempoDeServico) {
            println("TS: ${ts}\n")
        }
        println("\n")
    }

    fun criar() : Triple<ArrayList<Simulacao>, ArrayList<Int>, ArrayList<Float>> {
        val alSimulacao = ArrayList<Simulacao>()

        var somaTempoServico = 0
        var somaTempoClienteNaFila = 0
        var somaTempoClienteNoSistema = 0
        var somaTempoLivreOperador = 0

        var mediaEsperaNaFila = 0.0f
        var probabilidadeClienteEsperarNaFila = 0.0f
        var probabilidadeOperadorLivre = 0.0f
        var probabilidadeTmpoMediaServico = 0.0f
        var tempoMedioDispendidoSistema = 0.0f

        if (this.tempoDeSimulacao == 0 || this.tempoEntreChegadas.size == 0 || this.tempoDeServico.size == 0)
            return Triple(alSimulacao,
                    arrayListOf(somaTempoServico, somaTempoClienteNaFila, somaTempoClienteNoSistema, somaTempoLivreOperador),
                    arrayListOf(mediaEsperaNaFila, probabilidadeClienteEsperarNaFila, probabilidadeOperadorLivre, probabilidadeTmpoMediaServico,
                            tempoMedioDispendidoSistema)
            )

        var i = 0
        while (true) {
            val simulacao = Simulacao()
            simulacao.cliente = i + 1
            simulacao.tempoDesdeUltimaChegada = this.sortearTEC()
            simulacao.tempoServico = this.sortearTS()
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

            somaTempoServico += simulacao.tempoServico
            somaTempoClienteNaFila += simulacao.tempoClienteNaFila
            somaTempoClienteNoSistema += simulacao.tempoClienteNoSistema
            somaTempoLivreOperador += simulacao.tempoLivreOperador

            mediaEsperaNaFila = somaTempoClienteNaFila / simulacao.cliente.toFloat()
            probabilidadeClienteEsperarNaFila = somaTempoClienteNaFila / simulacao.cliente.toFloat()
            probabilidadeOperadorLivre = somaTempoLivreOperador / simulacao.tempoFinalServicoRelogioSimulacao.toFloat()
            probabilidadeTmpoMediaServico = somaTempoServico / simulacao.cliente.toFloat()
            tempoMedioDispendidoSistema = somaTempoClienteNoSistema / simulacao.cliente.toFloat()

            alSimulacao.add(simulacao)
            if (simulacao.tempoFinalServicoRelogioSimulacao >= this.tempoDeSimulacao)
                break
            i++
        }
        return Triple(alSimulacao,
                arrayListOf(somaTempoServico, somaTempoClienteNaFila, somaTempoClienteNoSistema, somaTempoLivreOperador),
                arrayListOf(mediaEsperaNaFila, probabilidadeClienteEsperarNaFila, probabilidadeOperadorLivre, probabilidadeTmpoMediaServico,
                        tempoMedioDispendidoSistema)
        )
    }

    fun reiniciar() {
        this.tempoDeSimulacao = 0
        this.tempoEntreChegadas.clear()
        this.tempoDeServico.clear()
    }

    private fun sortearTEC(index: Int? = null) : Int {
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
        return this.tempoEntreChegadas.shuffled().take(1)[0]
    }

    private fun sortearTS(index: Int? = null) : Int {
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
        return this.tempoDeServico.shuffled().take(1)[0]
    }
}