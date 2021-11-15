package com.mnzlabz.guessthenumber.data.di

import com.mnzlabz.guessthenumber.data.repository.implementations.GTNRepository
import com.mnzlabz.guessthenumber.data.repository.interfaces.IGTNRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun providesGTNRepository(repository: GTNRepository) : IGTNRepository
}