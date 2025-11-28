package com.ldb.bbzz.security.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "user_role", schema = "public")
@IdClass(UserRoleId::class)
class UserRole (
    @Id
    @Column(name="user_tsid", nullable = false, columnDefinition = "CHAR(13)")
     var userTsid: String,

    @Id
    @Column(name="user_role", nullable = false, length = 20)
     var userRole: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tsid", insertable = false, updatable = false)
    var userInfo: UserInfo? = null
){
    @CreatedDate
    @Column(name="reg_date")
    var regDate: Instant? = null

    constructor() : this("", "") // JPA용 기본 생성자

}