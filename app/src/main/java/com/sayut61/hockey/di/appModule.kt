package com.sayut61.hockey.di

import android.content.Context
import androidx.room.Room
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.*

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.repositories.*
import com.sayut61.hockey.domain.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    @Singleton
    @Provides
    fun provideGamesInfoDao(@ApplicationContext context: Context): GamesInfoDao {
        val db = Room.databaseBuilder(context, HockeyGameDB::class.java, "game").build()
        return db.gamesInfoDao()
    }
    @Singleton
    @Provides
    fun providePlayersInfoDao(@ApplicationContext context: Context): PlayersInfoDao {
        val db = Room.databaseBuilder(context, HockeyPlayerDB::class.java, "player").build()
        return db.playersInfoDao()
    }
    @Singleton
    @Provides
    fun providesPlayersRepositories(remoteDataSource: RemoteDataSource, playersInfoDao: PlayersInfoDao): PlayerRepository{
        return PlayerRepositoryImpl(remoteDataSource, playersInfoDao)
    }
    @Singleton
    @Provides
    fun providesTeamRepositories(remoteDataSource: RemoteDataSource): TeamRepository{
        return TeamRepositoryImpl(remoteDataSource)
    }
    @Singleton
    @Provides
    fun providesCalendarRepositories(remoteDataSource: RemoteDataSource, gamesInfoDao: GamesInfoDao): GameRepository{
        return GameRepositoryImpl(remoteDataSource, gamesInfoDao)
    }
    @Singleton
    @Provides
    fun providesMapRepositories(remoteDataSource: RemoteDataSource): MapRepository {
        return MapRepositoryImpl(remoteDataSource)
    }
    @Singleton
    @Provides
    fun providesFavRepositories(gamesInfoDao: GamesInfoDao, remoteDataSource: RemoteDataSource): GameFavoriteRepository {
        return GameFavoriteRepositoryImpl(gamesInfoDao, remoteDataSource)
    }
}

