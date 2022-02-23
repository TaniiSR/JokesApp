package com.task.joke.ui.homemain.jokehome

import com.task.joke.base.BaseTestCase
import com.task.joke.base.getOrAwaitValue
import com.task.joke.data.models.responsedtos.Joke
import com.task.joke.domain.IDataRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeVMTest : BaseTestCase() {
    // Subject under test
    lateinit var viewModel: HomeVM

    // Use a fake UseCase to be injected into the viewModel
    lateinit var dataRepo: IDataRepository

    @Before
    fun setUp() {
        dataRepo = mockk()
    }

    @Test
    fun `get device list success`() {
        //Given
        val list: ArrayList<Joke> = arrayListOf(Joke(), Joke(), Joke())
        //1- Mock calls
        coEvery { dataRepo.getJokes(10) } returns mockk(relaxed = true)

        //2-Call
        viewModel = HomeVM(spyk(), dataRepo)
        viewModel.getJokeList(10)

        //3-verify
        Assert.assertEquals(true, viewModel.jokes.getOrAwaitValue() != null)

    }

    @Test
    fun `get device list error`() {

        //1- Mock calls
        coEvery { dataRepo.getJokes(10) } returns null

        //2-Call
        viewModel = HomeVM(spyk(), dataRepo)
        viewModel.getJokeList(10)

        //3-verify
        Assert.assertEquals(true, viewModel.jokes.getOrAwaitValue() == null)
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }
}
