package com.ldb.bbzz.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/samples"])
class SampleController {

    @GetMapping
    fun getAll():String  {

        return "hi";
    }

    @GetMapping("/{tsid}")
    fun getOne(@PathVariable tsid:String ):String  {

        return tsid;
    }

}