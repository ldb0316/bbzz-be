package com.ldb.bbzz.security.menu.entity

import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "menu", schema = "public")
class Menu {
    @Id
    @Tsid
    @Size(max = 13)
    @Column(name = "menu_tsid", nullable = false, length = 13, columnDefinition = "BPCHAR(13)")
    var menuTsid: String? = null

    @Size(max = 2000)
    @NotNull
    @Column(name = "menu_url", nullable = false, length = 2000)
    var menuUrl: String = ""

    @Size(max = 10)
    @Column(name = "menu_method", nullable = false, length = 10)
    var menuMethod: String = ""

    @Size(max = 50)
    @Column(name = "menu_nm", length = 50)
    var menuNm: String? = null

    @Size(max = 13)
    @Column(name = "upper_menu_tsid", length = 13, columnDefinition = "BPCHAR(13)")
    var upperMenuTsid: String? = null

    @NotNull
    @Column(name = "menu_display_yn", nullable = false, length = 1, columnDefinition = "BPCHAR(1)")
    var menuDisplayYn: String = "N"

    @NotNull
    @Column(name = "required_login_yn", nullable = false, length = 1, columnDefinition = "BPCHAR(1)")
    var requiredLoginYn: String = "Y"

    @NotNull
    @Column(name = "menu_order", nullable = false, precision = 2)
    var menuOrder: Int = 0

    @NotNull
    @Column(name = "del_yn", nullable = false, length = 1, columnDefinition = "BPCHAR(1)")
    var delYn: String = "N"

    @NotNull
    @CreatedDate
    @Column(name = "reg_dt", nullable = false)
    var regDt: Instant? = null

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "menu")
    var menuRoles: MutableList<MenuRole> = mutableListOf()
}