package cl.optisoft.doctors.data.source

import cl.optisoft.doctors.data.repository.Remote

internal class Factory(private val remote: Remote)  {

    fun getRemote(): Remote = remote
}