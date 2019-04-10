package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.TEN_VALUE
import com.puzzlebench.clean_marvel_kotlin.ZERO_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterStoredUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SetCharacterStoredUseCase
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

//TODO fix on second iteration
// error: However, there was exactly 1 interaction with this mock:
class CharacterPresenterTest {

    @Mock
    private lateinit var view: CharacterContracts.View
    @Mock
    private lateinit var getCharacterServiceUseCase: GetCharacterServiceUseCase
    @Mock
    private lateinit var getCharacterStoredUseCase: GetCharacterStoredUseCase
    @Mock
    private lateinit var setCharacterStoredUseCase: SetCharacterStoredUseCase
    @Mock
    private lateinit var characterList: List<Character>

    private lateinit var model: CharacterContracts.Model
    private lateinit var presenter: CharacterContracts.Presenter

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

        model = CharacterModel(getCharacterServiceUseCase, getCharacterStoredUseCase, setCharacterStoredUseCase)
        presenter = CharacterPresenter(view, model)
    }

    @Test
    fun serviceResponseWithError() {
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.error(Exception(EMPTY_VALUE)))

        presenter.requestGetCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(getCharacterServiceUseCase).invoke()

        verify(view).hideLoading()
        verify(view).showToastNetworkError(EMPTY_VALUE)
    }

    @Test
    fun serviceResponseWithItemToShow() {
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.just(characterList))

        presenter.requestGetCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(getCharacterServiceUseCase).invoke()

        verify(setCharacterStoredUseCase).invoke(characterList)
        verify(view).showCharacters(characterList)

        verify(view).hideLoading()
    }

    @Test
    fun serviceResponseWithoutItemToShow() {
        `when`(characterList.isEmpty()).thenReturn(true)
        `when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.just(emptyList()))

        presenter.requestGetCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(getCharacterServiceUseCase).invoke()

        verify(view).showToastNoItemToShow()

        verify(view).hideLoading()
    }

    @Test
    fun storedResponseWithItemToShow() {
        `when`(getCharacterStoredUseCase.invoke()).thenReturn(characterList)

        presenter.requestStoredCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(getCharacterStoredUseCase).invoke()

        verify(view).showCharacters(characterList)

        verify(view).hideLoading()
    }

    @Test
    fun storedResponseWithoutItemToShow() {
        `when`(characterList.isEmpty()).thenReturn(true)
        `when`(getCharacterStoredUseCase.invoke()).thenReturn(characterList)

        presenter.requestStoredCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(getCharacterStoredUseCase).invoke()

        verify(view).showToastNoItemToShow()

        verify(view).hideLoading()
    }

    @Test
    fun viewInit() {
        presenter.init()
        verify(view).init()
    }
}
