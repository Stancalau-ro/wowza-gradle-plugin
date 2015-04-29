package ro.stancalau.wowza

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import ro.stancalau.wowza.tasks.WowzaDeployTask
import ro.stancalau.wowza.tasks.WowzaServiceTask
import ro.stancalau.wowza.vo.LocalDeploy

class WowzaPlugin implements Plugin<Project> {

    void apply(Project project) {

        project.apply(plugin: 'java')

        def deploys = project.container(LocalDeploy)
        project.extensions.create("wowza", WowzaExtension, project.container(LocalDeploy))

        project.configurations {
            pack
            copylib
            compile.extendsFrom pack
            compile.extendsFrom copylib
        }

        project.task('packageJars', type: Jar) {
            group = 'Wowza'
            description = 'Package jars from \'pack\' configuration to build jar.'
            manifest {
                attributes 'Implementation-Version': project.version
            }
            from { project.configurations.pack.collect { it.directory ? it : project.zipTree(it) } }
            with project.tasks.jar
        }

        project.task('stopWowza', type: WowzaServiceTask) {
            group = 'Wowza'
            description = 'Stop the local Wowza service.'
            command = 'stop'

            doFirst {
                println("Stopping Wowza Service")
            }
        }

        project.task('startWowza', type: WowzaServiceTask) {
            group = 'Wowza'
            description = 'Start the local Wowza service.'
            command = 'start'

            doFirst {
                println("Starting Wowza Service")
            }
        }

        project.task('restartWowza', type: DefaultTask) {
            group = 'Wowza'
            description = 'Restart the local Wowza service.'
        } << {
            project.tasks.stopWowza.execute()
            project.tasks.startWowza.execute()
        }

        project.task('deploy', type: WowzaDeployTask, dependsOn: 'build') {
            group = 'Wowza'
            description = 'Deploy the build jar to the local Wowza host and setup specified applications. Also copies \'copylib\' configuration jars to <wowzaPath>/lib'
        }

        project.tasks.build.dependsOn 'packageJars'
    }
}