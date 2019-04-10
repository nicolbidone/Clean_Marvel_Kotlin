package com.puzzlebench.clean_marvel_kotlin.fragment.mvp

import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.TEN_VALUE
import com.puzzlebench.clean_marvel_kotlin.ZERO_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetSingleCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.fragment.CharacterFragment
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
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class FragmentPresenterTest {

    @Mock
    private lateinit var view: FragmentContracts.View
    @Mock
    private lateinit var charactersFragment: CharacterFragment
    @Mock
    private lateinit var getSingleCharacterServiceUseCase: GetSingleCharacterServiceUseCase
    @Mock
    private lateinit var characterList: List<Character>

    private lateinit var model: FragmentContracts.Model
    private lateinit var presenter: FragmentContracts.Presenter

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
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        model = FragmentModel(getSingleCharacterServiceUseCase)
        presenter = FragmentPresenter(view, model)
    }

    @Test
    fun serviceResponseWithError() {
        `when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(Observable.error(Exception(EMPTY_VALUE)))
        presenter.init(charactersFragment)
        verify(getSingleCharacterServiceUseCase).invoke()

        verify(view).showToastNetworkError(EMPTY_VALUE)
    }

    @Test
    fun serviceResponseWithItemToShow() {
        `when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(Observable.just(characterList))
        presenter.init(charactersFragment)
        verify(getSingleCharacterServiceUseCase).invoke()

        verify(view).showFragmentDialog(charactersFragment)
    }

    @Test
    fun serviceResponseWithoutItemToShow() {
        `when`(characterList.isEmpty()).thenReturn(true)
        `when`(getSingleCharacterServiceUseCase.invoke()).thenReturn(Observable.just(characterList))
        presenter.init(charactersFragment)
        verify(getSingleCharacterServiceUseCase).invoke()

        verify(view).showToastNoItemToShow()
    }
}
