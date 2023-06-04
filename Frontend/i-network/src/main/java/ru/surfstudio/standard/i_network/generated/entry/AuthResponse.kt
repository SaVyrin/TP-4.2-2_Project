package ru.surfstudio.standard.i_network.generated.entry

import com.google.gson.annotations.SerializedName
import ru.surfstudio.standard.domain.entity.UserInfo
import ru.surfstudio.standard.i_network.network.Transformable

/**
 * Модель для парсинга ответа сервера с ключом
 */
data class AuthResponse(
    @SerializedName("personalAccount") private val id: Int,
    @SerializedName("name") private val name: String,
    @SerializedName("surname") private val surname: String,
    @SerializedName("address") private val address: String
) : Transformable<UserInfo> {

    override fun transform(): UserInfo {
        return UserInfo(id, name, surname, address)
    }
}