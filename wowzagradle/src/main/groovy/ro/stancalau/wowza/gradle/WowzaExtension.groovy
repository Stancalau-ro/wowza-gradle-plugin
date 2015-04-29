package ro.stancalau.wowza.gradle

import org.gradle.api.NamedDomainObjectContainer
import ro.stancalau.wowza.gradle.vo.LocalDeploy


class WowzaExtension {

    final NamedDomainObjectContainer<LocalDeploy> deploys
    String localWowzaPath
    String serviceName

    WowzaExtension(deploys) {
        this.deploys = deploys
    }

    def deploys(Closure closure) {
        deploys.configure(closure)
    }
}