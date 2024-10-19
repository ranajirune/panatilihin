package com.raineru.panatilihin2.demo

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier

// *** Hilt application class
// https://developer.android.com/training/dependency-injection/hilt-android#application-class
// All apps that use Hilt must contain an Application class that is annotated with @HiltAndroidApp
/*
@HiltAndroidApp
class ExampleApplication : Application()
 */

// *** Inject dependencies into Android classes
// https://developer.android.com/training/dependency-injection/hilt-android#android-classes
@AndroidEntryPoint
class ExampleActivity : ComponentActivity() {

    // You can now inject dependencies into Android classes using the @Inject annotation.
    @Inject
    lateinit var analytics: AnalyticsAdapter
}

// *** Define Hilt bindings
// https://developer.android.com/training/dependency-injection/hilt-android#define-bindings
// First way to define binding: constructor injection
class AnalyticsAdapter @Inject constructor(
    // You should also indicate how to provide instances of the parameter of the annotated constructor
    @AnalyticsServiceImpl2
    private val service: AnalyticsService
)

interface AnalyticsService {
    fun analyticsMethods()
}

// *** Hilt modules
// https://developer.android.com/training/dependency-injection/hilt-android#hilt-modules
// When constructor-injection is not possible or the class if from an external library,
// use hilt modules to provide the bindings

// first way to define bindings in hilt modules: Inject interface instances with @Binds
// https://developer.android.com/training/dependency-injection/hilt-android#inject-interfaces

// Constructor-injected, because Hilt needs to know how to
// provide instances of AnalyticsServiceImpl, too.
/*
class AnalyticsServiceImpl @Inject constructor(
) : AnalyticsService {

    override fun analyticsMethods() {}
}
*/

/*
@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsBindsModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: AnalyticsServiceImpl
    ): AnalyticsService
}
*/

// second way to define bindings in hilt modules: Inject instances with @Provides
// https://developer.android.com/training/dependency-injection/hilt-android#inject-provides

/*@Module
@InstallIn(ActivityComponent::class)
object AnalyticsProvidesModule {

    @Provides
    fun provideAnalyticsService(
        // Potential dependencies of this type
    ): AnalyticsService {
        // This function body is called every time an AnalyticsService type is requested
        return AnalyticsServiceImpl()
    }
}*/

// *** Provide multiple bindings for the same type
// https://developer.android.com/training/dependency-injection/hilt-android#multiple-bindings

// First define the qualifiers
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnalyticsServiceImpl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AnalyticsServiceImpl2

class AnalyticsServiceImplOne @Inject constructor(
) : AnalyticsService {

    override fun analyticsMethods() {
        TODO("Not yet implemented")
    }
}

class AnalyticsServiceImplTwo @Inject constructor(
) : AnalyticsService {

    override fun analyticsMethods() {
        TODO("Not yet implemented")
    }
}

// Annotate the function with a qualifier they'll be associated with
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @AnalyticsServiceImpl1
    @Provides
    fun provideAuthInterceptorOkHttpClient(
        // dependencies to use inside the body
//        authInterceptor: AuthInterceptor
    ): AnalyticsService {
        return AnalyticsServiceImplOne()
    }

    @AnalyticsServiceImpl2
    @Provides
    fun provideOtherInterceptorOkHttpClient(
        // dependencies to use inside the body
//        otherInterceptor: OtherInterceptor
    ): AnalyticsService {
        return AnalyticsServiceImplTwo()
    }
}

// Then lastly, inject the specific type that you need by
// annotating the field or parameter with the corresponding qualifier.

// As a dependency of another class.
/*@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {

    @Provides
    fun provideAnalyticsService(
        @AnalyticsServiceImpl1 analyticsServiceImpl1: AnalyticsService,
    ): AnalyticsService {
        return analyticsServiceImpl1
    }
}*/

// As a dependency of a constructor-injected class.
class ExampleServiceImpl @Inject constructor(
    @AnalyticsServiceImpl2 private val analyticsServiceImpl2: AnalyticsService
)

// At field injection.
@AndroidEntryPoint
class AnotherExampleActivity: ComponentActivity() {

    @AnalyticsServiceImpl1
    @Inject lateinit var analyticsServiceImpl1: AnalyticsService
}

// *** Predefined qualifiers in Hilt
// https://developer.android.com/training/dependency-injection/hilt-android#predefined-qualifiers
// Use predefined qualifiers like @ApplicationContext and @ActivityContext qualifiers
class AnotherAnalyticsAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    @AnalyticsServiceImpl1
    private val service: AnalyticsService
)

// *** Generated components for Android classes
// https://developer.android.com/training/dependency-injection/hilt-android#generated-components
/*
Hilt component	                Injector for
SingletonComponent	            Application
ActivityRetainedComponent	    N/A
ViewModelComponent	            ViewModel
ActivityComponent	            Activity
FragmentComponent	            Fragment
ViewComponent	                View
ViewWithFragmentComponent	    View annotated with @WithFragmentBindings
ServiceComponent	            Service
 */

// *** Component lifetimes
// https://developer.android.com/training/dependency-injection/hilt-android#component-lifetimes
/*
Generated component	            Created at	                Destroyed at
SingletonComponent	            Application#onCreate()  	Application destroyed
ActivityRetainedComponent	    Activity#onCreate()     	Activity#onDestroy()
ViewModelComponent	            ViewModel created	        ViewModel destroyed
ActivityComponent	            Activity#onCreate()	        Activity#onDestroy()
FragmentComponent           	Fragment#onAttach()     	Fragment#onDestroy()
ViewComponent               	View#super()	            View destroyed
ViewWithFragmentComponent   	View#super()	            View destroyed
ServiceComponent	            Service#onCreate()	        Service#onDestroy()
 */

// *** Component scopes
// By default, all bindings in Hilt are unscoped.
// This means that each time your app requests the binding, Hilt creates a new instance of the needed type.
// https://developer.android.com/training/dependency-injection/hilt-android#component-scopes
/*
Android class	                            Generated component	            Scope
Application	                                SingletonComponent	            @Singleton
Activity	                                ActivityRetainedComponent	    @ActivityRetainedScoped
ViewModel	                                ViewModelComponent	            @ViewModelScoped
Activity	                                ActivityComponent	            @ActivityScoped
Fragment	                                FragmentComponent	            @FragmentScoped
View	                                    ViewComponent	                @ViewScoped
View annotated with @WithFragmentBindings	ViewWithFragmentComponent	    @ViewScoped
Service	                                    ServiceComponent	            @ServiceScoped
 */

// A binding's scope must match the scope of the component where it is installed,
// so in this example you must install AnalyticsService in SingletonComponent instead of ActivityComponent:
/*
// If AnalyticsService is an interface.
@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

  @Singleton
  @Binds
  abstract fun bindAnalyticsService(
    analyticsServiceImpl: AnalyticsServiceImpl
  ): AnalyticsService
}

// If you don't own AnalyticsService.
@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

  @Singleton
  @Provides
  fun provideAnalyticsService(): AnalyticsService {
      return Retrofit.Builder()
               .baseUrl("https://example.com")
               .build()
               .create(AnalyticsService::class.java)
  }
}
 */

// *** Use Hilt with other Jetpack libraries
// https://developer.android.com/training/dependency-injection/hilt-jetpack
// https://developer.android.com/develop/ui/compose/libraries#hilt

// Inject ViewModel objects with Hilt
// Provide a ViewModel by annotating it with @HiltViewModel and
// using the @Inject annotation in the ViewModel object's constructor.
/*
@HiltViewModel
class ExampleViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val repository: ExampleRepository
) : ViewModel() {
  ...
}
 */

// *** Hilt and Dagger annotations cheat sheet
// https://developer.android.com/training/dependency-injection/hilt-cheatsheet

@HiltViewModel(assistedFactory = AssistedViewModel.Factory::class)
class AssistedViewModel @AssistedInject constructor(
    @Assisted private val aNumber: Int
): ViewModel() {

    fun giveMeTheNumber(): Int = aNumber

    @AssistedFactory
    interface Factory {
        fun create(aNumber: Int): AssistedViewModel
    }
}

@Composable
fun AssistedViewModelDemo() {
    val aNumber by remember {
        mutableIntStateOf((0..100).random())
    }

    val assistedViewModel: AssistedViewModel =
        hiltViewModel<AssistedViewModel, AssistedViewModel.Factory>(
            creationCallback = { factory ->
                factory.create(aNumber)
            }
        )
}
