package ro.stancalau.wowza.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class WowzaDeployTask extends DefaultTask {
    def jarFiles
    String localWowzaPath

    @TaskAction
    def deploy() {

        if (jarFiles == null) {
            jarFiles = [project.jar.archivePath.absolutePath]
        }

        if (localWowzaPath == null) {
            localWowzaPath = project.wowza.localWowzaPath
        }

        jarFiles.each { file ->
            project.copy {
                from file
                into "$localWowzaPath/lib"
            }
        }

        project.copy {
            from project.configurations.copylib
            into "$localWowzaPath/lib"
        }

        project.wowza.deploys.each { deploy ->
            project.mkdir("$localWowzaPath/applications/${deploy.applicationName}")
            project.copy {
                from deploy.configurationFile
                into "$localWowzaPath/conf/${deploy.applicationName}"
            }
        }
    }
}

