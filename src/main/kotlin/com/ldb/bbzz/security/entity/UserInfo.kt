package com.ldb.bbzz.security.entity

//import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "user_info", schema = "public")
data class UserInfo(
    @Id
//    @Tsid
    @Column(name = "user_tsid", nullable = false)
    val userTsid: String = "temp",

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "user_name")
    var userName: String,

    @Column(name = "user_password")
    var userPassword: String,

    @Column(name = "user_role")
    val userRole: String = "temp"
) {
    fun setNewPassword(newPassword: String, passwordEncoder: PasswordEncoder) {
        val newPassword = passwordEncoder.encode(newPassword) ?: throw IllegalArgumentException("비밀번호는 필수 입력값입니다.")
        this.userPassword = newPassword
    }
}
