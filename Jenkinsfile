@Library('jenkins-pipeline-library') _

pipeline {
	agent any

    parameters {
        booleanParam name: 'SKIP_BUILD' 	  	  , defaultValue: false, description: 'Skip maven build stage ?'
        booleanParam name: 'SKIP_UNIT_TESTS'  	  , defaultValue: false, description: 'Skip unit tests ?'
        booleanParam name: 'SKIP_SONAR'		  	  , defaultValue: false, description: 'Skip SonarQube Analysis ?'
        booleanParam name: 'SKIP_DEPLOY'          , defaultValue: false, description: 'Skip deploy stage ?'
        booleanParam name: 'SKIP_ACCEPTANCE_TESTS', defaultValue: false, description: 'Skip acceptance tests ?'
        choice       name: 'ACCEPTANCE_TESTS_ENV' , description: 'Environment where acceptance tests should run', choices: 'dev\nap1\nap2\nap3\nap4\nitg'
        choice       name: 'QA_ENV'               , description: 'QA Environment where released artifacts should be deployed', choices: 'ap1\nap2\nap3\nap4'
        booleanParam name: 'DRY_RUN'              , defaultValue: false, description: 'Disable release step'
        booleanParam name: 'SKIP_QA_DEPLOY'       , defaultValue: false, description: 'Skip Q/A deploy stage ?'
        booleanParam name: 'SKIP_SMOKE_TESTS'     , defaultValue: false, description: 'Skip smoke tests ?'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '3'))
        timeout(time: 2, unit: 'HOURS')
    }
    
    triggers {
        cron('H 19 * * 1-5')
    }
    
    stages {
    	stage('Environment Setup'){
			steps{
				script{
					echo "Loading Environment variables from file pipeline-conf.yml ..."
					pipelineConf = readYaml file: "pipeline-conf.yml"
					for(envVar in pipelineConf.envVars) {
						env.setProperty(envVar.key, envVar.value)
					}

					def pomModel = readMavenPom file: 'pom.xml'
					env.CURRENT_VERSION  = pomModel.version
					env.CURRENT_POM_ARTIFACT = pomModel.artifactId
					
					env.APPS_TO_DEPLOY = pipelineConf.envVars['APP_ARTIFACT_IDS'].collect{ '{\"a\":\"' + it + '\"}' }.join(', ')
					env.PIPELINE_STATUS = "SETUP"
					sh 'env'
				}
			}
		}
		
		stage('Build & Check Quality'){
			when { expression { !params.SKIP_BUILD} }
			steps{
				script{
					echo "Building version ${env.CURRENT_VERSION} of ${env.CURRENT_POM_ARTIFACT} ..."
					withMaven(maven: 'm-3.3', mavenSettingsConfig: 'user-maven-settings') {
						sh "mvn clean deploy -Dmaven.test.skip=${params.SKIP_UNIT_TESTS} "

					    // Sonar
						script{
						    if(params.SKIP_SONAR == false){
		    				    echo "Running SonarQube Analysis ..."
			                   // withSonarQubeEnv('swad-sonarqube') {
			    			    //	sh 'SONAR_USER_HOME=$WORKSPACE/.sonar mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar'
			    			    //}
						    }
						}
					}
				}
			}
		}

		stage('Deploy'){
		    when{ 
		    	allOf {
					branch "develop"
					expression { !params.SKIP_DEPLOY }
				} 
		    }
			steps{
				withMaven(maven: 'm-3.3', mavenSettingsConfig: 'user-maven-settings') {
					//sh "mvn -f ${env.OPS_MVN_MODULE_PATH}/pom.xml clean install -e -Denv=${env.ACCEPTANCE_TESTS_ENV} -Dactions=init,install,deploy -Dapps='{\"v\":\"${env.CURRENT_VERSION}\",\"apps\":[${env.APPS_TO_DEPLOY}]}'"
				}
			}
		}

		stage('Acceptance Tests'){
			when{ 
				allOf {
					branch "develop"
					expression { !params.SKIP_ACCEPTANCE_TESTS }
				} 
			}
			steps{
				withMaven(maven: 'm-3.3', mavenSettingsConfig: 'user-maven-settings') {
					//sh "mvn clean verify -Pacceptance,${params.ACCEPTANCE_TESTS_ENV} -f ${env.TESTS_MODULE_PATH}/pom.xml"
				}
				//cucumber buildStatus: 'UNSTABLE', fileIncludePattern: '**/*.json', jsonReportDirectory: "${env.TESTS_MODULE_PATH}/target/", sortingMethod: 'ALPHABETICAL'
			}
		}

		stage('Release'){
			when{ 
				allOf {
					branch "develop"
					expression { !params.DRY_RUN }
					expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' } // On ne poursuit que si l'etat courant du build est success
				} 			
			}
			steps{
				script{
                        
                        // On genere la version de release et la prochaine version de dev
                        def mvnVersions = computeMavenVersions(version: "${env.CURRENT_VERSION}")
                        env.RELEASE_VERSION = mvnVersions.releaseVersion 
                        env.NEXT_DEV_VERSION = mvnVersions.nextDevelopmentVersion
						
                        // On declenche la release
						withMaven(maven: 'm-3.3', mavenSettingsConfig: 'user-maven-settings') {
						   sh "mvn jgitflow:release-start -DreleaseVersion=${env.RELEASE_VERSION} -DdevelopmentVersion=${env.NEXT_DEV_VERSION}"
						   sh 'mvn jgitflow:release-finish -Darguments="-Dmaven.test.skip=true"'
						}

                        // On notifie
                        def releaseContent = releaseContentBuilder releaseVersion: "${env.RELEASE_VERSION}", env: env, build: currentBuild
                        mail    replyTo : '${DEFAULT_REPLYTO}', 
						 		subject : "${env.PROJECT_TRG} : new release ${env.RELEASE_VERSION} is ready to deploy.", 
						 		to	    : "${env.RELEASE_MAIL_RECIPENTS}", 
						 		mimeType: 'text/html',
						 		body    : "${releaseContent}"
						
						env.PIPELINE_STATUS = "RELEASED"
				}
			}
		}

		
		stage('QA Deploy') {
			 when{
				expression { env.PIPELINE_STATUS == "RELEASED" }
				expression { !params.SKIP_QA_DEPLOY }
			}
			steps{
				withMaven(maven: 'm-3.3', mavenSettingsConfig: 'user-maven-settings') {
					echo "No deploy stage defined"
			    }
			}
		}
		
		stage('Smoke Tests') {
			when{
				expression { env.PIPELINE_STATUS == "RELEASED" }
				expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' } // On ne poursuit que si l'etat courant du build est success
				expression { !params.SKIP_SMOKE_TESTS }
			}
			steps{
				withMaven(maven: 'm-3.3', mavenSettingsConfig: 'user-maven-settings') {
					echo "No smoke test defined"
				}
			}	
		}
    }
    
	post {
	    always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
	    failure {
		    mail to: "${env.BUILD_MAIL_RECIPENTS}",
                 subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                 body: "The pipeline ${currentBuild.fullDisplayName} failed."
	    }
	}

}