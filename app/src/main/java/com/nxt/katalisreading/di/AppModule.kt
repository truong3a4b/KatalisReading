package com.nxt.katalisreading.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nxt.katalisreading.data.remote.CloudinaryApi
import com.nxt.katalisreading.data.repository.AuthRepo
import com.nxt.katalisreading.data.repository.BookCategoryRepo
import com.nxt.katalisreading.data.repository.BookRepo
import com.nxt.katalisreading.data.repository.UserRepo
import com.nxt.katalisreading.domain.repository.IAuthRepository
import com.nxt.katalisreading.domain.repository.IBookCategoryRepo
import com.nxt.katalisreading.domain.repository.IBookRepo
import com.nxt.katalisreading.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//abstract class provideAuthRepositoryModule() {
//
//    @Binds
//    @Singleton
//    abstract fun bindIAuthRepository(impl: AuthRepo): IAuthRepository
//}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        realtimeDb: DatabaseReference
    ): IAuthRepository {
        return AuthRepo(firebaseAuth, realtimeDb)
    }

    @Provides
    @Singleton
    fun provideCloudinaryApi(): CloudinaryApi {
        return Retrofit.Builder()
            .baseUrl("https://api.cloudinary.com/v1_1/dtcdt4dcw/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CloudinaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        cloudinaryApi: CloudinaryApi,
        realtimeDb: DatabaseReference
    ): IUserRepository {
        return UserRepo(cloudinaryApi, realtimeDb)
    }

    @Provides
    @Singleton
    fun provideBookCategoryRepo(
        realtimeDb: DatabaseReference
    ): IBookCategoryRepo {
        return BookCategoryRepo(realtimeDb)
    }

    @Provides
    @Singleton
    fun provideBookRepo() : IBookRepo{
        return BookRepo()
    }
}