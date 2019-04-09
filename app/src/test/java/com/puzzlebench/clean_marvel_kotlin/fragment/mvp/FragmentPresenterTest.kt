package com.puzzlebench.clean_marvel_kotlin.fragment.mvp

import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.TEN_VALUE
import com.puzzlebench.clean_marvel_kotlin.ZERO_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetSingleCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.fragment.CharacterFragment
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class FragmentPresenterTest {

    private var view = mock(FragmentContracts.View::class.java)
    private var characterService = mock(CharacterServices::class.java)
    private var charactersFragment = mock(CharacterFragment::class.java)

    private lateinit var model: FragmentContracts.Model
    private lateinit var presenter: FragmentContracts.Presenter
    private lateinit var getSingleCharacterServiceUseCase: GetSingleCharacterServiceUseCase

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val immediate = object : Scheduler() {
                internal var noDelay = ZERO_VALUE

                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    return super.scheduleDirect(run, noDelay.toLong(), unit) // Prevents StackOverflowErrors when scheduling with a delay
                }

                override fun createWorker(): Scheduler.Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }
            // These avoid ExceptionInInitializerError when testing methods that contains RxJava Schedulers
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
        }

        private const val SIZE = TEN_VALUE
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        getSingleCharacterServiceUseCase = GetSingleCharacterServiceUseCase(characterService, ZERO_VALUE)
        model = FragmentModel(getSingleCharacterServiceUseCase)
        presenter = FragmentPresenter(view, model)
    }

    @Test
    fun serviceResponseWithError() {
        Mockito.`when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(Observable.error(Exception(EMPTY_VALUE)))
        presenter.init(charactersFragment)
        verify(view).showToastNetworkError(EMPTY_VALUE)
    }

    @Test
    fun serviceResponseWithItemToShow() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(model.getSingleCharacterServiceUseCase()).thenReturn(observable)
        presenter.init(charactersFragment)
        verify(view).showFragmentDialog(charactersFragment)
    }

    @Test
    fun serviceResponseWithoutItemToShow() {
        val itemsCharacters = emptyList<Character>()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(model.getSingleCharacterServiceUseCase()).thenReturn(observable)
        presenter.init(charactersFragment)
        verify(view).showToastNoItemToShow()
    }
}
