package com.example.passwordmanagerapp.domain.usecases.website

import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite
import javax.inject.Inject

class AddWebsiteUseCase  @Inject constructor(
    private val repositoryWebsite: RepositoryWebsite
) {
    suspend operator fun invoke(website: Website) = repositoryWebsite.addWebsite(website)
}