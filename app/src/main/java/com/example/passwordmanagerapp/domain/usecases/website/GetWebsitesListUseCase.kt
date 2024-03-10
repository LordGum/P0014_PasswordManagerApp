package com.example.passwordmanagerapp.domain.usecases.website

import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite
import javax.inject.Inject

class GetWebsitesListUseCase @Inject constructor(
    private val repositoryWebsite: RepositoryWebsite
) {
    operator fun invoke() = repositoryWebsite.websitesList
}