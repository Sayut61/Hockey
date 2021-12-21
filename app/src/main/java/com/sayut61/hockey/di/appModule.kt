package com.sayut61.hockey.di

import android.content.Context
import androidx.room.Room
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.GamesInfoDao
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.HockeyDB
import com.sayut61.hockey.datalayer.datasource.loacaldatasource.PlayersInfoDao
import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSourceImpl
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
object AppModule {
    @Singleton
    @Provides
    fun provideHockeyDB(@ApplicationContext context: Context): HockeyDB {
        return Room.databaseBuilder(context, HockeyDB::class.java, "db").build()
    }

    @Singleton
    @Provides
    fun provideGamesInfoDao(db: HockeyDB): GamesInfoDao {
        return db.gamesInfoDao()
    }

    @Singleton
    @Provides
    fun providePlayersInfoDao(db: HockeyDB): PlayersInfoDao {
        return db.playersInfoDao()
    }

    @Singleton
    @Provides
    fun providesPlayersRepositories(
        remoteDataSource: RemoteDataSourceImpl,
        playersInfoDao: PlayersInfoDao
    ): PlayersRepository {
        return PlayersRepositoryImpl(remoteDataSource, playersInfoDao)
    }

    @Singleton
    @Provides
    fun providesTeamRepositories(remoteDataSource: RemoteDataSourceImpl): TeamsRepository {
        return TeamsRepositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun providesCalendarRepositories(
        remoteDataSource: RemoteDataSourceImpl,
        gamesInfoDao: GamesInfoDao
    ): GamesRepository {
        return GamesRepositoryImpl(remoteDataSource, gamesInfoDao)
    }

    @Singleton
    @Provides
    fun providesMapRepositories(remoteDataSource: RemoteDataSourceImpl): MapRepository {
        return MapRepositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun providesFavRepositories(
        gamesInfoDao: GamesInfoDao,
        remoteDataSource: RemoteDataSourceImpl
    ): GamesFavoriteRepository {
        return GamesFavoriteRepositoryImpl(gamesInfoDao, remoteDataSource)
    }
}

