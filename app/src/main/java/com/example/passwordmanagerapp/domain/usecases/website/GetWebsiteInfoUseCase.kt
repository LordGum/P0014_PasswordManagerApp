package com.example.passwordmanagerapp.domain.usecases.website

import com.example.passwordmanagerapp.domain.repositories.RepositoryWebsite

class GetWebsiteInfoUseCase(
    private val repositoryWebsite: RepositoryWebsite
) {
    suspend operator fun invoke(websiteId: Int) = repositoryWebsite.getWebsiteInfo(websiteId)
}