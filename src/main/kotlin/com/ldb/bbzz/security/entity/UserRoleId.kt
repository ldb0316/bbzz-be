package com.ldb.bbzz.security.entity

import java.io.Serializable

data class UserRoleId(
    var userTsid: String? = null,
    var userRole: String? = null
) : Serializable
