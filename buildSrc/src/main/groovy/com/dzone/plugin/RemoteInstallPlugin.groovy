package com.dzone.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class RemoteInstallPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def remoteInstall = project.task('remoteInstall') {
            doLast {
                Constant.USERNAME = project.USERNAME
                Constant.PASSWORD = project.PASSWORD

                String projectInfo = project.PROJECT_INFO
                def projInfoArr = projectInfo.split(',')
                if (projInfoArr.length == 3) {
                    Constant.PROJECT = projInfoArr[0].trim()
                    Constant.PROJECTID = projInfoArr[1].trim()
                    Constant.ZONE = projInfoArr[2].trim()
                } else {
                    println('projectInfo incorrect,projectInfo = ' + projectInfo)
                }

                String phoneIdsGroupsStr = project.PHONE_IDS_GROUPS
                Constant.PHONEIDS_GROUPS = (phoneIdsGroupsStr == null || phoneIdsGroupsStr.length() == 0) ? null : phoneIdsGroupsStr.split(",")
                String serverIdsGroupsStr = project.SERVER_IDS_GROUPS
                Constant.SERVERIDS_GROUPS = (serverIdsGroupsStr == null || serverIdsGroupsStr.length() == 0) ? null : serverIdsGroupsStr.split(',')

//                Constant.IS_SEND_BROADCAST = project.IS_SEND_BROADCAST.toBoolean()
                Constant.CMD_BEFORE_INSTALL = project.CMD_BEFORE_INSTALL
                Constant.CMD_AFTER_INSTALL = project.CMD_AFTER_INSTALL

                println("Constant.USERNAME  = ${Constant.USERNAME},Constant.PASSWORD = ${Constant.PASSWORD}," +
                        "Constant.PROJECT = ${Constant.PROJECT},Constant.PROJECTID = ${Constant.PROJECTID},Constant.ZONE = ${Constant.ZONE}," +
                        "Constant.PHONEIDS_GROUPS = ${Constant.PHONEIDS_GROUPS},Constant.SERVERIDS_GROUPS = ${Constant.SERVERIDS_GROUPS}" +
                        "Constant.CMD_BEFORE_INSTALL = ${Constant.CMD_BEFORE_INSTALL},Constant.CMD_AFTER_INSTALL = ${Constant.CMD_AFTER_INSTALL}")

                boolean isFirst = true
                project.android.applicationVariants.all { variant ->
//                    if (variant.buildType.name == 'release') {
                    if (Arrays.asList(Constant.BUILD_VARIANTS).contains(variant.name)) {
                        variant.outputs.each { output ->
                            println("start to remoteInstall ${output.outputFile}")
                            String taskId = CloundServerManager.getInstance().installApkToCloud(output.outputFile,
                                    variant.mergedFlavor.applicationId, project.CMD_BEFORE_INSTALL, project.CMD_AFTER_INSTALL)
                            if (isFirst) {
                                Constant.TASK_IDS = taskId
                                isFirst = false
                            } else {
                                Constant.TASK_IDS = Constant.TASK_IDS + "," + taskId
                            }
                            println("excuting TASK_IDS = ${Constant.TASK_IDS}")
                        }
                    }

                }
            }
        }


        //assemblexxx --> remoteInstall
        project.afterEvaluate {
            String buildVariantsStr = project.BUILD_VARIANTS
            if (buildVariantsStr.matches("\".+?\"")) {
                //去除首尾的“”符号
                String[] buildVariantsArr = buildVariantsStr.substring(1, buildVariantsStr.length() - 1).split(",")
                Constant.BUILD_VARIANTS = buildVariantsArr
                println("buildVariantsArr = ${Arrays.toString(buildVariantsArr)}")
                for (int i = 0; i < buildVariantsArr.length; i++) {
                    def task = project.tasks.getByName("assemble${buildVariantsArr[i].trim()}")
                    remoteInstall.dependsOn(task)
                }
            }

        }


        //查询安装任务执行状态
        project.task("queryInstallStatus") {
            doLast {
                if (project.hasProperty('taskId')) {
                    println("querying-taskId = ${project.taskId}")
                    CloundServerManager.getInstance().queryTaskStatus(project.taskId)
                } else if (Constant.taskIds != null && Constant.taskIds.length() != 0) {
                    println("querying-taskIds = ${Constant.taskIds}")
                    String[] taskIdArr = Constant.taskIds.split(",")
                    for (int i = 0; i < taskIdArr.length; i++) {
                        CloundServerManager.getInstance().queryTaskStatus(taskIdArr[i])
                    }
                } else {
                    System.out.println("taskId and TASK_IDS are empty")
                }
            }
        }


    }

}