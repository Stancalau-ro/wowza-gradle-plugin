package ro.stancalau.wowza.tasks
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.os.OperatingSystem

class WowzaServiceTask extends DefaultTask {
    String serviceName
    String command

    @TaskAction
    def runCommand() {

        initServiceName()

        if (onWindows()) {
            executeWindowsCommand()
        } else {
            executeUnixCommand()
        }
    }

    private void executeUnixCommand() {
        project.exec {
            commandLine = ['-c', "service $serviceName $command"]
            ignoreExitValue = true
        }
    }

    private void executeWindowsCommand() {
        project.exec {
            commandLine = ['cmd', '/c', 'net', command, serviceName]
            ignoreExitValue = true
        }
    }

    private boolean onWindows() {
        OperatingSystem.current().isWindows()
    }

    private void initServiceName() {
        if (serviceName == null) {
            serviceName = project.wowza.serviceName
        }
    }
}
