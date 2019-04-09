package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.EMPTY_VALUE
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterServices
import com.puzzlebench.clean_marvel_kotlin.domain.contracts.CharacterStored
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterStoredUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SetCharacterStoredUseCase
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

//TODO fix on second iteration
// error: However, there was exactly 1 interaction with this mock:
class CharacterPresenterTest {


    private var view = mock(CharacterContracts.View::class.java)
    private var characterServiceImp = mock(CharacterServices::class.java)
    private var characterStoredImp = mock(CharacterStored::class.java)

    private lateinit var model: CharacterContracts.Model
    private lateinit var presenter: CharacterContracts.Presenter
    private lateinit var getCharacterServiceUseCase: GetCharacterServiceUseCase
    private lateinit var getCharacterStoredUseCase: GetCharacterStoredUseCase
    private lateinit var setCharacterStoredUseCase: SetCharacterStoredUseCase

    companion object {

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val immediate = object : Scheduler() {
                internal var noDelay = 0

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

        private const val SIZE = 10
    }

    @Before
    fun setUp() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

        getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        getCharacterStoredUseCase = GetCharacterStoredUseCase(characterStoredImp)
        setCharacterStoredUseCase = SetCharacterStoredUseCase(characterStoredImp)

        model = CharacterModel(getCharacterServiceUseCase, getCharacterStoredUseCase, setCharacterStoredUseCase)

        presenter = CharacterPresenter(view, model)
    }

    @Test
    fun serviceResponseWithError() {
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.error(Exception("")))

        presenter.requestGetCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showToastNetworkError(EMPTY_VALUE)
    }

    @Test
    fun serviceResponseWithItemToShow() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(model.getCharacterDataServiceUseCase()).thenReturn(observable)

        presenter.requestGetCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(view).showCharacters(itemsCharacters)
        verify(view).hideLoading()
    }

    @Test
    fun serviceResponseWithoutItemToShow() {
        val itemsCharacters = emptyList<Character>()
        val observable = Observable.just(itemsCharacters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)

        presenter.requestGetCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(view).showToastNoItemToShow()
        verify(view).hideLoading()
    }

    @Test
    fun storedResponseWithItemToShow() {
        val itemsCharacters = CharactersFactory.getMockCharacter()
        Mockito.`when`(getCharacterStoredUseCase.invoke()).thenReturn(itemsCharacters)

        presenter.requestStoredCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(view).showCharacters(itemsCharacters)
        verify(view).hideLoading()
    }

    @Test
    fun storedResponseWithoutItemToShow() {
        val itemsCharacters = emptyList<Character>()
        Mockito.`when`(getCharacterStoredUseCase.invoke()).thenReturn(itemsCharacters)

        presenter.requestStoredCharacters()
        verify(view).hideCharacters()
        verify(view).showLoading()
        verify(view).showToastNoItemToShow()
        verify(view).hideLoading()
    }

    @Test
    fun viewInit(){
        presenter.init()
        verify(view).init()
    }
}
