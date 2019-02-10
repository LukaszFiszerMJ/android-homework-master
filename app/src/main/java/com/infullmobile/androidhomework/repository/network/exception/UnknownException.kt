package com.infullmobile.androidhomework.repository.network.exception

import java.lang.RuntimeException

class UnknownException(throwable:Throwable) : RuntimeException(throwable) {
}