package com.flowz.drinkcocktails.di

import android.content.Context
import androidx.room.Room
import com.flowz.drinkcocktails.drinkroomdb.DrinkDao
import com.flowz.drinkcocktails.drinkroomdb.DrinkDatabase
import com.flowz.drinkcocktails.drinksrepository.DrinksCocktailsRepositoryForTesting
import com.flowz.drinkcocktails.drinksrepository.TdrinkRepository
import com.flowz.drinkcocktails.network.ApiServiceCalls
import com.flowz.drinkcocktails.network.DrinksRetrieverApiClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun httpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClientWithHeader = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return okHttpClientWithHeader
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(httpClient())
            .baseUrl(DrinksRetrieverApiClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()


    @Provides
    @Singleton
    fun providesDrinksApi(retrofit: Retrofit): ApiServiceCalls =
        retrofit.create(ApiServiceCalls::class.java)


    @Provides
    @Singleton
    fun providesDrinkDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, DrinkDatabase::class.java, "cocktailDrinks.db").build()


    @Provides
    @Singleton
    fun providesDrinksDao (db: DrinkDatabase) = db.drinkDao()

    @Singleton
    @Provides
    fun provideDefaultDrinkRepository(
        dao: DrinkDao,
        api: ApiServiceCalls
    ) = DrinksCocktailsRepositoryForTesting( api, dao) as TdrinkRepository
    
}