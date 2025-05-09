package app.cooperativa.data.localdb

import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.dto.FineDetail
import app.cooperativa.data.model.dto.FineType
import kotlinx.datetime.LocalDate

object FineMockData {

    private val mockFines = listOf(
        Fine(
            userName = "Juan Alberto Martínez Orellana",
            fineDetails = listOf(
                FineDetail(
                    name = "Mora Pago Casa",
                    date = LocalDate(2025, 5, 1),
                    amount = 25.00f,
                    type = FineType.LOAN
                )
            )
        ),
        Fine(
            userName = "María Fernanda López",
            fineDetails = listOf(
                FineDetail(
                    name = "Mora Pago Tarjeta",
                    date = LocalDate(2025, 4, 28),
                    amount = 15.00f,
                    type = FineType.QUOTA
                ),
                FineDetail(
                    name = "Penalización Extra",
                    date = LocalDate(2025, 5, 30),
                    amount = 10.00f,
                    type = FineType.QUOTA
                )
            )
        ),
        Fine(
            userName = "Carlos Eduardo Gómez",
            fineDetails = listOf(
                FineDetail(
                    name = "Mora Pago Vehículo",
                    date = LocalDate(2025, 5, 3),
                    amount = 30.00f,
                    type = FineType.LOAN
                ),
                FineDetail(
                    name = "Mora Pago Vehículo",
                    date = LocalDate(2025, 6, 3),
                    amount = 30.00f,
                    type = FineType.LOAN
                )

            )
        ),
        Fine(
            userName = "Ana Patricia Morales",
            fineDetails = listOf(
                FineDetail(
                    name = "Recargo por reconexión",
                    date = LocalDate(2025, 4, 30),
                    amount = 8.00f,
                    type = FineType.QUOTA
                )
            )
        ),
        Fine(
            userName = "Luis Fernando Castillo",
            fineDetails = listOf(
                FineDetail(
                    name = "Mora Estudiantil",
                    date = LocalDate(2025, 5, 5),
                    amount = 20.00f,
                    type = FineType.LOAN
                )
            )
        )
    )

    fun getAllFines(): List<Fine> = mockFines

    fun getFinesByUser(userName: String): Fine? =
        mockFines.find { it.userName == userName }
}