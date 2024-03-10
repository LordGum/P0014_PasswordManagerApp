package com.example.passwordmanagerapp.domain.usecases.website

import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite
import javax.inject.Inject

class DeleteWebsiteUseCase @Inject constructor(
    private val repositoryWebsite: RepositoryWebsite
) {
    suspend operator fun invoke(idWebsite: Int) = repositoryWebsite.deleteWebsite(idWebsite)
}