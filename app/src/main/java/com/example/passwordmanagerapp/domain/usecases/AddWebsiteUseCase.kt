package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite

class AddWebsiteUseCase(
    private val repositoryWebsite: RepositoryWebsite
) {
    suspend operator fun invoke(website: Website) = repositoryWebsite.addWebsite(website)
}