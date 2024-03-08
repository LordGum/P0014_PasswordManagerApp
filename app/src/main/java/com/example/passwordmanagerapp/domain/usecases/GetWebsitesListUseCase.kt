package com.example.passwordmanagerapp.domain.usecases

import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite

class GetWebsitesListUseCase(
    private val repositoryWebsite: RepositoryWebsite
) {
    operator fun invoke() = repositoryWebsite.websitesList
}