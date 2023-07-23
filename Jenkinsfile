/* groovylint-disable-next-line CompileStatic */
pipeline {
   agent any

    tools {
        maven 'MAVEN_HOME'
    }
    stages {
        stage('Checkout') {
            steps {
                // Clonage manuel du référentiel Git
                git branch:'main', url:'https://github.com/s4v4te974/MyApp-MultiModules.git'
            }
        }
        stage('build') {
            steps {
                sh 'mvn -B clean install'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(installationName: 'SONAR') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Quality gate') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
        stage('SUCCESS') {
            steps {
                echo ' BUILD WITH SUCCESS '
            }
        }
    }
}