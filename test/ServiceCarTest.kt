package com.moobie

import com.moobie.model.Car
import com.moobie.service.CarService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import kotlin.test.assertEquals
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

var cars = mutableListOf<Car>()

class CarTestService : CarService {
    override suspend fun list(): List<Car?> {
        return cars
    }

    override suspend fun create(newCar: Car?): Car? {
        val size = cars.size
        val newId = cars[size - 1].id + 1

        newCar?.run {
            cars.add(Car(newId, model, brand, latitude, longitude))
        }

        return get(newId)
    }

    override suspend fun update(updatedCar: Car?): Car? {
        cars.replaceAll { if (it.id == updatedCar?.id) updatedCar else it }

        return get(updatedCar?.id)
    }

    override suspend fun get(id: Int?): Car? {
        return cars.firstOrNull { it.id == id }
    }

    override suspend fun delete(id: Int?) {
        cars.removeIf { it.id == id }
    }
}

class ServiceCarTest {

    private val carService: CarService = CarTestService()

    @Before
    fun before() {

        cars = mutableListOf(
            Car(
                1,
                "FIESTA 1.6 TI-VCT FLEX SE MANUAL 2018",
                "FORD",
                -23.562356,
                -46.6694725
            ),
            Car(
                2,
                "GOL 1.6 MSI TOTALFLEX TRENDLINE 4P MANUAL",
                "VOLKSWAGEN",
                -23.542096677319027,
                -46.658216017167874
            )
        )
    }

    @Test
    fun testGetCars() {
        runBlocking {
            val cars: List<Car?> = carService.list()

            assertEquals(cars.size, 2)
            assertEquals(cars[0]?.id, 1)
            assertEquals(cars[0]?.model, "FIESTA 1.6 TI-VCT FLEX SE MANUAL 2018")
        }
    }

    @Test
    fun testCreateCar() {
        runBlocking {
            var cars: List<Car?> = carService.list()

            assertEquals(cars.size, 2)

            val newCar = Car(
                3,
                "S60 2.4 TURBO GASOLINA 4P AUTOMÁTICO",
                "VOLVO",
                -23.571316,
                -46.6438717
            )
            val result = carService.create(newCar)

            assertNotNull(result)

            cars = carService.list()

            assertEquals(cars.size, 3)
            assertEquals(cars[2]?.model, result.model)
        }
    }

    @Test
    fun testUpdateCar() {
        runBlocking {
            var cars: List<Car?> = carService.list()

            assertEquals(cars[0]?.model, "FIESTA 1.6 TI-VCT FLEX SE MANUAL 2018")

            val result = carService.update(
                Car(
                    1,
                    "FIESTA 1.6 TI-VCT FLEX SE MANUAL 2018",
                    "FORD",
                    -23.562356,
                    -46.6694725
                )
            )

            assertNotNull(result)

            cars = carService.list()

            assertEquals(cars.size, 2)
            assertEquals(cars[0]?.model, result.model)
        }
    }

    @Test
    fun testDeleteCar() {
        runBlocking {
            var cars: List<Car?> = carService.list()

            assertEquals(cars.size, 2)
            assertEquals(carService.get(2)?.model, "GOL 1.6 MSI TOTALFLEX TRENDLINE 4P MANUAL")

            carService.delete(2)

            cars = carService.list()

            assertEquals(cars.size, 1)
            assertNull(carService.get(2))
        }
    }
}