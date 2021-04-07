package com.example.duoblogistics.data.network

import java.io.IOException

class NoConnectivityException(override val message: String?): IOException()
class HttpServerErrorException(override val message: String?): IOException()
class HttpNotFoundException(override val message: String?): IOException()
class HttpForbiddenException(override val message: String?): IOException()
class HttpUnauthorizedException(override val message: String?): IOException()
class HttpNotAuthorizedException(override val message: String?): IOException()