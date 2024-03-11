package com.example.passwordmanagerapp.data.mappers

import com.example.passwordmanagerapp.data.database.WebsiteDbModel
import com.example.passwordmanagerapp.domain.entities.Website
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun entityToDbModel(website: Website): WebsiteDbModel {
        return WebsiteDbModel(
            id = website.id,
            iconUrl = website.iconFileName,
            address = website.address,
            name = website.name,
            account = website.account
        )
    }

    fun dbModelToEntity(websiteDbModel: WebsiteDbModel): Website {
        return Website(
            id = websiteDbModel.id,
            iconFileName = websiteDbModel.iconUrl,
            address = websiteDbModel.address,
            name = websiteDbModel.name,
            account = websiteDbModel.account
        )
    }
}