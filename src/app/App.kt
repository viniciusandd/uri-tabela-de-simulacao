package app

import kotlinx.html.ButtonType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

interface AppState : RState {
    var tempoDeSimulacao: Int
    var tempoEntreChegadas: ArrayList<Int>
    var tempoDeServico: ArrayList<Int>
    var alSimulacao: ArrayList<Simulacao>?
}

class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
        tempoDeSimulacao = 180
        tempoEntreChegadas = arrayListOf(10, 12, 15)
        tempoDeServico = arrayListOf(9, 10, 11)
        alSimulacao = null
    }

    override fun RBuilder.render() {
        appHeaderUi()
        containerUi()
    }

    private fun RBuilder.appHeaderUi() {
        div("App-header") {
            h2 {
                +"Tabela de Simulação"
            }
        }
    }

    private fun RBuilder.containerUi() {
        div("container") {
            formUi()
            tableUi()
        }
    }

    private fun RBuilder.formUi() {
        div("row borderRow") {
            div("col-3") {}
            div("col-6") {
                form {
                    div("form-group") {
                        input(classes = "form-control") {
                            attrs.placeholder = "Tempo de Simulação (minutos)"
                        }
                    }
                    div("form-row") {
                        div("form-group col-md-6") {
                            input(classes = "form-control") {
                                attrs.placeholder = "TEC"
                            }
                            button(classes = "btn btn-secondary btn-sm") {
                                +"Adicionar"
                            }
                        }
                        div("form-group col-md-6") {
                            textArea(classes = "form-control") {
                            }
                        }
                    }
                    div("form-row") {
                        div("form-group col-md-6") {
                            input(classes = "form-control") {
                                attrs.placeholder = "TS"
                            }
                            button(classes = "btn btn-secondary btn-sm") {
                                +"Adicionar"
                            }
                        }
                        div("form-group col-md-6") {
                            textArea(classes = "form-control") {

                            }
                        }
                    }

                    botaoSimularUi()
                }
            }
            div("col-3") {}
        }
    }

    private fun RBuilder.botaoSimularUi() {
        button(type = ButtonType.button, classes = "btn btn-secondary btn-lg btn-block") {
            attrs.id = "buttonSimular"
            +"Simular"
            attrs.onClickFunction = {
                val tabela = Tabela()
                setState {
                    alSimulacao = tabela.criar(tempoDeSimulacao, tempoEntreChegadas, tempoDeServico)
                }
            }
        }
    }

    private fun RBuilder.tableUi() {
        div("row") {
            table(classes = "table table-striped table-dark table-bordered") {
                thead {
                    tr {
                        th(classes = "border align-middle") { +"Cliente" }
                        th(classes = "border align-middle") { +"Tempo desde a última chegada" }
                        th(classes = "border align-middle") { +"Tempo de chegada no Relógio da Simulação" }
                        th(classes = "border align-middle") { +"Tempo de Serviço" }
                        th(classes = "border align-middle") { +"Tempo do ínicio do serviço no relógio da simulação" }
                        th(classes = "border align-middle") { +"Tempo do cliente na fila" }
                        th(classes = "border align-middle") { +"Tempo final do serviço no relógio da simulação" }
                        th(classes = "border align-middle") { +"Tempo do cliente no sistema (minutos)" }
                        th(classes = "border align-middle") { +"Tempo livre do operador (minutos)" }
                    }
                }
                tbody {
                    state.alSimulacao?.let {
                        for (simulacao in it) {
                            tr {
                                td { +simulacao.cliente.toString() }
                                td { +simulacao.tempoDesdeUltimaChegada.toString() }
                                td { +simulacao.tempoChegadaRelogioSimulacao.toString() }
                                td { +simulacao.tempoServico.toString() }
                                td { +simulacao.tempoInicioServicoRelogioSimulacao.toString() }
                                td { +simulacao.tempoClienteNaFila.toString() }
                                td { +simulacao.tempoFinalServicoRelogioSimulacao.toString() }
                                td { +simulacao.tempoClienteNoSistema.toString() }
                                td { +simulacao.tempoLivreOperador.toString() }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
