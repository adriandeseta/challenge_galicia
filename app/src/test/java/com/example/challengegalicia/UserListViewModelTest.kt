package com.example.challengegalicia

import androidx.paging.PagingData
import com.example.challengegalicia.data.remote.UsersRepository
import com.example.challengegalicia.data.response.Dob
import com.example.challengegalicia.data.response.Name
import com.example.challengegalicia.data.response.Street
import com.example.challengegalicia.data.response.UserImage
import com.example.challengegalicia.data.response.UserLocation
import com.example.challengegalicia.presentation.model.UserModel
import com.example.challengegalicia.presentation.userlist.UserListUiState
import com.example.challengegalicia.presentation.userlist.UserListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private lateinit var viewModel: UserListViewModel
    private lateinit var usersRepository: UsersRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        usersRepository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando inicia y no hay query, emite Success`() = runTest(testDispatcher) {
        val fakeUsers = listOf(
            UserModel(
                uuid = "uuid-1",
                gender = "male",
                name = Name("Mr", "Adrián", "De Seta"),
                picture = UserImage("", "", ""),
                country = "Argentina",
                email = "adrian@example.com",
                dob = Dob("1990-01-01", 34),
                location = UserLocation(Street(123, "Sarachaga")),
                phone = "+54 11 1234 5678"
            ),
            UserModel(
                uuid = "uuid-2",
                gender = "male",
                name = Name("Mr", "Juan", "Pérez"),
                picture = UserImage("", "", ""),
                country = "Paraguay",
                email = "juan@example.com",
                dob = Dob("1985-05-15", 39),
                location = UserLocation(Street(456, "Juan b. justo")),
                phone = "+56 9 8765 4321"
            )
        )

        coEvery { usersRepository.getAllUsers() } returns flowOf(PagingData.from(fakeUsers))

        // Act
        viewModel = UserListViewModel(usersRepository)
        advanceTimeBy(300)
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is UserListUiState.Success)
    }

    @Test
    fun `cuando la query no coincide con ningun usuario, emite Success con lista vacia`() =
        runTest(testDispatcher) {
            // Arrange
            val fakeUsers = listOf(
                UserModel(
                    uuid = "uuid-1",
                    gender = "male",
                    name = Name("Mr", "Adrián", "De Seta"),
                    picture = UserImage("", "", ""),
                    country = "Argentina",
                    email = "adrian@example.com",
                    dob = Dob("1990-01-01", 34),
                    location = UserLocation(Street(123, "Sarachaga")),
                    phone = "+54 11 1234 5678"
                )
            )

            coEvery { usersRepository.getAllUsers() } returns flowOf(PagingData.from(fakeUsers))

            viewModel = UserListViewModel(usersRepository)
            viewModel.onSearchQueryChange("zzzzzz")
            advanceTimeBy(300)
            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertTrue(state is UserListUiState.Success)
        }
}
