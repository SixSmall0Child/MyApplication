package com.tb.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class TestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.android.applicationVariants.each { variant ->
            println(variant)
        }
        project.task('testty') {
            Constant.USERNAME = project.USERNAME
            Constant.PASSWORD = project.PASSWORD

            String projectInfo = project.PROJECT_INFO
            def projInfoArr = projectInfo.split(';')
            if (projInfoArr.length == 2){
                Constant.PROJECT = projectInfo.split(';')[0]
                Constant.PROJECTID = projectInfo.split(';')[1]
            }else {
                println('projectInfo incorrect')
            }

            String phoneIdsGroupsStr = project.phone_ids_groups
            Constant.PhoneIdsGroups = phoneIdsGroupsStr.split(',')
            String serverIdsGroupsStr = project.server_ids_groups
            Constant.ServerIdsGroups = serverIdsGroupsStr.split(',')

            println("Constant.USERNAME  = ${Constant.USERNAME },Constant.PASSWORD = ${Constant.PASSWORD}," +
                    "Constant.PROJECT = ${Constant.PROJECT},Constant.PROJECTID = ${Constant.PROJECTID}+" +
                    "Constant.PhoneIdsGroups = ${Constant.PhoneIdsGroups},Constant.ServerIdsGroups = ${Constant.ServerIdsGroups}")

        }
    }
}