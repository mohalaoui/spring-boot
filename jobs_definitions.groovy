// creation de job pipeline
multibranchPipelineJob("b2comm-pipeline/b2comm-r-comm-pipeline") {
    branchSources {
        git {
            id = "b2comm-r-comm-pipeline"
            remote("git@gitlab.pin.dolmen.bouyguestelecom.fr:b2comm/ressource.git")
            credentialsId('ihddev')
        }
    }
    configure {
        def traits = it / sources / data / 'jenkins.branch.BranchSource' / source / traits
        traits << 'jenkins.plugins.git.traits.BranchDiscoveryTrait' {}
        traits << 'jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait' {
            includes("*")
            excludes("master")
        }
        traits << 'jenkins.plugins.git.traits.LocalBranchTrait' {
            extension(class: 'hudson.plugins.git.extensions.impl.LocalBranch') {
                localBranch("**")
            }
        }
        traits << 'jenkins.plugins.git.traits.PruneStaleBranchTrait' {
            extension(class: 'hudson.plugins.git.extensions.impl.PruneStaleBranch')
        }
        traits << 'jenkins.plugins.git.traits.WipeWorkspaceTrait' {
            extension(class: 'hudson.plugins.git.extensions.impl.WipeWorkspace')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            daysToKeep(5)
        }
    }
    triggers {
        cron('@daily')
    }
}

// creation des autres jobs
mavenJob('b2comm-admin/b2comm-r-comm-ops') {
    description("""Invoke the following actions :<br>
              <ul>
                 <li><b>init</b> :  initialize or update a node (admin.sh)
                 </li><li><b>install</b> : install an application</li>
                 <li><b>deploy</b> : deploy an application)</li>
                 <li><b>rollback</b> : revert to the previous version)</li>
                 <li><b>start|stop|status|restart|health</b></li>
                 <li><b>config</b> : reload configuration from the live application (+ notification)</li>
                 <li><b>management</b> : triggers a monitoring operation</li>
               </ul>""")
    logRotator(-1, 10)

    wrappers {
        configFiles {
            file('suivi-des-bancs-notifier') {
                targetLocation('suivi-des-bancs-notifier.groovy')
            }
        }
    }

    parameters {
        choiceParam('TARGET', ['dev', 'ap1', 'ap2', 'ap3'], 'Environnement cible')
        choiceParam('ACTIONS', ['init,install,deploy', 'install,deploy', 'start', 'stop', 'restart', 'status', 'health', 'config', 'management', 'init', 'install', 'deploy', 'rollback'], 'operation invoquee')
        mavenMetadataParameterDefinition {
          name("APP")
          description("version de l'application")
          repoBaseUrl('http://swad-factory-3b.pin.dev.dolmen.bouyguestelecom.fr:8081/repository/internal')
          credentialsId('nexus_admin')
          groupId('com.bouygtel.swad')
          artifactId('comms-provider')
          packaging('zip')
          maxVersions('15')
          defaultValue('LATEST')
          classifier('')
          versionFilter('') 
          sortOrder('DESC')
          currentArtifactInfoUrl('')
          currentArtifactInfoLabel('')
          currentArtifactInfoPattern('')
        }
        choiceParam('ARGS', ['-u reload?type=config -p'], 'Liste des actions de management/admin')
    }
    scm {
        git {
            branch('develop')
            remote {
                url("git@gitlab.pin.dolmen.bouyguestelecom.fr:b2comm/ressource.git")
                credentials('ihddev')
            }
        }
    }

    mavenInstallation('m-3.3')
    rootPOM('comms-ops/pom.xml')
    goals('clean install -e -Denv=${TARGET} -Dactions=${ACTIONS}  -Dargs="${ARGS}" -Dapps=\'{"v":"${APP_VERSION}","apps":[{"a":"comms-provider"}]}\' ')
    
    postBuildSteps {
    	conditionalSteps {
    		condition {
                expression('ap.*', '${TARGET}')
            }
            steps {
                groovyScriptFile('suivi-des-bancs-notifier') {
                	prop("st", "B2COMM")
                	prop("module", "RCOMM")
                	prop("banc", '${TARGET}')
                	prop("version", '${APP_VERSION}')
                }
            }
    	}
    }
}