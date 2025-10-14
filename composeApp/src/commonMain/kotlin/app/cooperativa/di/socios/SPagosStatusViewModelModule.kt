package app.cooperativa.di.socios

import app.cooperativa.domain.socios.SPagosStatusRepository
import app.cooperativa.domain.socios.SociosPagosStatusRepository
import app.cooperativa.presentation.mainflow.socios.pagos.pagoStatus.SPagosStatusViewModel
import com.apollographql.apollo3.ApolloClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val spagosStatusModule = module {
    single<SPagosStatusRepository> {
        SociosPagosStatusRepository(get<ApolloClient>(named("payment")))
    }

    // Insertar client y prefs para el token
    factory { SPagosStatusViewModel(get(), get()) }
}
