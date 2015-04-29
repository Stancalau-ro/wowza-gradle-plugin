package ro.stancalau.wowza.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem

class WowzaServiceTask extends DefaultTask {
    String serviceName
    String command

    @TaskAction
    def runCommand() {

        if (serviceName == null) {
            serviceName = project.wowza.serviceName
        }

        if (OperatingSystem.current().isWindows()) {
            project.exec {
                commandLine = ['cmd', '/c', 'net', command, serviceName]
                ignoreExitValue = true
            }
        } else {
            project.exec {
                commandLine = ['-c', "service $serviceName $command"]
                ignoreExitValue = true
            }
        }
    }
}
