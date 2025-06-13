package app.cooperativa.data.localdb

import app.cooperativa.data.model.dto.Fine
import app.cooperativa.data.model.dto.FineDetail
import app.cooperativa.data.model.dto.FineType
import kotlinx.datetime.LocalDate

object FineMockData {
    private val mockFines = listOf(
        Fine(
            userId = 1,
            userName = "Juan Alberto Martínez Orellana",
            fineDetails = listOf(
                FineDetail(
                    id = 1,
                    name = "Mora Pago Casa",
                    date = LocalDate(2025, 5, 1),
                    amount = 25.0f,
                    type = FineType.LOAN
                )
            )
        ),
        Fine(
            userId = 2,
            userName = "María Fernanda López",
            fineDetails = listOf(
                FineDetail(
                    id = 2,
                    name = "Mora Pago Tarjeta",
                    date = LocalDate(2025, 4, 28),
                    amount = 15.0f,
                    type = FineType.QUOTA
                ),
                FineDetail(
                    id = 3,
                    name = "Penalización Extra",
                    date = LocalDate(2025, 5, 30),
                    amount = 10.0f,
                    type = FineType.QUOTA
                ),
                FineDetail(
                    id = 4,
                    name = "Mora Pago Vehículo",
                    date = LocalDate(2025, 5, 3),
                    amount = 30.0f,
                    type = FineType.LOAN
                ),
                FineDetail(
                    id = 5,
                    name = "Mora Pago Vehículo",
                    date = LocalDate(2025, 6, 3),
                    amount = 30.0f,
                    type = FineType.LOAN
                )
            )
        ),
        Fine(
            userId = 3,
            userName = "Carlos Eduardo Gómez",
            fineDetails = listOf(
                FineDetail(
                    id = 4,
                    name = "Mora Pago Vehículo",
                    date = LocalDate(2025, 5, 3),
                    amount = 30.0f,
                    type = FineType.LOAN
                ),
                FineDetail(
                    id = 5,
                    name = "Mora Pago Vehículo",
                    date = LocalDate(2025, 6, 3),
                    amount = 30.0f,
                    type = FineType.LOAN
                )
            )
        ),
        Fine(
            userId = 4,
            userName = "Ana Patricia Morales",
            fineDetails = listOf(
                FineDetail(
                    id = 6,
                    name = "Recargo por reconexión",
                    date = LocalDate(2025, 4, 30),
                    amount = 8.0f,
                    type = FineType.QUOTA
                )
            )
        ),
        Fine(
            userId = 5,
            userName = "Luis Fernando Castillo",
            fineDetails = listOf(
                FineDetail(
                    id = 7,
                    name = "Mora Estudiantil",
                    date = LocalDate(2025, 5, 5),
                    amount = 20.0f,
                    type = FineType.LOAN
                )
            )
        )
    )

    fun getAllFines(): List<Fine> = mockFines

    fun getFinesByUser(userId: Int): Fine? =
        mockFines.find { it.userId == userId }
}