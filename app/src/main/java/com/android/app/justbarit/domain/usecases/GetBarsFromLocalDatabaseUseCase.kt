package com.android.app.justbarit.domain.usecases

import com.android.app.justbarit.data.remote.entity.Bar
import com.android.app.justbarit.data.remote.repository.BarRepository
import javax.inject.Inject

class GetBarsFromLocalDatabaseUseCase @Inject constructor(
    private val barRepository: BarRepository
) {
    suspend operator fun invoke(): List<Bar> {
        return barRepository.getBarsFromDatabase()
    }
}