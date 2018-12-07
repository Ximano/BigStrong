package com.mili.net.exceptions

import java.io.IOException

class ErrorException(val code: String, message: String) : IOException(message)