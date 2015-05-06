package ro.stancalau.wowza

import ro.stancalau.wowza.vo.LocalDeploy

class WowzaExtension {
    def deploys

    String localWowzaPath
    String serviceName

    WowzaExtension(deploys) {
        this.deploys = deploys
    }

    def deploys(Closure closure) {
        deploys.configure(closure)
    }

    def add(LocalDeploy deploy){
        deploys.add(deploy)
    }
}