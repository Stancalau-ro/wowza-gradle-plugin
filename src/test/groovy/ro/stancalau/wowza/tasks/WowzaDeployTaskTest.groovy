package ro.stancalau.wowza.tasks
import org.gradle.testfixtures.ProjectBuilder
import ro.stancalau.wowza.WowzaExtension
import spock.lang.Specification

class WowzaDeployTaskTest extends Specification {

    static final TASK_NAME = "deployTask"
    static final WOWZA_PATH = "testTemp/wowzaFolder"
    static final WowzaExtension EXTENSION = new WowzaExtension();

    def project
    WowzaDeployTask task

    def setup() {
        EXTENSION.localWowzaPath = WOWZA_PATH

        project = ProjectBuilder.builder().build()
        project.task(TASK_NAME, type: WowzaDeployTask)
        project.extensions.add("wowza", EXTENSION)
        project.plugins.apply("java")
        project.libsDirName = "test"
        task = project.tasks[TASK_NAME]
    }

    def cleanup() {
        //TODO delete all wowza related deployment folders and files
    }

    def 'test defaults'() {
        expect: task.localWowzaPath == null
                task.jarFile == null
    }

    def 'test initialization'() {
        when:   task.initLocalWowzaPath()
        then:   task.localWowzaPath == WOWZA_PATH

        when:   task.initBuildJarFiles()
        then:   task.jarFile == project.jar.archivePath.absolutePath
    }

}
