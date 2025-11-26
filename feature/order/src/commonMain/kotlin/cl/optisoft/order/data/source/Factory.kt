package cl.optisoft.order.data.source

import cl.optisoft.order.data.repository.Remote

internal class Factory(private val remote: Remote)  {
    fun getRemote(): Remote = remote
}