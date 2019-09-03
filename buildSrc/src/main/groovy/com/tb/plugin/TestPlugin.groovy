package com.tb.plugin

import com.a.b.Constant
import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.android.applicationVariants.each { variant ->
            println(variant)
        }
        project.task('testty') {
            doLast {

                initParams()

            }

        }
    }

    private void initParams() {
        Constant.USERNAME = project.USERNAME
        Constant.PASSWORD = project.PASSWORD

        String projectInfo = project.PROJECT_INFO
        def projInfoArr = projectInfo.split(';')
        if (projInfoArr.length == 3) {
            Constant.PROJECT = projInfoArr[0]
            Constant.PROJECTID = projInfoArr[1]
            Constant.ZONE = projInfoArr[2]
        } else {
            println('projectInfo incorrect')
        }

        String phoneIdsGroupsStr = project.PHONE_IDS_GROUPS
        Constant.PhoneIdsGroups = phoneIdsGroupsStr.split(',')
        String serverIdsGroupsStr = project.SERVER_IDS_GROUPS
        Constant.ServerIdsGroups = serverIdsGroupsStr.split(',')

        println("Constant.USERNAME  = ${Constant.USERNAME},Constant.PASSWORD = ${Constant.PASSWORD}," +
                "Constant.PROJECT = ${Constant.PROJECT},Constant.PROJECTID = ${Constant.PROJECTID},Constant.ZONE = ${Constant.ZONE}" +
                "Constant.PhoneIdsGroups = ${Constant.PhoneIdsGroups},Constant.ServerIdsGroups = ${Constant.ServerIdsGroups}")
    }
}