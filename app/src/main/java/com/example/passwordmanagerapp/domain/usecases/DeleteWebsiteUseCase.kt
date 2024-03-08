package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite

class DeleteWebsiteUseCase(
    private val repositoryWebsite: RepositoryWebsite
) {
    suspend operator fun invoke(idWebsite: Int) = repositoryWebsite.deleteWebsite(idWebsite)
}