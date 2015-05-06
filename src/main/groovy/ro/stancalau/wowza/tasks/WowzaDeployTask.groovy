package ro.stancalau.wowza.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class WowzaDeployTask extends DefaultTask {

    public static final String LIB_FOLDER = "lib"
    public static final String APPLICATIONS_FOLDER = "applications"
    public static final String CONF_FOLDER = "conf"

    def jarFile
    String localWowzaPath

    @TaskAction
    def deploy() {
        initBuildJarFiles()
        initLocalWowzaPath()

        copyBuildJarsToLib()
        copyDependenciesToLib()

        deployApplications()
    }

    void deployApplications() {
        project.wowza.deploys.each { deploy ->
            project.mkdir("$localWowzaPath/$APPLICATIONS_FOLDER/${deploy.name}")
            project.copy {
                from deploy.configurationFile
                into "$localWowzaPath/$CONF_FOLDER/${deploy.name}"
            }
        }
    }

    void copyDependenciesToLib() {
        project.copy {
            from project.configurations.copylib
            into "$localWowzaPath/$LIB_FOLDER"
        }
    }

    void copyBuildJarsToLib() {
        project.copy {
            from jarFile
            into "$localWowzaPath/$LIB_FOLDER"
        }
    }

    void initLocalWowzaPath() {
        if (localWowzaPath == null) {
            localWowzaPath = project.wowza.localWowzaPath
        }
    }

    void initBuildJarFiles() {
        if (jarFile == null) {
            jarFile = project.jar.archivePath.absolutePath
        }
    }
}

