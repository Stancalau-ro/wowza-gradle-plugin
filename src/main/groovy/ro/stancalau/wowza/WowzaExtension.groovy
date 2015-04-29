package ro.stancalau.wowza

import org.gradle.api.NamedDomainObjectContainer
import ro.stancalau.wowza.vo.LocalDeploy

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