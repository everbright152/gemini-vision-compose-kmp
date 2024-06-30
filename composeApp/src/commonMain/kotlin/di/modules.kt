package di


import di.network.GeminiApi
import di.network.GeminiClient
import org.koin.dsl.module

fun appModule() = listOf(
    domainModule,
    dataModule
)

val domainModule = module {

}

val dataModule = module {
    single<GeminiApi> { GeminiClient.app.create()}
}