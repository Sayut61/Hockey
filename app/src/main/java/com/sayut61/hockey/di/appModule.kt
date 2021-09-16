package com.sayut61.hockey.di

import com.sayut61.hockey.datalayer.datasource.remotedatasource.RemoteDataSource
import com.sayut61.hockey.datalayer.repositories.TeamRepositoriesImpl
import com.sayut61.hockey.domain.TeamRepositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@InstallIn(SingletonComponent::class)
//@Module
//object AppModule {
//    @Singleton
//    @Provides
//    fun provideTeamsInfoDao(@ApplicationContext context: Context): TeamsInfoDao {
//        val db = Room.databaseBuilder(context, HockeyDB::class.java, "db").build()
//        return db.countriesInfoDao()
//    }
//}

@InstallIn(SingletonComponent::class)
@Module
object AppModule{
    @Singleton
    @Provides
    fun providesTeamRepositories(remoteDataSource: RemoteDataSource): TeamRepositories{
        return TeamRepositoriesImpl(remoteDataSource)
    }
}