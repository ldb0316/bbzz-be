package com.ldb.bbzz.security.menu.entity

import jakarta.persistence.*

@Entity
@Table(name = "menu_role", schema = "public")
@IdClass(MenuRoleId::class)
class MenuRole(
    @Id
    @Column(name = "menu_tsid", nullable = false, columnDefinition = "BPCHAR(13)")
    var menuTsid: String,

    @Id
    @Column(name = "menu_role", nullable = false)
    var menuRole: String,

    @ManyToOne
    @JoinColumn(name = "menu_tsid", insertable = false, updatable = false)
    var menu: Menu? = null

) {
    constructor() : this("", "")
}