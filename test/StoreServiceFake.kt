package com.paringapi

import com.paringapi.model.Store
import com.paringapi.service.StoreService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import kotlin.test.assertEquals
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

var stores = mutableListOf<Store>()

class StoreServiceFake : StoreService {
    override suspend fun list(): List<Store> {
        return stores
    }

    override suspend fun create(newStore: Store): Store {
        val size = stores.size
        val newId = stores[size - 1].id + 1

        newStore.run {
            stores.add(Store(newId, name, iconUrl, latitude, longitude))
        }

        return get(newId)
    }

    override suspend fun update(updatedStore: Store): Store {
        stores.replaceAll { if (it.id == updatedStore.id) updatedStore else it }

        return get(updatedStore.id)
    }

    override suspend fun get(id: Int): Store {
        return stores.first { it.id == id }
    }

    override suspend fun delete(id: Int) {
        stores.removeIf { it.id == id }
    }
}

class StoreServiceTest {

    private val storeService: StoreService = StoreServiceFake()

    @Before
    fun before() {

        stores = mutableListOf(
            Store(
                1,
                "Magazine Luiza",
                "https://imgsapp.impresso.correioweb.com.br/app/da_impresso_130686904244/2020/03/30/325011/20200329211503546282i.jpg",
                -23.562356,
                -46.6694725
            ),
            Store(
                2,
                "Lojas Americanas",
                "https://s2.glbimg.com/Dgf_qkF--yAtoBEDeaPPVWmLvOg=/0x20:650x397/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_63b422c2caee4269b8b34177e8876b93/internal_photos/bs/2019/A/g/cQ3xPVTaee8zHQ4549fw/lojasamericanas.jpg",
                -23.542096677319027,
                -46.658216017167874
            )
        )
    }

    @Test
    fun testGetStores() {
        runBlocking {
            val stores: List<Store> = storeService.list()

            assertEquals(stores.size, 2)
            assertEquals(stores[0].id, 1)
            assertEquals(stores[0].name, "Magazine Luiza")
        }
    }

    @Test
    fun testCreateStore() {
        runBlocking {
            var stores: List<Store> = storeService.list()

            assertEquals(stores.size, 2)

            val newStore = Store(
                3,
                "Ponto frio",
                "https://saocarlosemrede.com.br/wp-content/uploads/2018/10/ponto_frio_002_1.jpg",
                -23.571316,
                -46.6438717
            )
            val result = storeService.create(newStore)

            assertNotNull(result)

            stores = storeService.list()

            assertEquals(stores.size, 3)
            assertEquals(stores[2].name, result.name)
        }
    }

    @Test
    fun testUpdateStore() {
        runBlocking {
            var stores: List<Store> = storeService.list()

            assertEquals(stores[0].name, "Magazine Luiza")

            val result = storeService.update(
                Store(
                    1,
                    "Magazine Luiza",
                    "https://imgsapp.impresso.correioweb.com.br/app/da_impresso_130686904244/2020/03/30/325011/20200329211503546282i.jpg",
                    -23.562356,
                    -46.6694725
                )
            )

            assertNotNull(result)

            stores = storeService.list()

            assertEquals(stores.size, 2)
            assertEquals(stores[0].name, result.name)
        }
    }

    @Test(expected = NoSuchElementException::class)
    fun testDeleteStore() {
        runBlocking {
            var stores: List<Store> = storeService.list()

            assertEquals(stores.size, 2)
            assertEquals(storeService.get(2).name, "Lojas Americanas")

            storeService.delete(2)

            stores = storeService.list()

            assertEquals(stores.size, 1)
            assertNull(storeService.get(2))
        }
    }
}