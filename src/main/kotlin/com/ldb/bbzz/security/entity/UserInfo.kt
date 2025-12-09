package com.ldb.bbzz.security.entity

import io.hypersistence.tsid.TSID
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "user_info", schema = "public")
open class UserInfo protected constructor() {
    @Id
//    @Tsid
    @Column(name = "user_tsid", nullable = false, columnDefinition = "BPCHAR(13)")
    lateinit var userTsid: String

    @Column(name = "user_id", length = 20)
    var userId: String? = null

    @Column(name = "user_name", length = 20)
    var userName: String? = null

    @Column(name = "user_password", length = 1000)
    var userPassword: String? = null

    @CreatedDate
    @Column(name = "reg_date")
    var regDate: Instant? = null

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "userInfo")
    var userRoles: MutableList<UserRole> = mutableListOf()


    data class Builder(
        var userTsid: String? = null,
        var userId: String? = null,
        var userName: String? = null,
        var userPassword: String? = null,
    ) {
        fun userId(userId: String?) = apply { this.userId = userId }
        fun userName(userName: String) = apply { this.userName = userName }
        fun userPassword(userPassword: String?) = apply { this.userPassword = userPassword }
        fun build() : UserInfo {
            val userInfo = UserInfo()
            userInfo.userTsid = TSID.fast().toString()
            userInfo.userId = this.userId
            userInfo.userName = this.userName
            userInfo.userPassword = this.userPassword
            return userInfo
        }
    }

    companion object {
        fun builder() = Builder()

    }
}