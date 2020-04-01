package app

import react.*
import react.dom.*
import logo.*
import ticker.*

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        div("App-header") {
            h2 {
                +"Tabela de Simulação"
            }
        }

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
                tr {
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                }

                tr {
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                    td { +"Teste" }
                }
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
